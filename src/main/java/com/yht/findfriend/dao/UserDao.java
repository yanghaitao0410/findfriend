package com.yht.findfriend.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yht.findfriend.entity.Hobby;
import com.yht.findfriend.entity.User;


public interface UserDao {

	User findUserByUserName(String user_name);

	int registerAccount(User user);

	User findUserByNameAndPwd(Map<String, String> map);

	int editAccountInfo(User user);

	String getUser_id(int user_id);

	User queryUserInfo(@Param("user_id")String user_id);

	int updateUserInfo(User user);

	


}
