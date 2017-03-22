package com.yht.findfriend.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.User;
import com.yht.findfriend.service.UserService;
import com.yht.findfriend.util.AccountUtil;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Resource
	private UserService userService;
	@SuppressWarnings("unused")
	
	
	@RequestMapping("/checkUserName")
	@ResponseBody
	public ResultMap findUserByUserName(String user_name) throws IOException{
		ResultMap result = userService.findUserByUserName(user_name);
		return result;
	}
	
	@RequestMapping("/register")
	@ResponseBody
	public ResultMap registerAccount(User user){
		String md5_pwd = AccountUtil.md5(user.getUser_pwd());
		user.setUser_pwd(md5_pwd);
		ResultMap result = userService.registerAccount(user);
		return result;
	}
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping("/toRegister")
	public String toRegister(){
		return "register";
	}
	
	/*@RequestMapping("/toIndex")
	public String toIndex(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		User user = new User();
		user.setUser_name(cookies[0].getValue());
		user.setUser_pwd(cookies[1].getValue());
		ResultMap result = userService.findUserByNameAndPwd(user);
		if(0 == result.getStatus()){
			return "index";
		}
		return "login";
	}*/
	
	@RequestMapping(value="/toIndex",method=RequestMethod.POST)
	public String toIndex(HttpServletRequest request, HttpServletResponse response){
		String user_name = request.getParameter("user_name");
		String user_pwd = request.getParameter("user_pwd");
		String md5_pwd = AccountUtil.md5(user_pwd);
		ResultMap result = userService.findUserByNameAndPwd(user_name, md5_pwd);
		if(0 == result.getStatus()){
			Cookie cookie = new Cookie("user_id", result.getUser().getUser_id()+ "");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
			request.setAttribute("user_name", user_name);
			return "index";	
		}
		request.setAttribute("msg", "用户名或密码错误");
		return "login";
	}
	
	@RequestMapping("editAccountInfo")
	@ResponseBody
	public ResultMap editAccountInfo(User user){
		ResultMap result = userService.editAccountInfo(user);
		return result;
	}
	  
}
