package com.fashion.spider.service;

import java.util.List;

import com.fashion.spider.db.BaseDao;
import com.fashion.spider.model.HrmResource;

public class HrmResourceService extends BaseDao<HrmResource>{
	//根据人员workcode获取oracle数据库人员   有可能找出兼职
	public List<HrmResource> getHrResoureId(String db,String workcode) {
		String sql = "select ID,LOGINID,password,lastname,workcode from ECOLOGY.HrmResource  where WORKCODE=? ";
		return query_entity(db, sql, workcode);
	}
	//根据人员loginid获取oracle数据库人员   只找出正职
	public List<HrmResource> getHrSecretaryId(String db,String LOGINID) {
		String sql = "select ID,LOGINID,password,lastname,workcode from ECOLOGY.HrmResource  where LOGINID=? ";
		return query_entity(db, sql, LOGINID);
	}
}
