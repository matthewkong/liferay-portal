/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class MBCategoryPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, category, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return MBPermission.contains(permissionChecker, groupId, actionId);
		}
		else {
			MBCategory category = MBCategoryLocalServiceUtil.getCategory(
				categoryId);

			return contains(permissionChecker, category, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		return contains(permissionChecker, category, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_CATEGORY)) {
			actionId = ActionKeys.ADD_SUBCATEGORY;
		}

		if (MBBanLocalServiceUtil.hasBan(
				category.getGroupId(), permissionChecker.getUserId())) {

			return false;
		}

		long parentCategoryId = category.getParentCategoryId();

		MBCategory parentCategory = category;

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
			try {
				while (parentCategoryId !=
						MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

					parentCategory = MBCategoryLocalServiceUtil.getCategory(
						parentCategoryId);

					if (!permissionChecker.hasOwnerPermission(
							parentCategory.getCompanyId(),
							MBCategory.class.getName(), parentCategoryId,
							parentCategory.getUserId(), ActionKeys.VIEW) &&
						!permissionChecker.hasPermission(
							parentCategory.getGroupId(),
							MBCategory.class.getName(), parentCategoryId,
							ActionKeys.VIEW)) {
						return false;
					}

					parentCategoryId = parentCategory.getParentCategoryId();
				}
			}
			catch (NoSuchCategoryException nsce) {
				if (!parentCategory.isInTrash()) {
					throw nsce;
				}
			}

			parentCategoryId = category.getParentCategoryId();

			parentCategory = category;
		}

		try {
			while (parentCategoryId !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				parentCategory = MBCategoryLocalServiceUtil.getCategory(
					parentCategoryId);

				if (permissionChecker.hasOwnerPermission(
						parentCategory.getCompanyId(),
						MBCategory.class.getName(), parentCategoryId,
						parentCategory.getUserId(), actionId) ||
					permissionChecker.hasPermission(
						parentCategory.getGroupId(), MBCategory.class.getName(),
						parentCategoryId, actionId)) {

					return true;
				}

				parentCategoryId = parentCategory.getParentCategoryId();
			}
		}
		catch (NoSuchCategoryException nsce) {
			if (!parentCategory.isInTrash()) {
				throw nsce;
			}
		}

		if (permissionChecker.hasOwnerPermission(
				category.getCompanyId(), MBCategory.class.getName(),
				category.getCategoryId(), category.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			category.getGroupId(), MBCategory.class.getName(),
			category.getCategoryId(), actionId);
	}

}