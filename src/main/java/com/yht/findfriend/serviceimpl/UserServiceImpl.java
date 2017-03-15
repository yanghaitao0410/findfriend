package com.yht.findfriend.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yht.findfriend.dao.UserDao;
import com.yht.findfriend.entity.User;
import com.yht.findfriend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao dao;
	
	public User findUserByUserName(String userName) {
		User user = dao.findUserByUserName(userName);
		return user;
	}

}
