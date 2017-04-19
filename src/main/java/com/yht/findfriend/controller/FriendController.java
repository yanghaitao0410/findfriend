package com.yht.findfriend.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yht.findfriend.entity.Friend;
import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.User;
import com.yht.findfriend.service.FriendService;

@Controller
@RequestMapping("/friend")
public class FriendController {

	@Resource
	private FriendService service;
	
	//TODO 将参数变为对象
	@RequestMapping("loadFriend")
	@ResponseBody
	public ResultMap loadFriend(String user_id){
		ResultMap result = service.QueryFriend(user_id); 
		return result;
	}
	
	//TODO 将参数变为对象
	@RequestMapping("loadGroup")
	@ResponseBody
	public ResultMap QueryGroup(String user_id){
//		System.out.println(user_id);
		ResultMap result = service.QueryGroup(user_id);
		return result;
	}
	
	//TODO 将参数变为对象
	@RequestMapping("loadFriendInfo")
	@ResponseBody
	public ResultMap QueryFriendInfo(String user_id, String friend_id){
		System.out.println("user_id"+user_id +" friend_id" + friend_id);
		ResultMap result = service.QueryFriendInfo(user_id, friend_id);
		return result;
	}
	
	/**
	 * 检查当前分组在数据库中是否存在
	 * TODO:在前台用户输入新组名后的blur()发送该请求
	 * @param user_id
	 * @return
	 */
	@RequestMapping("checkGroup")
	@ResponseBody
	public ResultMap checkGroup(String user_id, String group_name){
		ResultMap result = service.checkGroup(user_id, group_name);
		return result;
	}
	
	/**
	 * 更改好友分组
	 * 真正参数为下面三个
	 * @param user_id
	 * @param friend_id
	 * @param group_name
	 * @return
	 */
	@RequestMapping("changeGroup")
	@ResponseBody
	public ResultMap changeGroup(Friend friend){
		ResultMap result = service.changeGroup(friend);
		return result;
	}
	
	//TODO 将参数变为对象
	@RequestMapping("deleteFriend")
	@ResponseBody
	public ResultMap deleteFriend(String user_id, String friend_id){
		ResultMap result = service.deleteFriendService(user_id, friend_id);
		return result;
	}
	
	/**
	 * TODO 将参数变为对象
	 * 根据用户名或昵称来查找用户
	 * @param user_name
	 * @param nick_name
	 * @return
	 */
	@RequestMapping("searchUser")
	@ResponseBody
	public ResultMap searchUser(String user_name, String nick_name){
		System.out.println(user_name + " "+ nick_name);
		ResultMap result = service.searchUser(user_name, nick_name);
		return result;
	}
	
	/**
	 * 添加好友：实际传入的参数有：
	 * 		[user_id, friend_id, group_name]
	 * "parent_id" 在service中查出
	 * @param friend
	 * @return
	 */
	@RequestMapping("addFriend")
	@ResponseBody
	public ResultMap addFriend(Friend friend){
		ResultMap result = service.addFriend(friend);
		return result;
	}
	

	@RequestMapping("addGroup")
	@ResponseBody
	public ResultMap addGroup(String user_id, String group_name){
		ResultMap result = service.addGroup(user_id, group_name);
		return result;
	}
	
	@RequestMapping("groupRename")
	@ResponseBody
	public ResultMap groupRename(String user_id, String old_group_name, String new_group_name){
		ResultMap result = service.groupRename(user_id, old_group_name, new_group_name);
		return result;
	}
	
	/**
	 * 删除分组
	 * 实际传入的参数为  String user_id, String group_name
	 * @param user
	 * @return
	 */
	@RequestMapping("deleteGroup")
	@ResponseBody
	public ResultMap deleteGroup(String user_id, String group_name){
		ResultMap result = service.deleteGroup(user_id, group_name);
		return result;
	}
	
	/**
	 * 确认该用户是否已经是好友了
	 * @param user
	 * @return
	 */
	@RequestMapping("checkFriendAdded")
	@ResponseBody
	public ResultMap checkFriendAdded(User user){
		ResultMap resultMap = service.checkFriendAdded(user);
		return resultMap;
	}
	
	
}




