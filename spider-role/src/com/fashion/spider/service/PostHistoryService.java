package com.fashion.spider.service;

import java.util.List;

import com.fashion.spider.db.BaseDao;
import com.fashion.spider.model.PostHistory;

public class PostHistoryService extends BaseDao<PostHistory> {
	private static final String TABLE_NAME = "posthistory";

	public List<PostHistory> getAllPosthistory(String db) {
		String sql = "SELECT * from posthistory";
		return query_entity(db, sql, null);
	}

	public List<PostHistory> getAllPosthistoryController(String db) {
		// String
		// sql="SELECT ph.* ,pl.typepost from posthistory ph INNER JOIN postlevel pl ON ph.postId=pl.postId WHERE (ph.isCurrentPost <>2 AND ph.postid in (SELECT postId from post WHERE isController=1 ) and pl.typepost<>1)";)
		String sql = "SELECT * from posthistory WHERE (isCurrentPost <>2 AND postid in (SELECT postId from post WHERE isController=1 ))  ";
		return query_entity(db, sql, null);
	}

	/**
	 * 根据postId获取Posthistory数据
	 * 
	 * @param db
	 * @param params
	 * @return
	 */
	public List<PostHistory> getUnitId(String db, Object... params) {
		String sql = "select unitId,shopGroupId from posthistory where postId=?";
		return query_entity(db, sql, params);

	}

	/**
	 * 通过userId得到部门编号或店组编号
	 * 
	 * @param db
	 * @param userId
	 * @return
	 */
	public List<PostHistory> getunitIdorshopgroupIdbyuserId(String db,
			int userId) {
		// String sql
		// ="select unitId,shopGroupId,postBeginType,postId,isCurrentPost from posthistory where userId=? ";
		String sql = "select ph.unitId,ph.shopGroupId,ph.postId,ph.isCurrentPost,ph.pluralism,ph.postEndType,p.isController,p.levelcode FROM posthistory ph INNER JOIN postlevel p ON ph.postId = p.postId where userId=? ";
		return query_entity(db, sql, userId);
	}

	public List<PostHistory> GetunitIdorshopgroupIdbyuserId(String db,
			int userId) {
		String sql = "select ph.userId,ph.unitId,ph.shopGroupId,ph.postBeginType,ph.postId,ph.isCurrentPost,p.levelcode FROM posthistory ph INNER JOIN postlevel p ON ph.postId = p.postId where p.typepost=1 AND ph.isCurrentPost=1 and userId=? ";
		return query_entity(db, sql, userId);
	}

	public List<PostHistory> GetunitIdorshopgroupId(String db, int userId) {
		String sql = "select ph.userId,ph.unitId,ph.shopGroupId,ph.postBeginType,ph.postId,ph.isCurrentPost FROM posthistory ph WHERE ph.postId=4910 and ph.isCurrentPost = 1 AND userId= ? ";
		return query_entity(db, sql, userId);
	}

	/**
	 * 通过postId得到部门编号或店组编号
	 * 
	 * @param db
	 * @param postId
	 * @return
	 */
	public List<PostHistory> getunitId(String db, int postId) {
		String sql = "select unitId,shopGroupId from " + TABLE_NAME
				+ " where postId=?";
		return query_entity(db, sql, postId);

	}

	/**
	 * 通过userId得到职务编号
	 * 
	 * @param db
	 * @param userId
	 * @return
	 */
	public List<PostHistory> getpostIdbyuserId(String db, int userId) {
		String sql = "select postId,postBeginType ,isCurrentPost from "
				+ TABLE_NAME
				+ " where userId=? GROUP BY postBeginDate HAVING MAX(postBeginDate)";
		return query_entity(db, sql, userId);

	}

	public List<PostHistory> getuserId(String db, int parentShopGroupId) {
		String sql = "SELECT ph.* FROM posthistory ph WHERE unitId in (SELECT DISTINCT parentUnitId FROM shopgroup WHERE parentShopGroupId = ?) AND isCurrentPost=1 and (postId=5500 or postId=5300)";
		return query_entity(db, sql, parentShopGroupId);

	}

