package com.yht.findfriend.service;

import javax.servlet.http.HttpServletRequest;

import com.yht.findfriend.entity.Friend;
import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.User;

public interface FriendService {

	/**
	 * 查询好友的服务方法
	 * @param user_id
	 * @return
	 */
	ResultMap QueryFriend(String user_id);

	/**
	 * 查询分组信息的服务方法
	 * @param user_id
	 * @return
	 */
	ResultMap QueryGroup(String user_id);

	/**
	 * 查询好友信息的服务方法
	 * @param user_id
	 * @param friend_id
	 * @return
	 */
	ResultMap QueryFriendInfo(String user_id, String friend_id);

	/**
	 * 检查组名是否存在的服务方法
	 * @param user_id
	 * @param group_name
	 * @return
	 */
	ResultMap checkGroup(String user_id, String group_name);

	/**
	 * 更改好友分组的服务方法
	 * @param user_id
	 * @param friend_id
	 * @param group_name
	 * @return
	 */

	ResultMap changeGroup(Friend friend);

	/**
	 * 删除好友的服务方法
	 * @param user_id
	 * @param friend_id
	 * @return
	 */
	ResultMap deleteFriendService(String user_id, String friend_id);

	/**
	 * 查找用户的服务方法
	 * @param user_name
	 * @param nick_name
	 * @return
	 */
	ResultMap searchUser(String user_name, String nick_name);

	/**
	 * 添加好友的服务方法
	 * @param friend
	 * @param request 
	 * @return
	 */
	ResultMap addFriend(Friend friend, HttpServletRequest request);

	/**
	 * 新建分组的服务方法
	 * @param user_id
	 * @param group_name
	 * @return
	 */
	ResultMap addGroup(String user_id, String group_name);

	/**
	 * 更改组名的服务方法
	 * @param user_id
	 * @param old_group_name
	 * @param new_group_name
	 * @return
	 */
	ResultMap groupRename(String user_id, String old_group_name, String new_group_name);

	/**
	 * 删除分组的服务方法
	 * @param user
	 * @return
	 */
	ResultMap deleteGroup(String user_id, String group_name);

	/**
	 * 确认是否已经添加该好友的服务方法
	 * @param user
	 * @return
	 */
	ResultMap checkFriendAdded(User user);

	/**
	 * 根据标签推荐好友的服务方法
	 * @param user_id
	 * @param tag
	 * @return
	 */
	ResultMap recommendFriendByTag(String user_id, String tag);

	/**
	 * 加载推荐用户信息的服务方法
	 * @param friend_id
	 * @return
	 */
	ResultMap loadReComInfo(String re_friend_id);

}
