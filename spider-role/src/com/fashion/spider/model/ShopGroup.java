package com.fashion.spider.model;

public class ShopGroup {
   private Integer  shopGroupId;
   private String name;
   private Integer parentShopGroupId;
   private Integer parentUnitId;
public Integer getShopGroupId() {
	return shopGroupId;
}
public void setShopGroupId(Integer shopGroupId) {
	this.shopGroupId = shopGroupId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getParentShopGroupId() {
	return parentShopGroupId;
}
public void setParentShopGroupId(Integer parentShopGroupId) {
	this.parentShopGroupId = parentShopGroupId;
}
public Integer getParentUnitId() {
	return parentUnitId;
}
public void setParentUnitId(Integer parentUnitId) {
	this.parentUnitId = parentUnitId;
}
}
