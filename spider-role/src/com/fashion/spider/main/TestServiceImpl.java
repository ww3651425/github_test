package com.fashion.spider.main;

import java.util.List;
import com.fashion.spider.model.HrmDepartment;
import com.fashion.spider.model.HrmResource;
import com.fashion.spider.model.HrmRoleMember;
import com.fashion.spider.model.PostHistory;
import com.fashion.spider.model.ShopGroup;
import com.fashion.spider.service.HrDepartmentService;
import com.fashion.spider.service.HrRoleMemeberService;
import com.fashion.spider.service.HrmResourceService;
import com.fashion.spider.service.MatrixTableService;
import com.fashion.spider.service.PostHistoryService;
import com.fashion.spider.service.ShopGroupService;

public class TestServiceImpl {
	private int shopcount = 0;
	private int groupcount = 0;
	private int rolecount = 0;
	private int dianzhang = 0;
	private int director = 0;
	/* 矩阵 */
	private String hrSercre;
	private String hrlead;
	private String secreId;

	private HrmResourceService HrResourser = new HrmResourceService();
	private HrDepartmentService hrdepartService = new HrDepartmentService();
	private MatrixTableService matrixService = new MatrixTableService();
	/* 实例化对象 */
	private PostHistoryService posthistoryservice = new PostHistoryService();
	private ShopGroupService shopGroupSer = new ShopGroupService();
	private HrRoleMemeberService roleMemberSer = new HrRoleMemeberService();

	/* 获取所有门店 不包含组 */
	public void getAllshop() {
		List<ShopGroup> shopList = shopGroupSer.getShopGroup("group");
		for (ShopGroup shopGroup : shopList) {
			List<PostHistory> phList = posthistoryservice.getSecretary("group",
					shopGroup.getShopGroupId());
			if (phList == null || phList.isEmpty()
					|| phList.get(0).getUserId() == null) {
				System.out.println("此门店没有秘书" + shopGroup.getShopGroupId());
				hrSercre = "";
			} else if (phList.size() > 1) {
				for (int i = 0; i < phList.size(); i++) {
					if (phList.get(i).getPostBeginType() != 6) {
						/* 拿到秘书userId */
						Integer secre = phList.get(i).getUserId();
						/* 根据秘书userID获取hr表格对应id */
						List<HrmResource> resourList = HrResourser
								.getHrResoureId("group3",
										"11" + secre.toString());
						if (resourList == null || resourList.isEmpty()) {
							System.out.println("对应的秘书在中间表有在OA表里面没有" + secre);
							hrSercre = "";
						} else {
							hrSercre = resourList.get(0).getID().toString();
						}
						break;
					} else {
						continue;
					}
				}
			} else {
				/* 拿到秘书userId */
				Integer secre = phList.get(0).getUserId();
				/* 根据秘书userID获取hr表格对应id */
				List<HrmResource> resourList = HrResourser.getHrResoureId(
						"group3", "11" + secre.toString());
				if (resourList == null || resourList.isEmpty()) {
					System.out.println("对应的秘书在中间表有在OA表里面没有" + secre);
					hrSercre = "";
				} else {
					hrSercre = resourList.get(0).getID().toString();
				}

			}
			List<PostHistory> ogList = posthistoryservice.getLead("group",
					shopGroup.getParentUnitId());
			if (ogList == null || ogList.isEmpty()
					|| ogList.get(0).getUserId() == null) {
				System.out.println("此门店所属部门没有领导" + shopGroup.getParentUnitId());
				hrlead = "";
				/* 存在兼职跟正职两条记录取正职 */
			} else if (ogList.size() > 1) {
				for (int i = 0; i < ogList.size(); i++) {
					if (ogList.get(i).getPluralism() == 0) {
						/* 拿到部门负责人的userid */
						Integer lead = ogList.get(i).getUserId();
						List<HrmResource> leadhrList = HrResourser
								.getHrResoureId("group3",
										"11" + lead.toString());
						if (leadhrList == null || leadhrList.isEmpty()) {
							System.out.println("领导存在于中间表不存在于OA数据库"
									+ lead.toString());
							hrlead = "";
						} else {
							/* hr中间部门负责人 */
							hrlead = leadhrList.get(0).getID().toString();
						}
						break;
					} else {
						continue;
					}
				}
				/* 一条记录 */
			} else {
				/* 拿到部门负责人的userid */
				Integer lead = ogList.get(0).getUserId();
				List<HrmResource> leadhrList = HrResourser.getHrResoureId(
						"group3", "11" + lead.toString());
				/* hr中间部门负责人 */
				hrlead = leadhrList.get(0).getID().toString();
			}
			List<HrmDepartment> departList = hrdepartService.getHrdepartmentId(
					"group3", "11" + shopGroup.getShopGroupId().toString());

			/* 根据门店获取hr数据库的id */
			if (departList == null || departList.isEmpty()) {
				System.out.println("对应的门店在中间表有在hr表里面没有"
						+ shopGroup.getShopGroupId());
				secreId = "0";
			} else {
				secreId = departList.get(0).getID().toString();
			}

			int counts = matrixService.updateMatrixId("group3", hrlead,
					hrSercre, secreId);
			shopcount++;
		}

		System.out.println("成功修改门店矩阵" + shopcount);
	}

