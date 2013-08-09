/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

/**
 * @author Mate Thurzo
 * @author Raymond Augé
 */
public class SubscriptionPermissionImpl implements SubscriptionPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, subscriptionClassName, subscriptionClassPK,
				inferredClassName, inferredClassPK)) {

			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException {

		if (subscriptionClassName == null) {
			return false;
		}

		try {
			MBDiscussionLocalServiceUtil.getDiscussion(
				subscriptionClassName, subscriptionClassPK);

			return hasDiscussionPermission(
				permissionChecker, subscriptionClassName, subscriptionClassPK);
		}
		catch (NoSuchDiscussionException nsde) {
		}

		if (Validator.isNotNull(inferredClassName)) {
			Boolean hasPermission = hasPermission(
				permissionChecker, inferredClassName, inferredClassPK,
				ActionKeys.VIEW);

			if ((hasPermission == null) || !hasPermission) {
				return false;
			}
		}

		Boolean hasPermission = hasPermission(
			permissionChecker, subscriptionClassName, subscriptionClassPK,
			ActionKeys.SUBSCRIBE);

		if (hasPermission != null) {
			return hasPermission;
		}

		return true;
	}

	protected boolean hasDiscussionPermission(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK)
		throws PortalException, SystemException {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			subscriptionClassName, subscriptionClassPK);

		if (assetEntry == null) {
			return false;
		}

		if (subscriptionClassName.equals(BlogsEntry.class.getName())) {
			BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
				subscriptionClassPK);

			long groupId = blogsEntry.getGroupId();

			long companyId = blogsEntry.getCompanyId();

			long blogsCreatorId = blogsEntry.getUserId();

			return MBDiscussionPermission.contains(
				permissionChecker, companyId, groupId, subscriptionClassName,
				subscriptionClassPK, blogsCreatorId, ActionKeys.VIEW);
		}
		else if (subscriptionClassName.equals(JournalArticle.class.getName())) {
			JournalArticle journalArticle =
				JournalArticleLocalServiceUtil.getLatestArticle(
					subscriptionClassPK);

			long groupId = journalArticle.getGroupId();

			long companyId = journalArticle.getCompanyId();

			long articleCreatorId = journalArticle.getUserId();

			return MBDiscussionPermission.contains(
				permissionChecker, companyId, groupId, subscriptionClassName,
				subscriptionClassPK, articleCreatorId, ActionKeys.VIEW);
		}

		return false;
	}

	protected Boolean hasPermission(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException, SystemException {

		if (className.equals(BlogsEntry.class.getName())) {
			return BlogsPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return JournalPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBCategory.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(classPK);

			if (group == null) {
				return MBCategoryPermission.contains(
					permissionChecker, classPK, actionId);
			}

			return MBPermission.contains(permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBThread.class.getName())) {
			MBThread mbThread = MBThreadLocalServiceUtil.fetchThread(classPK);

			if (mbThread == null) {
				return false;
			}

			return MBMessagePermission.contains(
				permissionChecker, mbThread.getRootMessageId(), actionId);
		}
		else if (className.equals(WikiNode.class.getName())) {
			return WikiNodePermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(WikiPage.class.getName())) {
			return WikiPagePermission.contains(
				permissionChecker, classPK, actionId);
		}

		return null;
	}

}