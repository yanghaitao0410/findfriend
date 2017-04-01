package com.yht.findfriend.controller;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.deploy.LoginConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.Share;
import com.yht.findfriend.service.ShareService;

@Controller
@RequestMapping("/share")
public class ShareController {

	@Resource
	private ShareService shareService;
	
	@RequestMapping("/")
	public String login(){
		return "login";
	}
	
	@RequestMapping("toUpload")
	public String toUpload(String user_name, HttpServletResponse request){
		Cookie cookie = new Cookie("user_name", user_name);
		request.addCookie(cookie);
		return "upload";
	}
	
	/**
	 * 发布动态 实际传入的参数有 share_msg, share_image, share_tag
	 * @param share
	 * @param share_image
	 * @param request
	 * @return
	 */
	@RequestMapping("sendShare")
	@ResponseBody
	public ResultMap sendShare(Share share, @RequestParam MultipartFile[] share_image, 
			HttpServletRequest request){
		ResultMap resultMap = shareService.sendShare(share, share_image, request);
		
		return resultMap;
	}
	
	/**
	 * 根据用户id和索引加载动态
	 * @param user_id
	 * @param index
	 * @return
	 */
	@RequestMapping("loadShare")
	@ResponseBody
	public ResultMap loadShare(String user_id, int index){
		ResultMap resultMap = shareService.loadShare(user_id, index);
		return resultMap;
	}
	
	/**
	 * 加载热门动态，排除自己的动态
	 * @param user_id
	 * @return
	 */
	@RequestMapping("loadHotShare")
	@ResponseBody
	public ResultMap loadHotShare(String user_id, int index){
		ResultMap resultMap = shareService.loadHotShare(user_id, index);
		return resultMap;
	}
	
	/**
	 * 加载好友动态
	 * @param user_id
	 * @return
	 */
	@RequestMapping("loadFriendShare")
	@ResponseBody
	public ResultMap loadFriendShare(String user_id, int index){
		ResultMap resultMap = shareService.loadFriendShare(user_id, index);
		return resultMap;
	}
	
	/**
	 * 点赞功能
	 * @param share_id
	 * @param user_id
	 * @return
	 */
	@RequestMapping("clickGreed")
	@ResponseBody
	public ResultMap clickGreed(String share_id, String user_id, HttpServletRequest request){
		ResultMap resultMap = shareService.clickGreed(share_id, user_id, request);
		return resultMap;
	}
	
	/**
	 * 加载指定好友的动态
	 * 指定：specific
	 * @param friend_id
	 * @return
	 */
	@RequestMapping("loadSpecificShare")
	@ResponseBody
	public ResultMap loadSpecificShare(String friend_id, int index){
		System.out.println(friend_id + " " + index);
		ResultMap resultMap = shareService.loadSpecificShare(friend_id, index);
		return resultMap;
	}
	
	
	
	/**
	 * 重置用户的动态状态，将状态为1的改为0
	 * @param user_id
	 * @return
	 */
	/*@RequestMapping("resetShare")
	@ResponseBody
	public ResultMap resetShare(String user_id){
		ResultMap resultMap = shareService.resetShare(user_id);
		return resultMap;
	}*/
	
}
