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

package com.liferay.portlet.bookmarks.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.WorkflowedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the BookmarksFolder service. Represents a row in the &quot;BookmarksFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolder
 * @see com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl
 * @see com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl
 * @generated
 */
public interface BookmarksFolderModel extends BaseModel<BookmarksFolder>,
	ContainerModel, GroupedModel, StagedModel, WorkflowedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a bookmarks folder model instance should use the {@link BookmarksFolder} interface instead.
	 */

	/**
	 * Returns the primary key of this bookmarks folder.
	 *
	 * @return the primary key of this bookmarks folder
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this bookmarks folder.
	 *
	 * @param primaryKey the primary key of this bookmarks folder
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this bookmarks folder.
	 *
	 * @return the uuid of this bookmarks folder
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this bookmarks folder.
	 *
	 * @param uuid the uuid of this bookmarks folder
	 */
	public void setUuid(String uuid);

	/**
	 * Returns the folder ID of this bookmarks folder.
	 *
	 * @return the folder ID of this bookmarks folder
	 */
	public long getFolderId();

	/**
	 * Sets the folder ID of this bookmarks folder.
	 *
	 * @param folderId the folder ID of this bookmarks folder
	 */
	public void setFolderId(long folderId);

	/**
	 * Returns the group ID of this bookmarks folder.
	 *
	 * @return the group ID of this bookmarks folder
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this bookmarks folder.
	 *
	 * @param groupId the group ID of this bookmarks folder
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this bookmarks folder.
	 *
	 * @return the company ID of this bookmarks folder
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this bookmarks folder.
	 *
	 * @param companyId the company ID of this bookmarks folder
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this bookmarks folder.
	 *
	 * @return the user ID of this bookmarks folder
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this bookmarks folder.
	 *
	 * @param userId the user ID of this bookmarks folder
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this bookmarks folder.
	 *
	 * @return the user uuid of this bookmarks folder
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this bookmarks folder.
	 *
	 * @param userUuid the user uuid of this bookmarks folder
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this bookmarks folder.
	 *
	 * @return the user name of this bookmarks folder
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this bookmarks folder.
	 *
	 * @param userName the user name of this bookmarks folder
	 */
	public void setUserName(String userName);

	/**
	 * Returns the create date of this bookmarks folder.
	 *
	 * @return the create date of this bookmarks folder
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this bookmarks folder.
	 *
	 * @param createDate the create date of this bookmarks folder
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this bookmarks folder.
	 *
	 * @return the modified date of this bookmarks folder
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this bookmarks folder.
	 *
	 * @param modifiedDate the modified date of this bookmarks folder
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the resource block ID of this bookmarks folder.
	 *
	 * @return the resource block ID of this bookmarks folder
	 */
	public long getResourceBlockId();

	/**
	 * Sets the resource block ID of this bookmarks folder.
	 *
	 * @param resourceBlockId the resource block ID of this bookmarks folder
	 */
	public void setResourceBlockId(long resourceBlockId);

	/**
	 * Returns the parent folder ID of this bookmarks folder.
	 *
	 * @return the parent folder ID of this bookmarks folder
	 */
	public long getParentFolderId();

	/**
	 * Sets the parent folder ID of this bookmarks folder.
	 *
	 * @param parentFolderId the parent folder ID of this bookmarks folder
	 */
	public void setParentFolderId(long parentFolderId);

	/**
	 * Returns the name of this bookmarks folder.
	 *
	 * @return the name of this bookmarks folder
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this bookmarks folder.
	 *
	 * @param name the name of this bookmarks folder
	 */
	public void setName(String name);

	/**
	 * Returns the description of this bookmarks folder.
	 *
	 * @return the description of this bookmarks folder
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this bookmarks folder.
	 *
	 * @param description the description of this bookmarks folder
	 */
	public void setDescription(String description);

	/**
	 * Returns the status of this bookmarks folder.
	 *
	 * @return the status of this bookmarks folder
	 */
	public int getStatus();

	/**
	 * Sets the status of this bookmarks folder.
	 *
	 * @param status the status of this bookmarks folder
	 */
	public void setStatus(int status);

	/**
	 * Returns the status by user ID of this bookmarks folder.
	 *
	 * @return the status by user ID of this bookmarks folder
	 */
	public long getStatusByUserId();

	/**
	 * Sets the status by user ID of this bookmarks folder.
	 *
	 * @param statusByUserId the status by user ID of this bookmarks folder
	 */
	public void setStatusByUserId(long statusByUserId);

	/**
	 * Returns the status by user uuid of this bookmarks folder.
	 *
	 * @return the status by user uuid of this bookmarks folder
	 * @throws SystemException if a system exception occurred
	 */
	public String getStatusByUserUuid() throws SystemException;

	/**
	 * Sets the status by user uuid of this bookmarks folder.
	 *
	 * @param statusByUserUuid the status by user uuid of this bookmarks folder
	 */
	public void setStatusByUserUuid(String statusByUserUuid);

	/**
	 * Returns the status by user name of this bookmarks folder.
	 *
	 * @return the status by user name of this bookmarks folder
	 */
	@AutoEscape
	public String getStatusByUserName();

	/**
	 * Sets the status by user name of this bookmarks folder.
	 *
	 * @param statusByUserName the status by user name of this bookmarks folder
	 */
	public void setStatusByUserName(String statusByUserName);

	/**
	 * Returns the status date of this bookmarks folder.
	 *
	 * @return the status date of this bookmarks folder
	 */
	public Date getStatusDate();

	/**
	 * Sets the status date of this bookmarks folder.
	 *
	 * @param statusDate the status date of this bookmarks folder
	 */
	public void setStatusDate(Date statusDate);

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	 */
	public boolean getApproved();

	/**
	 * Returns <code>true</code> if this bookmarks folder is approved.
	 *
	 * @return <code>true</code> if this bookmarks folder is approved; <code>false</code> otherwise
	 */
	public boolean isApproved();

	/**
	 * Returns <code>true</code> if this bookmarks folder is denied.
	 *
	 * @return <code>true</code> if this bookmarks folder is denied; <code>false</code> otherwise
	 */
	public boolean isDenied();

	/**
	 * Returns <code>true</code> if this bookmarks folder is a draft.
	 *
	 * @return <code>true</code> if this bookmarks folder is a draft; <code>false</code> otherwise
	 */
	public boolean isDraft();

	/**
	 * Returns <code>true</code> if this bookmarks folder is expired.
	 *
	 * @return <code>true</code> if this bookmarks folder is expired; <code>false</code> otherwise
	 */
	public boolean isExpired();

	/**
	 * Returns <code>true</code> if this bookmarks folder is inactive.
	 *
	 * @return <code>true</code> if this bookmarks folder is inactive; <code>false</code> otherwise
	 */
	public boolean isInactive();

	/**
	 * Returns <code>true</code> if this bookmarks folder is incomplete.
	 *
	 * @return <code>true</code> if this bookmarks folder is incomplete; <code>false</code> otherwise
	 */
	public boolean isIncomplete();

	/**
	 * Returns <code>true</code> if this bookmarks folder is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this bookmarks folder is in the Recycle Bin; <code>false</code> otherwise
	 */
	public boolean isInTrash();

	/**
	 * Returns <code>true</code> if this bookmarks folder is pending.
	 *
	 * @return <code>true</code> if this bookmarks folder is pending; <code>false</code> otherwise
	 */
	public boolean isPending();

	/**
	 * Returns <code>true</code> if this bookmarks folder is scheduled.
	 *
	 * @return <code>true</code> if this bookmarks folder is scheduled; <code>false</code> otherwise
	 */
	public boolean isScheduled();

	/**
	 * Returns the container model ID of this bookmarks folder.
	 *
	 * @return the container model ID of this bookmarks folder
	 */
	public long getContainerModelId();

	/**
	 * Sets the container model ID of this bookmarks folder.
	 *
	 * @param container model ID of this bookmarks folder
	 */
	public void setContainerModelId(long containerModelId);

	/**
	 * Returns the container name of this bookmarks folder.
	 *
	 * @return the container name of this bookmarks folder
	 */
	public String getContainerModelName();

	/**
	 * Returns the parent container model ID of this bookmarks folder.
	 *
	 * @return the parent container model ID of this bookmarks folder
	 */
	public long getParentContainerModelId();

	/**
	 * Sets the parent container model ID of this bookmarks folder.
	 *
	 * @param parent container model ID of this bookmarks folder
	 */
	public void setParentContainerModelId(long parentContainerModelId);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(BookmarksFolder bookmarksFolder);

	public int hashCode();

	public CacheModel<BookmarksFolder> toCacheModel();

	public BookmarksFolder toEscapedModel();

	public BookmarksFolder toUnescapedModel();

	public String toString();

	public String toXmlString();
}