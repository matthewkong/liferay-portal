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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.ListTypeServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class ContactImpl extends ContactBaseImpl {

	public ContactImpl() {
	}

	public String getFullName() {
		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		return fullNameGenerator.getFullName(
			getFirstName(), getMiddleName(), getLastName());
	}

	public String getPrefix(Contact contact) throws Exception {
		if (contact.getPrefixId() == 0) {
			return StringPool.BLANK;
		}

		ListType listType = null;

		try {
			listType = ListTypeServiceUtil.getListType(contact.getPrefixId());
		}
		catch (Exception e) {
			throw e;
		}

		String prefix = listType.getName();

		if (prefix.equalsIgnoreCase("dr")) {
			prefix = "Dr.";
		}
		else if (prefix.equalsIgnoreCase("mr")) {
			prefix = "Mr.";
		}
		else if (prefix.equalsIgnoreCase("mrs")) {
			prefix = "Mrs.";
		}
		else if (prefix.equalsIgnoreCase("ms")) {
			prefix = "Ms.";
		}
		else {
			prefix = StringPool.BLANK;
		}

		return prefix;
	}

	public String getSuffix(Contact contact) throws Exception {
		if (contact.getSuffixId() == 0) {
			return StringPool.BLANK;
		}

		ListType listType = null;

		try {
			listType = ListTypeServiceUtil.getListType(contact.getSuffixId());
		}
		catch (Exception e) {
			throw e;
		}

		String suffix = listType.getName();

		if (suffix.equalsIgnoreCase("ii")) {
			suffix = "II";
		}
		else if (suffix.equalsIgnoreCase("iii")) {
			suffix = "III";
		}
		else if (suffix.equalsIgnoreCase("iv")) {
			suffix = "IV";
		}
		else if (suffix.equalsIgnoreCase("jr")) {
			suffix = "Jr.";
		}
		else if (suffix.equalsIgnoreCase("phd")) {
			suffix = "Phd.";
		}
		else if (suffix.equalsIgnoreCase("sr")) {
			suffix = "Sr.";
		}
		else {
			suffix = StringPool.BLANK;
		}

		return suffix;
	}

	public boolean isUser() {
		return hasClassName(User.class);
	}

	protected boolean hasClassName(Class<?> clazz) {
		long classNameId = getClassNameId();

		if (classNameId == PortalUtil.getClassNameId(clazz)) {
			return true;
		}
		else {
			return false;
		}
	}

}