	public List<PostHistory> getuserIdbyunitId(String db, int unitId) {
		String sql = "SELECT * FROM posthistory WHERE unitId in (SELECT DISTINCT parentUnitId FROM shopgroup WHERE shopGroupId = ?) AND isCurrentPost=1 and (postId=5500 or postId=5300)";
		return query_entity(db, sql, unitId);

	}

	public List<PostHistory> GetuserIdbyunitId(String db, int unitId) {
		String sql = "SELECT * FROM posthistory WHERE unitId in (SELECT DISTINCT parentUnitId FROM shopgroup WHERE shopGroupId = ?) AND isCurrentPost=1 and (postId=6600 or postId=6300)";
		return query_entity(db, sql, unitId);

	}

	public List<PostHistory> getAlluserIdbyunitId(String db, int unitId) {
		String sql = "SELECT * FROM posthistory WHERE unitId in (SELECT parentUnitId FROM organization WHERE unitId = ?) AND isCurrentPost=1 and (postId=6600 or postId=6300)";
		return query_entity(db, sql, unitId);

	}

	// 通过部门找出所有的用户
	public List<PostHistory> GetAlluserIdbyunitId(String db, int unitId) {
		String sql = "SELECT * FROM posthistory WHERE unitId =? ";
		return query_entity(db, sql, unitId);

	}

	// 找出商圈里面的总监
	public List<PostHistory> getZjuserIdbyshopGroupId(String db, int shopGroupId) {
		String sql = "SELECT * FROM posthistory WHERE unitId IN( SELECT parentUnitId FROM shopgroup WHERE unitType = -10 AND shopGroupId =? ) and postId = 5910";
		return query_entity(db, sql, shopGroupId);

	}

	// 找出太屋部门里面的副总
	public List<PostHistory> getFzuserIdbyshopGroupId(String db, int shopGroupId) {
		String sql = "SELECT * FROM posthistory WHERE unitId IN(SELECT parentUnitId FROM shopgroup WHERE shopGroupId IN( SELECT parentShopGroupId FROM shopgroup WHERE unitType = -10 AND shopGroupId =? ) ) and postId = 6810 and isCurrentPost=1";
		return query_entity(db, sql, shopGroupId);

	}

	// 找出菁英里面的副总
	public List<PostHistory> getJYFzUser(String db, int shopGroupId) {
		String sql = "SELECT * FROM posthistory WHERE unitId IN(SELECT parentUnitId FROM shopgroup WHERE shopGroupId IN( SELECT parentShopGroupId FROM shopgroup WHERE unitType = -10 AND shopGroupId =? ) ) and postId in(6810,6800) and isCurrentPost=1";
		return query_entity(db, sql, shopGroupId);
	}

	// 根据门店ID 获取太屋所属门店的秘书
	public List<PostHistory> getSecretary(String db, int shopGroupId) {
		// String sql =
		// "SELECT userId,postId,rankId,postBeginDate,postBeginType,isCurrentPost,unitId,shopGroupId,pluralism from posthistory where shopGroupId =? and (postId=4700 or postId=5401) AND postBeginDate=(SELECT MIN(postBeginDate)pluralism from posthistory where shopGroupId =? and (postId=4700 or postId=5401) and postEndDate is null AND (pluralism=0 or pluralism=1))";

		String sql = "SELECT ph.userId,ph.postId,ph.rankId,ph.postBeginDate,ph.postBeginType,ph.isCurrentPost,ph.unitId,ph.shopGroupId,ph.pluralism from posthistory as ph INNER JOIN `user` as u on ph.userId=u.userId where shopGroupId =? and (postId=4700 or postId=5401)and u.status in(1,5) and postEndDate is null";
		return query_entity(db, sql, shopGroupId);
	}

