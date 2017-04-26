package com.yht.findfriend.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yht.findfriend.entity.User;


public interface UserDao {

	User findUserByUserName(@Param("user_name")String user_name);

	int registerAccount(User user);

	User findUserByNameAndPwd(Map<String, String> map);

	int editAccountInfo(User user);

	String getUser_id(int user_id);

	User queryUserInfo(@Param("user_id")String user_id);

	int updateUserInfo(User user);

	/**
	 * 查找用户
	 * @param user_name
	 * @param nick_name
	 * @return
	 */
	User searchUser(@Param("user_name")String user_name, @Param("nick_name")String nick_name);

	/**
	 * 加载推荐用户的信息
	 * @param friend_id
	 * @return
	 */
	User loadReComInfo(@Param("user_id")String friend_id);


}
