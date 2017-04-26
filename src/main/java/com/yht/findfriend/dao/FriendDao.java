package com.yht.findfriend.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yht.findfriend.entity.Friend;
import com.yht.findfriend.entity.Hobby;
import com.yht.findfriend.entity.User;

public interface FriendDao {

	/**
	 * 查询出当前用户的所有好友
	 * @param user_id
	 * @return
	 */
	List<Friend> queryFriend(String user_id);

	/**
	 * 查询好友信息
	 * @param user_id
	 * @param friend_id
	 * @return
	 */
	Friend QueryFriendInfo(Map<String, String> param);

	/**
	 * 删除好友
	 * @param user_id
	 * @param friend_id
	 * @return
	 */
	int deleteFriend(@Param("user_id")String user_id, @Param("friend_id")String friend_id);

	int addFriend(Friend friend);

	/**
	 * 更新分组名称（好友表）
	 * @param user_id
	 * @param old_group_name
	 * @param new_group_name
	 * @return
	 */
	int groupRenameAtFriend(
			@Param("user_id")String user_id, 
			@Param("old_group_name")String old_group_name, 
			@Param("new_group_name")String new_group_name);

	/**
	 * 确认在是否还有好友在当前分组
	 * @param user
	 * @return
	 */
	int checkFriendAtGroup(@Param("user_id")String user_id, @Param("group_name")String group_name);

	/**
	 * 更换好友分组
	 * @param friend
	 * @return
	 */
	int changeGroup(Friend friend);

	/**
	 * 确认是否已经添加该好友
	 * @param user
	 * @return
	 */
	int checkFriendAdded(User user);

	/**
	 * 根据动态标签推荐好友
	 * @param user_id 
	 * @param ids
	 * @return
	 */
	List<User> recommendFriendByTag(@Param("list")List<Integer> ids, @Param("tag")String tag);

	

}