	// 根据门店ID 获取菁英所属门店的秘书
	public List<PostHistory> getJySecretary(String db, int shopGroupId) {
		// String sql =
		// "SELECT userId,postId,rankId,postBeginDate,postBeginType,isCurrentPost,unitId,shopGroupId,pluralism from posthistory where shopGroupId =? and (postId=4700 or postId=5401) AND postBeginDate=(SELECT MIN(postBeginDate)pluralism from posthistory where shopGroupId =? and (postId=4700 or postId=5401) and postEndDate is null AND (pluralism=0 or pluralism=1))";
		String sql = "SELECT ph.userId,ph.postId,ph.rankId,ph.postBeginDate,ph.postBeginType,ph.isCurrentPost,ph.unitId,ph.shopGroupId,ph.pluralism from posthistory as ph INNER JOIN `user` as u on ph.userId=u.userId where shopGroupId =? and (postId=4700 or postId=4701)and u.status in(1,5) and postEndDate is null";
		return query_entity(db, sql, shopGroupId);
	}

	// 根据部门ID获取部门领导
	public List<PostHistory> getLead(String db, int unitId) {
		String sql = "SELECT userId,postId,rankId,postBeginDate,postBeginType,isCurrentPost,unitId,shopGroupId,pluralism from posthistory where unitId=? and postId= (SELECT MAX(postId) from posthistory where unitId=? and postEndDate is null and postBeginType<>6 ) and postEndDate is null and postId<>5500 and postId<>5300 and isCurrentPost=1";
		return query_entity(db, sql, unitId, unitId);
	}

	// 根据Id获取太屋门店领导
	public List<PostHistory> getShopLead(String db, int shopGroupId) {
		String sql = "SELECT userId,postId,rankId,postBeginDate,postBeginType,isCurrentPost,unitId,shopGroupId,pluralism from posthistory where shopGroupId=? and postId= (SELECT MAX(postId) from posthistory where shopGroupId=? and postEndDate is null and postBeginType<>6 ) and postEndDate is null and isCurrentPost=1";
		return query_entity(db, sql, shopGroupId, shopGroupId);
	}

	// 根据Id获取菁英门店领导
	public List<PostHistory> getJYShopLead(String db, int shopGroupId) {
		String sql = "SELECT userId,postId,rankId,postBeginDate,postBeginType,isCurrentPost,unitId,shopGroupId,pluralism from posthistory where shopGroupId=? and postId= (SELECT MAX(ph.postId) from posthistory as ph INNER JOIN post as p on ph.postId=p.postId where shopGroupId=? and postEndDate is null and postBeginType<>6 and p.isController=1) and postEndDate is null and isCurrentPost=1";
		return query_entity(db, sql, shopGroupId, shopGroupId);
	}

	// 判断领导是否为正职or兼职
	public List<PostHistory> getpluralism(String db, int userId, int unitId) {
		String sql = "select * from posthistory where userId=? and (unitId=? or shopGroupId=?) and isCurrentPost =1";
		return query_entity(db, sql, userId, unitId, unitId);

	}

