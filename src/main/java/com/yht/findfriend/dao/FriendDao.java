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
	 * 查询出当前用户的分组
	 * @param user_id
	 * @return
	 */
	List<String> queryGroup(String user_id);

	/**
	 * 查询好友信息
	 * @param user_id
	 * @param friend_id
	 * @return
	 */
	Friend QueryFriendInfo(Map<String, String> param);
	
	/**
	 * 根据用户id查询用户爱好
	 * @param user_id
	 * @return
	 */
	List<Hobby> QueryHobby(String user_id);

	/**
	 * 检查该分组是否在数据库中存在
	 * @param user_id
	 * @param group_name
	 * @return
	 */
	int checkGroup(@Param("user_id")String user_id, @Param("group_name")String group_name);

	/**
	 * 根据组名查询改组的id
	 * @param group_name
	 * @return
	 */
	int queryGroupIdByName(@Param("group_name")String group_name, @Param("user_id")int user_id);

	/**
	 * 更换好友分组
	 * @param friend
	 * @return
	 */
	int changeGroup(Friend friend);

	/**
	 * 删除好友
	 * @param user_id
	 * @param friend_id
	 * @return
	 */
	int deleteFriend(@Param("user_id")String user_id, @Param("friend_id")String friend_id);

	/**
	 * 查找用户
	 * @param user_name
	 * @param nick_name
	 * @return
	 */
	User searchUser(@Param("user_name")String user_name, @Param("nick_name")String nick_name);

	int addFriend(Friend friend);

	
	int addGroup(@Param("user_id")String user_id, @Param("group_name")String group_name);

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
	 * 更新分组名称（分组表）
	 * @param user_id
	 * @param old_group_name
	 * @param new_group_name
	 * @return
	 */
	int groupRenameAtGroup(
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
	 * 删除分组
	 * @param user_id
	 * @param group_name
	 * @return 
	 */
	int deleteGroup(@Param("user_id")String user_id, @Param("group_name")String group_name);

	

}
