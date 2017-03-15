package com.yht.findfriend.dao;

import com.yht.findfriend.entity.User;


public interface UserDao {

	User findUserByUserName(String user_name);

}
