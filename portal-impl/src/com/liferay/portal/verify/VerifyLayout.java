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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Kenneth Chang
 */
public class VerifyLayout extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyFriendlyURL();
		verifyUuid();
	}

	protected void updateLayoutUuid(
		String tableName, String primaryKeyColumn, long primaryKey, String uuid)
		throws Exception {

		StringBundler sb = new StringBundler();
		sb.append("update ");
		sb.append(tableName);
		sb.append(" set ");
		sb.append(tableName);
		sb.append(".layoutUuid = " + uuid + " where ");
		sb.append(tableName + "." + primaryKeyColumn);
		sb.append("=" + primaryKey);

		runSQL(sb.toString());
	}

	protected void verifyFriendlyURL() throws Exception {
		List<Layout> layouts =
			LayoutLocalServiceUtil.getNullFriendlyURLLayouts();

		for (Layout layout : layouts) {
			String friendlyURL = StringPool.SLASH + layout.getLayoutId();

			LayoutLocalServiceUtil.updateFriendlyURL(
				layout.getPlid(), friendlyURL);
		}
	}

	protected void verifyUuid() throws Exception {
		verifyUuid("AssetEntry", "entryId");
		verifyUuid("JournalArticle", "id_");

		StringBundler sb = new StringBundler(3);

		sb.append("update Layout set uuid_ = sourcePrototypeLayoutUuid where ");
		sb.append("sourcePrototypeLayoutUuid != '' and ");
		sb.append("uuid_ != sourcePrototypeLayoutUuid");

		runSQL(sb.toString());
	}

	protected void verifyUuid(String tableName, String primaryKeyColumn)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBundler sb = new StringBundler(9);

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			sb.append("select ");
			sb.append(tableName + "." + primaryKeyColumn + ", ");
			sb.append("Layout.sourcePrototypeLayoutUuid");
			sb.append(" from ");
			sb.append(tableName);
			sb.append(" inner join Layout on ");
			sb.append(tableName);
			sb.append(".layoutUuid = Layout.uuid_ ");
			sb.append("where ");
			sb.append("Layout.sourcePrototypeLayoutUuid != '' and ");
			sb.append("Layout.uuid_ != Layout.sourcePrototypeLayoutUuid");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				String sourcePrototypeLayoutUuid = rs.getString(
					"sourcePrototypeLayoutUuid");
				long primaryKey = rs.getLong(primaryKeyColumn);
				updateLayoutUuid(
					tableName, primaryKeyColumn, primaryKey,
					sourcePrototypeLayoutUuid);
			}

		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}