package com.fashion.spider.service;

import com.fashion.spider.db.BaseDao;
import com.fashion.spider.model.MatrixTable;

public class MatrixTableService extends BaseDao<MatrixTable> {
	public int updateMatrixId(String db, String bmfzr, String bmms, String id) {
		String sql = "update ECOLOGY.Matrixtable_2 set BMFZR=?  , BMMS=?   where ID=? ";
		return update(db, sql, bmfzr, bmms, id);
	}
}
