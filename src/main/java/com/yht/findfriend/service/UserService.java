package com.yht.findfriend.service;

import org.springframework.stereotype.Service;

import com.yht.findfriend.entity.User;

@Service
public interface UserService {

	public User findUserByUserName(String userName);
	
}
