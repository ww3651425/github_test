package com.fashion.spider.service;

import java.util.List;

import com.fashion.spider.db.BaseDao;
import com.fashion.spider.model.HrmDepartment;

public class HrDepartmentService extends BaseDao<HrmDepartment> {
	public List<HrmDepartment> getHrdepartmentId(String db,
			String DEPARTMENTCODE) {
		String sql = "select * from ECOLOGY.HrmDepartment where DEPARTMENTCODE=? ";
		return query_entity(db, sql, DEPARTMENTCODE);
	}
}
