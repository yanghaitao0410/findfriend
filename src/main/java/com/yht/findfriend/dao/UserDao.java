package com.yht.findfriend.dao;

import java.util.Map;

import com.yht.findfriend.entity.User;


public interface UserDao {

	User findUserByUserName(String user_name);

	int registerAccount(User user);

	User findUserByNameAndPwd(Map<String, String> map);

	int editAccountInfo(User user);

	String getUser_id(int user_id);


}
