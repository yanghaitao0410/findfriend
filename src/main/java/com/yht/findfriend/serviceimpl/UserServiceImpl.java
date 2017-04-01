package com.yht.findfriend.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yht.findfriend.dao.UserDao;
import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.User;
import com.yht.findfriend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao dao;
	
	public ResultMap findUserByUserName(String userName) {
		User user = dao.findUserByUserName(userName);
		ResultMap result = new ResultMap();
		if(user != null){
			result.setStatus(1);
			result.setMsg("此用户名已被占用");
		}else{
			result.setStatus(0);
			result.setMsg("此用户名可用");
		}
		return result;
	}

	@Override
	public ResultMap registerAccount(User user) {
		int account = dao.registerAccount(user);
//		System.out.println("account:" +account);
		if(1 == account){
			ResultMap result = new ResultMap();
			result.setStatus(0);
			result.setMsg("注册成功！！！");
			return result;
		}
		return null;
	}

	@Override
	public ResultMap findUserByNameAndPwd(String user_name, String user_pwd) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("user_name", user_name);
		data.put("user_pwd", user_pwd);
		User realUser = dao.findUserByNameAndPwd(data);
		ResultMap result = new ResultMap();
		if(realUser != null){
			result.setStatus(0);
			result.setMsg("用户名密码正确");
			result.setUser(realUser);
		}else{
			result.setStatus(1);
			result.setMsg("用户名或密码错误");
		}
		return result;
	}

	@Override
	public ResultMap editAccountInfo(User user) {
		int count = dao.editAccountInfo(user);
		ResultMap result = new ResultMap();
		if(count == 1){
			result.setStatus(0);
			result.setMsg("信息编辑成功");
		}else{
			result.setStatus(1);
			result.setMsg("信息编辑失败");
		}
		return result;
	}

	@Override
	public ResultMap queryUserName(int user_id) {
		String user_name = dao.getUser_id(user_id);
//		System.out.println("user_name:" + user_name);
		ResultMap resultMap = new ResultMap();
//		if(user_name != null){
			resultMap.setStatus(0);
			resultMap.setMsg("查询用户名成功");
			resultMap.setData(user_name);
		/*}else{
			resultMap.setStatus(1);
			resultMap.setMsg("查询用户名失败");
		}*/
		return resultMap;
	}


}
