package com.fashion.spider.model;

public class PostHistory {
	private Integer userId;//用户ID
	private Integer postId;//职位ID	
	private Integer rankId;//职等编号
	private Integer unitId;//部门编号
	private Integer shopGroupId;//门店编号
	private Integer postBeginType;
	private Integer isCurrentPost;
	private Integer pluralism;
	private Integer levelcode;
	private Integer typepost;
	private Integer isController;
	private Integer postEndType;

	public Integer getPostEndType() {
		return postEndType;
	}

	public void setPostEndType(Integer postEndType) {
		this.postEndType = postEndType;
	}

	public Integer getIsController() {
		return isController;
	}

	public void setIsController(Integer isController) {
		this.isController = isController;
	}

	public Integer getTypepost() {
		return typepost;
	}

	public void setTypepost(Integer typepost) {
		this.typepost = typepost;
	}

	public Integer getLevelcode() {
		return levelcode;
	}

	public void setLevelcode(Integer levelcode) {
		this.levelcode = levelcode;
	}

	public Integer getIsCurrentPost() {
		return isCurrentPost;
	}

	public Integer getPluralism() {
		return pluralism;
	}

	public void setPluralism(Integer pluralism) {
		this.pluralism = pluralism;
	}

	public void setIsCurrentPost(Integer isCurrentPost) {
		this.isCurrentPost = isCurrentPost;
	}

	public Integer getPostBeginType() {
		return postBeginType;
	}

	public void setPostBeginType(Integer postBeginType) {
		this.postBeginType = postBeginType;
	}

	public Integer getShopGroupId() {
		return shopGroupId;
	}

	public void setShopGroupId(Integer shopGroupId) {
		this.shopGroupId = shopGroupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getRankId() {
		return rankId;
	}

	public void setRankId(Integer rankId) {
		this.rankId = rankId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
}
