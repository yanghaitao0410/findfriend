package com.yht.findfriend.service;

import org.springframework.stereotype.Service;

import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.User;

@Service
public interface UserService {

	public ResultMap findUserByUserName(String userName);

	public ResultMap registerAccount(User user);

	public ResultMap findUserByNameAndPwd(String user_name, String user_pwd);

	public ResultMap editAccountInfo(User user);

	public ResultMap queryUserName(int user_id);
	
}