	/* 获取所有组别 */
	public void getShopSer() {
		List<ShopGroup> getGroup = shopGroupSer.getGroup("group");
		for (ShopGroup group : getGroup) {
			List<PostHistory> groupList = posthistoryservice.getSecretary(
					"group", group.getParentShopGroupId());
			if (groupList == null || groupList.isEmpty()
					|| groupList.get(0).getUserId() == null) {
				System.out.println("此门店没有秘书" + group.getParentShopGroupId());
				hrSercre = "";
			} else if (groupList.size() > 1) {
				for (int i = 0; i < groupList.size(); i++) {
					if (groupList.get(i).getPostBeginType() != 6) {
						/* 拿到秘书userId */
						Integer secre = groupList.get(i).getUserId();
						/* 根据秘书userID获取hr表格对应id */
						List<HrmResource> resourIdList = HrResourser
								.getHrResoureId("group3",
										"11" + secre.toString());
						if (resourIdList == null || resourIdList.isEmpty()) {
							System.out.println("对应的秘书在中间表有在OA表里面没有" + secre);
							hrSercre = "";
						} else {
							hrSercre = resourIdList.get(0).getID().toString();
						}
						break;
					} else {
						continue;
					}
				}
			} else {
				/* 拿到秘书userId */
				Integer secre = groupList.get(0).getUserId();
				/* 根据秘书userID获取hr表格对应id */
				List<HrmResource> resourIdList = HrResourser.getHrResoureId(
						"group3", "11" + secre.toString());
				if (resourIdList == null || resourIdList.isEmpty()) {
					System.out.println("对应的秘书在中间表有在OA表里面没有" + secre);
					hrSercre = "";
				} else {
					hrSercre = resourIdList.get(0).getID().toString();
				}
			}
			List<PostHistory> shopGroupLeadList = posthistoryservice
					.getShopLead("group", group.getShopGroupId());
			if (shopGroupLeadList == null || shopGroupLeadList.isEmpty()
					|| shopGroupLeadList.get(0).getUserId() == null) {
				System.out.println("此门店没有店长:" + group.getShopGroupId() + "门店:"
						+ group.getName() + "找总监:" + group.getParentUnitId());
				List<PostHistory> getDirector = posthistoryservice.getLead(
						"group", group.getParentUnitId());
				if (getDirector == null || getDirector.isEmpty()
						|| getDirector.get(0).getUserId() == null) {
					System.out.println("此商圈没有总监:" + group.getParentUnitId()
							+ "找部门副总");
					List<PostHistory> getAssistant = posthistoryservice
							.getFzuserIdbyshopGroupId("group",
									group.getShopGroupId());
					Integer lead = getAssistant.get(0).getUserId();
					List<HrmResource> leadhrList = HrResourser.getHrResoureId(
							"group3", "11" + lead.toString());
					/* hr中间部门负责人 */
					hrlead = leadhrList.get(0).getID().toString();
				} else {
					Integer lead = getDirector.get(0).getUserId();
					List<HrmResource> leadhrList = HrResourser.getHrResoureId(
							"group3", "11" + lead.toString());
					/* hr中间部门负责人 */
					hrlead = leadhrList.get(0).getID().toString();
				}
			} else {
				/* 拿到部门负责人的userid */
				Integer lead = shopGroupLeadList.get(0).getUserId();
				List<HrmResource> leadhrList = HrResourser.getHrResoureId(
						"group3", "11" + lead.toString());
				/* hr中间部门负责人 */
				hrlead = leadhrList.get(0).getID().toString();
			}
			List<HrmDepartment> departList = hrdepartService.getHrdepartmentId(
					"group3", "11" + group.getShopGroupId().toString());
			/* 根据门店获取hr数据库的id */
			if (departList == null || departList.isEmpty()) {
				System.out.println("对应的组别在中间表有在hr表里面没有"
						+ group.getShopGroupId());
				secreId = "0";
			} else {
				secreId = departList.get(0).getID().toString();
			}
			int shopCounts = matrixService.updateMatrixId("group3", hrlead,
					hrSercre, secreId);
			groupcount++;
		}
		System.out.println("成功修改组别矩阵" + groupcount);
	}

