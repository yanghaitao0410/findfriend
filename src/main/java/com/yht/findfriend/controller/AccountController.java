package com.yht.findfriend.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping("/")
	public String login(){
		return "login";
	}
	
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
	
	@RequestMapping(value="/toIndex")
	public String toIndex(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		String user_name = request.getParameter("user_name");
		String user_pwd = request.getParameter("user_pwd");
		if(user_name == null || user_pwd == null){
			return "login";
		}
		String md5_pwd = AccountUtil.md5(user_pwd);
		ResultMap result = userService.findUserByNameAndPwd(user_name, md5_pwd);
		if(0 == result.getStatus()){
			session.setAttribute("user_id", result.getUser().getUser_id()+ "");
			session.setAttribute("user_name", user_name);
			session.setAttribute("user_pwd", md5_pwd);
			Cookie cookie = new Cookie("user_id", result.getUser().getUser_id()+ "");
			cookie.setMaxAge(6000);
			response.addCookie(cookie);
//			request.setAttribute("user_name", user_name);
			return "index";	
		}
		request.setAttribute("msg", "用户名或密码错误");
		return "login";
	}
	
	@RequestMapping("redirectIndex")
	public String redirectIndex(HttpServletRequest request, String tag, User user){
		if(tag != null){
			request.setAttribute("tag", tag);
		}
		if(user != null){
			request.setAttribute("reUser_id", user.getUser_id());
			request.setAttribute("reUser_name", user.getUser_name());
		}
		
		return "index";
	}
	
	@RequestMapping("editAccountInfo")
	@ResponseBody
	public ResultMap editAccountInfo(User user){
		ResultMap result = userService.editAccountInfo(user);
		return result;
	}
	
	@RequestMapping("queryUserName")
	@ResponseBody
	public ResultMap queryUserName(int user_id){
		ResultMap resultMap = userService.queryUserName(user_id);
		return resultMap;
	}
	
	@RequestMapping("exitUser")
	public String exitUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute("user_id");
		session.removeAttribute("user_name");
		session.removeAttribute("user_pwd");
		//session.invalidate();
		return "login";
	}
	
	@RequestMapping("queryUserInfo")
	@ResponseBody
	public ResultMap queryUserInfo(String user_id){
		ResultMap resultMap = userService.queryUserInfo(user_id);
		return resultMap;
	}
	
	@RequestMapping("updateUserInfo")
	@ResponseBody
	public ResultMap updateUserInfo(User user, String string_hobby){
		ResultMap resultMap = userService.updateUserInfo(user, string_hobby);
		return resultMap;
	}
	
}
