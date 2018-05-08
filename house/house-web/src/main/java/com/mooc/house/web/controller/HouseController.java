package com.mooc.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mooc.house.biz.service.AgencyService;
import com.mooc.house.biz.service.CityService;
import com.mooc.house.biz.service.HouseService;
import com.mooc.house.biz.service.RecommendService;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.HouseUser;
import com.mooc.house.common.model.User;
import com.mooc.house.common.model.UserMsg;
import com.mooc.house.common.page.PageData;
import com.mooc.house.common.page.PageParams;
import com.mooc.house.common.result.ResultMsg;
import com.mooc.house.web.interceptor.UserContext;


@Controller
public class HouseController {

	@Autowired
	private HouseService houseService;

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private CityService cityService;
	/**
	 * 1.实现分页
	 * 2.支持小区搜索、类型搜索
	 * 3.支持排序
	 * 4.支持展示图片、价格、标题、地址等信息
	 * @return
	 */
	@RequestMapping("/house/list")
	public String houseList(Integer pageSize,Integer pageNum,House query,ModelMap modelMap){
		PageData<House> ps =  houseService.queryHouse(query,PageParams.build(pageSize, pageNum));
		List<House> hotHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("ps", ps);
		modelMap.put("vo", query);
		return "house/listing";
	}

	/**
	 * 查询房屋详情
	 * 查询关联经纪人
	 * @param id
	 * @return
	 */
	@RequestMapping("house/detail")
	public String houseDetail(Long id,ModelMap modelMap){
		House house = houseService.queryOneHouse(id);
		HouseUser houseUser = houseService.getHouseUser(id);
			    recommendService.increase(id);
		//	    List<Comment> comments = commentService.getHouseComments(id,8);
		if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
			modelMap.put("agent", agencyService.getAgentDeail(houseUser.getUserId()));
		}
	   List<House> hotHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("house", house);
		//	 modelMap.put("commentList", comments);
		return "/house/detail";
	}

	@RequestMapping("house/leaveMsg")
	public String houseMsg(UserMsg userMsg){
		houseService.addUserMsg(userMsg);
		return "redirect:/house/detail?id=" + userMsg.getHouseId() +"&"+ResultMsg.successMsg("留言成功").asUrlParams();
	}
	
	@RequestMapping("/house/toAdd")
	public String toAdd(ModelMap modelMap) {
		modelMap.put("citys", cityService.getAllCitys());
		modelMap.put("communitys", houseService.getAllCommunitys());
		return "/house/add";
	}
	
	@RequestMapping("/house/add")
	public String doAdd(House house){
		User user = UserContext.getUser();
		house.setState(CommonConstants.HOUSE_STATE_UP);
		houseService.addHouse(house,user);
		return "redirect:/house/ownlist";
	}

}
