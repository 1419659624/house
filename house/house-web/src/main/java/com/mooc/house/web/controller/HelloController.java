package com.mooc.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;

@Controller
public class HelloController {
	@Autowired
	private UserService service;
	
	@RequestMapping("/hello")
	public String hello(Model model){
		List<User> users = service.getUsers();
		User user = users.get(0);
		if(user!=null){
			throw new IllegalArgumentException();
		}
		model.addAttribute("user",user);
		return "hello";
	}
	
	@RequestMapping("/index")
	public String index(){
		return "homepage/index";
	}
	
}
