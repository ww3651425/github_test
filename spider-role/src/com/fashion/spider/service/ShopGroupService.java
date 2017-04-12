package com.fashion.spider.service;

import java.util.List;

import com.fashion.spider.db.BaseDao;
import com.fashion.spider.model.ShopGroup;

public class ShopGroupService extends BaseDao<ShopGroup>{
	public List<ShopGroup> getparentShopGroupId(String db,int shopGroupId) {
		String sql = "select parentShopGroupId from shopgroup where shopGroupId = ? " ;
		return query_entity(db, sql, shopGroupId);
	}
	//通过shopgroupId得到parentUnitId
	public List<ShopGroup> getparentUnitId(String db,int shopGroupId) {
		String sql = "select parentUnitId from shopgroup where shopGroupId = ? " ;
		return query_entity(db, sql, shopGroupId);
	}
	public List<ShopGroup> getShopGroup(String db) {
		String sql = "SELECT * from shopgroup where unitType=-5 and parentUnitId is not null and shopGroupStatus=0" ;
		return query_entity(db, sql, null);
	}
	public List<ShopGroup> getGroup(String db){
		String sql="SELECT * from shopgroup where unitType=-10 and parentUnitId is not null and shopGroupStatus=0";
		return query_entity(db, sql, null);
	}
}
