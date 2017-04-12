package com.fashion.spider.service;

import java.util.List;

import com.fashion.spider.db.BaseDao;
import com.fashion.spider.model.HrmRoleMember;

public class HrRoleMemeberService extends BaseDao<HrmRoleMember> {
	public int insertRoleMember(String db, int ROLEID,
			int RESOURCEID, int ROLELEVEL) {
		String sql = "insert into ECOLOGY.HrmRoleMembers (ROLEID,RESOURCEID,ROLELEVEL) values (?,?,?) ";
		return update(db, sql, ROLEID, RESOURCEID, ROLELEVEL);
	}
	public List<HrmRoleMember> getHrRoleMember(String db,int RESOUREID) {
		String sql = "SELECT * from ECOLOGY.HrmRoleMembers where RESOURCEID=? ";
		return query_entity(db, sql, RESOUREID);
	}
	public int deleteRoleMber(String db,int RESOURCEID,int roleId) {
		String sql = "delete from  ECOLOGY.HrmRoleMembers where RESOURCEID=? and ROLEID=?";
		return update(db, sql, RESOURCEID,roleId);
	}
}
