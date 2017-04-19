package com.yht.findfriend.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yht.findfriend.dao.HobbyDao;
import com.yht.findfriend.dao.UserDao;
import com.yht.findfriend.entity.Hobby;
import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.User;
import com.yht.findfriend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private HobbyDao hobbyDao;
	
	public ResultMap findUserByUserName(String userName) {
		User user = userDao.findUserByUserName(userName);
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
		int account = userDao.registerAccount(user);
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
		User realUser = userDao.findUserByNameAndPwd(data);
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
		int count = userDao.editAccountInfo(user);
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
		String user_name = userDao.getUser_id(user_id);
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

	@Override
	public ResultMap queryUserInfo(String user_id) {
		ResultMap resultMap = new ResultMap();
		User user = userDao.queryUserInfo(user_id);
		List<Hobby> hobbys = hobbyDao.queryHobby(user_id);
		Map <String, Object> data = new HashMap<String, Object>();
		data.put("user", user);
		data.put("hobby", hobbys);
		if(user != null){
			resultMap.setStatus(0);
			resultMap.setMsg("查询用户信息成功");
			resultMap.setData(data);
		}else{
			resultMap.setStatus(1);
			resultMap.setMsg("查无此人");
		}		
		return resultMap;
	}

	@Override
	public ResultMap updateUserInfo(User user, String string_hobby) {
		int count = userDao.updateUserInfo(user);
		if(string_hobby != null){
			count += hobbyDao.deleteHobby(user.getUser_id());
			String[] hobbys = string_hobby.split(",");
			for (String hobby : hobbys) {
				count +=hobbyDao.insertHobby(user.getUser_id(), hobby);
			}
		}
		ResultMap resultMap = new ResultMap();
		if(count > 0){
			resultMap.setStatus(0);
			resultMap.setMsg("更新用户信息成功！！！");
		}else{
			resultMap.setStatus(1);
			resultMap.setMsg("更新用户信息失败！！！");
		}
		
		
		return resultMap;
	}


}