	// 得到领导的所在部门
	public List<PostHistory> getOrgId(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId= (SELECT parentunitId from organization where unitId=?) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	public List<PostHistory> getOrgzationId(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and (unitId= ? or shopGroupId=?) ";
		return query_entity(db, sql, userId, unitId, unitId);

	}

	// 第一层
	public List<PostHistory> getOrgid(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId= (SELECT parentunitId from shopgroup where shopGroupId=?)and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 第二层门店
	public List<PostHistory> getOrgids(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId= (SELECT parentunitId from organization where unitId=(SELECT parentunitId from shopgroup where shopGroupId=?)) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 第三层门店
	public List<PostHistory> getShopThreeids(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId=(select parentunitId from organization where unitId= (SELECT parentunitId from organization where unitId=(SELECT parentunitId from shopgroup where shopGroupId=?))) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 第四层门店
	public List<PostHistory> getShopFourids(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId=(select parentunitId from organization where unitId=(select parentunitId from organization where unitId= (SELECT parentunitId from organization where unitId=(SELECT parentunitId from shopgroup where shopGroupId=?)))) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 第五层门店
	public List<PostHistory> getShopFiveids(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId=(select parentunitId from organization where unitId=(select parentunitId from organization where unitId=(select parentunitId from organization where unitId= (SELECT parentunitId from organization where unitId=(SELECT parentunitId from shopgroup where shopGroupId=?))))) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 第六层门店

	public List<PostHistory> getShopSixids(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId=(select parentunitId from organization where unitId=(select parentunitId from organization where unitId=(select parentunitId from organization where unitId=(select parentunitId from organization where unitId= (SELECT parentunitId from organization where unitId=(SELECT parentunitId from shopgroup where shopGroupId=?)))))) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 找一层
	public List<PostHistory> getParentOrg(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId= (SELECT parentunitId from organization where unitId=?) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 找两层
	public List<PostHistory> getmangerId(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId= (SELECT parentunitId from organization where unitId=(SELECT parentunitId from organization where unitId=?)) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 找三层

	public List<PostHistory> getThreeId(String db, int userId, int unitId) {
		String sql = "SELECT * from posthistory where userId=? and unitId=(select parentUnitId from organization where unitId= (SELECT parentunitId from organization where unitId=(SELECT parentunitId from organization where unitId=?))) and isCurrentPost=1";
		return query_entity(db, sql, userId, unitId);

	}

	// 判断秘书领导是否为正职or兼职
	public List<PostHistory> getplur(String db, int userId, int unitId) {
		String sql = "select * from posthistory where (userId=? and unitId=? or shopGroupId=?) and isCurrentPost =1";
		return query_entity(db, sql, userId, unitId, unitId);

	}

	// 判断店长领导是否为正职或兼职
	public List<PostHistory> getshopplur(String db, int userId, int shopGroupId) {
		String sql = "select * from posthistory where (userId=? and unitId=? or shopGroupId=?) and isCurrentPost =1";
		return query_entity(db, sql, userId, shopGroupId, shopGroupId);

	}

	// 获取所有店长
	public List<PostHistory> getSupervisor(String db) {
		String sql = "SELECT u.userId,u.name,ph.shopgroupid from `user` as u INNER JOIN posthistory as ph on u.userId=ph.userId where (postId=4910 or postId=4900 or postId=4092 or postId=4903 or postId=4901) and u.status in (1,5) and ph.isCurrentPost=1 and ph.pluralism=0";
		return query_entity(db, sql, null);
	}

	// 获取所有幕僚
	public List<PostHistory> getSuperintendent(String db) {
		String sql = "SELECT u.userId,u.name,ph.unitId from `user` as u INNER JOIN posthistory as ph on u.userId=ph.userId INNER JOIN post as p on ph.postId=p.postId where p.postType=0 and u.status in (1,5) and ph.isCurrentPost=1 and ph.pluralism=0";
		return query_entity(db, sql, null);
	}

	// 获取所有的幕僚主管
	public List<PostHistory> getCharge(String db) {
		String sql = "SELECT u.userId,u.name,ph.unitId from `user` as u INNER JOIN posthistory as ph on u.userId=ph.userId INNER JOIN post as p on ph.postId=p.postId where p.postType=0 and u.status in (1,5) and ph.isCurrentPost=1 and ph.pluralism=0 and p.isController=1 and ph.shopGroupId is null";
		return query_entity(db, sql, null);
	}

	// 获取所有总监
	public List<PostHistory> getDirector(String db) {
		String sql = "SELECT u.userId,u.name,ph.unitId from `user` as u INNER JOIN posthistory as ph on u.userId=ph.userId INNER JOIN post as p on ph.postId=p.postId where p.postType=1 and u.status in (1,5) and ph.isCurrentPost=1 and ph.pluralism=0 and p.isController=1 and ph.shopGroupId is null and (ph.postId=5910 or ph.postId=5920)";
		return query_entity(db, sql, null);
	}
}
