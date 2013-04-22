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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v6_2_0.util.GroupTable;

import java.sql.SQLException;

/**
 * @author Hugo Huijser
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type Group_ friendlyURL VARCHAR(255) null");

			DB db = DBFactoryUtil.getDB();

			String dbType = db.getType();

			if (dbType.equals(DB.TYPE_DB2) || dbType.equals(DB.TYPE_DERBY) ||
				dbType.equals(DB.TYPE_ORACLE)) {
					runSQL("alter_column_type Group_ typeSettings CLOB null");
			}
		}
		catch (SQLException sqle) {
			upgradeTable(
				GroupTable.TABLE_NAME, GroupTable.TABLE_COLUMNS,
				GroupTable.TABLE_SQL_CREATE, GroupTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}