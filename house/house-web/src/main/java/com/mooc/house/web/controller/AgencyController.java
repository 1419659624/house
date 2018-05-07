package com.mooc.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Objects;
import com.mooc.house.biz.service.AgencyService;
import com.mooc.house.biz.service.HouseService;
import com.mooc.house.biz.service.MailService;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.model.Agency;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.User;
import com.mooc.house.common.page.PageData;
import com.mooc.house.common.page.PageParams;
import com.mooc.house.common.result.ResultMsg;
import com.mooc.house.web.interceptor.UserContext;

@Controller
public class AgencyController {
  
  @Autowired
  private AgencyService agencyService;
  
//  @Autowired
//  private RecommendService recommendService;
//  
  @Autowired
  private HouseService houseService;
  
  @Autowired
  private MailService mailService;

  @RequestMapping("agency/create")
  public String agencyCreate(){
    User user =  UserContext.getUser();
    if (user == null || !Objects.equal(user.getEmail(), "spring_boot@163.com")) {
      return "redirect:/accounts/signin?" + ResultMsg.successMsg("请先登录").asUrlParams();
    }
    return "/user/agency/create";
  }
  
  
  @RequestMapping("/agency/agentList")
  public String agentList(Integer pageSize,Integer pageNum,ModelMap modelMap){
//    if (pageSize == null) {
//      pageSize = 6;
//    }
    PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
    //<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
   // modelMap.put("recomHouses", houses);
    modelMap.put("ps", ps);
    return "/user/agent/agentList";
  }
  
  @RequestMapping("/agency/agentDetail")
  public String agentDetail(Long id,ModelMap modelMap){
      User user =  agencyService.getAgentDeail(id);
     // List<House> houses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
      House query = new House();
      query.setUserId(id);
      query.setBookmarked(false);
      PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3,1));
      if (bindHouse != null) {
        modelMap.put("bindHouses", bindHouse.getList()) ;
      }
   //   modelMap.put("recomHouses", houses);
      modelMap.put("agent", user);
      modelMap.put("agencyName", user.getAgencyName());
      return "/user/agent/agentDetail";
  }
  
  
  

}
