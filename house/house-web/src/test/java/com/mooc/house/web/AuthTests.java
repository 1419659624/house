package com.mooc.house.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.mooc.house.biz.service.UserService;
import com.mooc.house.common.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
public class AuthTests {

	@Autowired
	private UserService userService;

	@Test
	public void testAuth() {
	  User user =	userService.auth("1419659624@qq.com", "123456789");
	  assert user != null;
	  System.out.println(user.getAboutme());
	}

}