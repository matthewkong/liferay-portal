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
import com.liferay.portal.upgrade.v6_2_0.util.LayoutSetBranchTable;

import java.sql.SQLException;

/**
 * @author Harrison Schueler
 */
public class UpgradeLayoutSetBranch extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (dbType.equals(DB.TYPE_DB2) || dbType.equals(DB.TYPE_DERBY) ||
			dbType.equals(DB.TYPE_ORACLE)) {
				try {
					runSQL("alter_column_type LayoutSetBranch css CLOB null");
				}
			catch (SQLException sqle) {
				upgradeTable(
					LayoutSetBranchTable.TABLE_NAME,
					LayoutSetBranchTable.TABLE_COLUMNS,
					LayoutSetBranchTable.TABLE_SQL_CREATE,
					LayoutSetBranchTable.TABLE_SQL_ADD_INDEXES);
			}
		}
	}

}