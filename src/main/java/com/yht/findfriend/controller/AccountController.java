package com.yht.findfriend.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.User;
import com.yht.findfriend.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Resource
	private UserService userService;
	private ResultMap result;
	
	public String register(User user){
		
		
		return null;
	}
	
	@RequestMapping("/checkUserName")
	@ResponseBody
	public ResultMap findUserByUserName(String user_name) throws IOException{
		System.out.println("==============================");
		User user = userService.findUserByUserName(user_name);
		System.out.println(user);
		result = new ResultMap();
		if(user != null){
			result.setStatus(1);
			result.setMsg("此用户名已被占用");
		}else{
			result.setStatus(0);
			result.setMsg("此用户名可用");
		}
		return result;
	}
	  
}