	//
	/* 修改所有店长角色 获取所有店长 */
	public void updateShop() {

		List<PostHistory> Listrole = posthistoryservice.getSupervisor("group");
		for (int i = 0; i < Listrole.size(); i++) {
			List<HrmResource> hrRole = HrResourser.getHrSecretaryId("group3",
					"11" + Listrole.get(i).getUserId());
			if (hrRole == null || hrRole.isEmpty()) {
				System.out
						.println("在OA表里面没有找到此人" + Listrole.get(i).getUserId());
				roleMemberSer.insertRoleMember("group3", 81, 0, 2);
			} else {
				List<HrmRoleMember> ListMember = roleMemberSer.getHrRoleMember(
						"group3", hrRole.get(0).getID());
				if (ListMember == null || ListMember.isEmpty()) {
					roleMemberSer.insertRoleMember("group3", 81, hrRole.get(0)
							.getID(), 2);
				} else if (ListMember.size() >= 1) {
					roleMemberSer.deleteRoleMber("group3", hrRole.get(0)
							.getID(), 81);
					roleMemberSer.insertRoleMember("group3", 81, hrRole.get(0)
							.getID(), 2);
				}

			}
			dianzhang++;
		}
		System.out.println("店长---->" + dianzhang);
	}

	/* 角色所有幕僚 */
	public void updateSuperintendent() {
		List<PostHistory> intendent = posthistoryservice
				.getSuperintendent("group");
		for (int i = 0; i < intendent.size(); i++) {
			List<HrmResource> hrintent = HrResourser.getHrSecretaryId("group3",
					"11" + intendent.get(i).getUserId());
			if (hrintent == null || hrintent.isEmpty()) {
				System.out.println("在OA表里面没有找到此人"
						+ intendent.get(i).getUserId());
				roleMemberSer.insertRoleMember("group3", 141, 0, 2);
			} else {
				List<HrmRoleMember> ListMember = roleMemberSer.getHrRoleMember(
						"group3", hrintent.get(0).getID());
				if (ListMember == null || ListMember.isEmpty()) {
					roleMemberSer.insertRoleMember("group3", 141,
							hrintent.get(0).getID(), 2);
				} else if (ListMember.size() >= 1) {
					roleMemberSer.deleteRoleMber("group3", hrintent.get(0)
							.getID(), 141);
					roleMemberSer.insertRoleMember("group3", 141,
							hrintent.get(0).getID(), 2);
				}

			}
			rolecount++;

		}
		System.out.println("幕僚---->" + rolecount);
	}

	/* 增加角色幕僚所有管理者=tpy管理者 */
	public void updatecharge() {
		List<PostHistory> Listcharge = posthistoryservice.getCharge("group");
		for (int i = 0; i < Listcharge.size(); i++) {
			List<HrmResource> hrRole = HrResourser.getHrSecretaryId("group3",
					"11" + Listcharge.get(i).getUserId());
			if (hrRole == null || hrRole.isEmpty()) {
				System.out.println("在OA表里面没有找到此人"
						+ Listcharge.get(i).getUserId());
				roleMemberSer.insertRoleMember("group3", 82, 0, 2);
			} else {
				List<HrmRoleMember> ListMember = roleMemberSer.getHrRoleMember(
						"group3", hrRole.get(0).getID());
				if (ListMember == null || ListMember.isEmpty()) {
					roleMemberSer.insertRoleMember("group3", 82, hrRole.get(0)
							.getID(), 2);
				} else if (ListMember.size() >= 1) {
					roleMemberSer.deleteRoleMber("group3", hrRole.get(0)
							.getID(), 82);
					roleMemberSer.insertRoleMember("group3", 82, hrRole.get(0)
							.getID(), 2);
				}

			}
			rolecount++;
		}

		System.out.println("TPY管理---->" + rolecount);
	}

	/* 增加角色总监 */
	public void updateDirector() {
		List<PostHistory> directorList = posthistoryservice
				.getDirector("group");
		for (int i = 0; i < directorList.size(); i++) {
			List<HrmResource> hrDirector = HrResourser.getHrSecretaryId(
					"group3", "11" + directorList.get(i).getUserId());
			if (hrDirector == null || hrDirector.isEmpty()) {
				System.out.println("在OA表里面没有找到此人"
						+ directorList.get(i).getUserId());
				roleMemberSer.insertRoleMember("group3", 121, 0, 2);
			} else {
				List<HrmRoleMember> ListMember = roleMemberSer.getHrRoleMember(
						"group3", hrDirector.get(0).getID());
				if (ListMember == null || ListMember.isEmpty()) {
					roleMemberSer.insertRoleMember("group3", 121, hrDirector
							.get(0).getID(), 2);
				} else if (ListMember.size() >= 1) {
					roleMemberSer.deleteRoleMber("group3", hrDirector.get(0)
							.getID(), 121);
					roleMemberSer.insertRoleMember("group3", 121, hrDirector
							.get(0).getID(), 2);
				}

			}
			director++;
		}

		System.out.println("总监---->" + director);
	}

	public int getStatus() {
		return 1;
	}

	public static void main(String[] args) {
		TestServiceImpl serviceImpl = new TestServiceImpl();
		serviceImpl.getAllshop();
		serviceImpl.getShopSer();
		serviceImpl.updateShop();
		serviceImpl.updateSuperintendent();
		serviceImpl.updatecharge();
		serviceImpl.updateDirector();
	}
}
