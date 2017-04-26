package com.yht.findfriend.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yht.findfriend.dao.FriendDao;
import com.yht.findfriend.dao.GroupDao;
import com.yht.findfriend.dao.HobbyDao;
import com.yht.findfriend.dao.UserDao;
import com.yht.findfriend.entity.Friend;
import com.yht.findfriend.entity.Hobby;
import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.User;
import com.yht.findfriend.service.FriendService;
import com.yht.findfriend.util.AccountUtil;
import com.yht.findfriend.util.ShareUtil;

@Service
public class FriendServiceImpl implements FriendService {

	@Resource
	private FriendDao friendDao;
	
	@Resource
	private GroupDao groupDao;
	
	@Resource
	private HobbyDao hobbyDao;
	
	@Resource
	private UserDao userDao;
	
	@Override
	public ResultMap QueryFriend(String user_id) {
		List<Friend> friends = friendDao.queryFriend(user_id);
		ResultMap result = new ResultMap();
		result.setStatus(0);
		result.setMsg("查询成功");
		result.setData(friends);
//		result.setList(list);
		/*for (Friend friend : friends) {
			System.out.println(friend);
		}*/
		return result;
	}

	@Override
	public ResultMap QueryGroup(String user_id) {
		List<String> groups = groupDao.queryGroup(user_id);
		ResultMap result = new ResultMap();
		result.setStatus(0);
		result.setMsg("查询组名成功");
		result.setData(groups);
		return result;
	}

	@Override
	public ResultMap QueryFriendInfo(String user_id, String friend_id) {
		ResultMap result = new ResultMap();
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("user_id", user_id);
		param.put("friend_id", friend_id);
		
		Friend friend = friendDao.QueryFriendInfo(param);
		List<Hobby> hobbys = hobbyDao.queryHobby(friend_id);
		
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("friend", friend);
		data.put("hobby", hobbys);
		result.setStatus(0);
		result.setMsg("查询信息成功");
		result.setData(data);
		return result;
	}

	@Override
	public ResultMap checkGroup(String user_id, String group_name) {
		int count = groupDao.checkGroup(user_id, group_name);
		ResultMap result = new ResultMap();
		if(count == 1){
			result.setMsg("该分组已存在，请输入其他组名");
			result.setStatus(1);
		}else{
			result.setStatus(0);
			result.setMsg("该分组可以使用");
		}
		return result;
	}

	@Override
	public ResultMap changeGroup(Friend friend) {
		if("no_group".equals(friend.getGroup_name())){
			friend.setParent_id(0);
		}else{
			int group_id = groupDao.queryGroupIdByName(friend.getGroup_name(), friend.getUser_id());
			friend.setParent_id(group_id);
		}
		
		int count = friendDao.changeGroup(friend);
		ResultMap result = new ResultMap();
		if(count == 1){
			result.setStatus(0);
			result.setMsg("更新分组成功");
		}else{
			result.setStatus(1);
			result.setMsg("更新分组失败");
		}
		return result;
	}

	@Override
	public ResultMap deleteFriendService(String user_id, String friend_id) {
		int count = friendDao.deleteFriend(user_id, friend_id);
		ResultMap result = new ResultMap();
		if(count == 1){
			result.setStatus(0);
			result.setMsg("删除好友成功");
		}else{
			result.setStatus(1);
			result.setMsg("删除好友失败");
		}
		return result;
	}

	@Override
	public ResultMap searchUser(String user_name, String nick_name) {
		if("".equals(user_name.trim())){
			user_name = null;
		}
		if("".equals(nick_name.trim())){
			nick_name = null;
		}
		User user = userDao.searchUser(user_name, nick_name);
		List<Hobby> hobbys = hobbyDao.queryHobby(user.getUser_id()+"");
		return getUserInfoResult(user, hobbys);
	}

	/**
	 * 将用户基本信息和用户爱好封装到返回类中返回
	 * @param user
	 * @param hobbys
	 * @return
	 */
	private ResultMap getUserInfoResult(User user, List<Hobby> hobbys){
		
		ResultMap result= new ResultMap();
		if(user != null){
			result.setStatus(0);
			result.setMsg("查询成功！！！");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("user", user);
			if(hobbys != null){
				data.put("hobbys", hobbys);
			}
			result.setData(data);
		}else{
			result.setStatus(1);
			result.setMsg("该用户不存在，请重新确认！！！");
		}
		return result;
	}
	
	@Override
	public ResultMap addFriend(Friend friend, HttpServletRequest request) {
//		if(friend.getGroup_name() != null){
//			int group_id = groupDao.queryGroupIdByName(friend.getGroup_name(), friend.getUser_id());
//			if(group_id >= -1){
//				friend.setParent_id(group_id);
//			}
//		}
		User user = userDao.queryUserInfo(friend.getFriend_id()+"");
		friend.setParent_id(0);
		friend.setFriend_name(user.getUser_name());
		friend.setFriend_sex(Integer.valueOf(user.getUser_sex()));
		friend.setFriend_phonenum(user.getUser_phone_num());
		friend.setNick_name(user.getNick_name());
		ResultMap result = new ResultMap();
		int count = friendDao.addFriend(friend);
		
		if(count == 1){
			result.setStatus(0);
			result.setMsg("添加好友成功");
			//根据当前用户和添加好友的爱好交集推荐好友
			
			List<String> ids = getIds(friend);
			List<String> hobby_name = getIntersectHobby(friend);
			if(hobby_name.size() > 0){
				List<String> user_ids = hobbyDao.queryUserIdByHobby(ids, hobby_name);
				if(user_ids != null && user_ids.size() > 0){
					User reUser = userDao.queryUserInfo(user_ids.get(0));
					result.setData(reUser);
				}
			}
		}else{
			result.setStatus(1);
			result.setMsg("添加好友失败");
		}
		return result;
	}

	/**
	 * 将当前用户id和好友的id集合返回
	 * @param friend
	 * @return
	 */
	private List<String> getIds(Friend friend){
		List<Friend> friends = friendDao.queryFriend(friend.getUser_id()+"");
		List<String> ids = new ArrayList<String>();
		ids.add(friend.getUser_id() + "");
		for (Friend friend2 : friends) {
			ids.add(friend2.getFriend_id()+"");
		}
		return ids;
	}
	
	/**
	 * 获取当前用户和当前好友的爱好交集
	 * @param friend
	 * @return
	 */
	private List<String> getIntersectHobby(Friend friend){
		List<Hobby> userHobby = hobbyDao.queryHobby(friend.getUser_id()+""); 
		List<Hobby> friendHobby = hobbyDao.queryHobby(friend.getFriend_id()+"");
		List<String> user_hobby_name = new ArrayList<String>();
		for(Hobby hobby : userHobby){
			user_hobby_name.add(hobby.getHobby_name());
		}
		List<String> friend_hobby_name = new ArrayList<String>();
		for(Hobby hobby : friendHobby){
			friend_hobby_name.add(hobby.getHobby_name());
		}
		user_hobby_name.retainAll(friend_hobby_name);
		return user_hobby_name;
	}
	
	@Override
	public ResultMap addGroup(String user_id, String group_name) {
		ResultMap result = new ResultMap();
		int count = groupDao.checkGroup(user_id, group_name);
		if(count != 1){
			count = 0;
			count = groupDao.addGroup(user_id, group_name);
			if(count == 1){
				result.setStatus(0);
				result.setMsg("添加分组成功");
				return result;
			}
		}
		result.setStatus(1);
		result.setMsg("添加分组失败");
		return result;
	}

	@Override
	public ResultMap groupRename(String user_id, String old_group_name, String new_group_name) {
		ResultMap result = new ResultMap();
		int friendCount = friendDao.groupRenameAtFriend(user_id, old_group_name, new_group_name);
		int groupCount = groupDao.groupRenameAtGroup(user_id, old_group_name, new_group_name);
		if(friendCount >= 0 && groupCount == 1){
			result.setStatus(0);
			result.setMsg("更改组名成功！！！");
		}else{
			result.setStatus(1);
			result.setMsg("更新组名失败！！！");
		}
		return result;
	}

	@Override
	public ResultMap deleteGroup(String user_id, String group_name) {
		ResultMap result = new ResultMap();
		int count = friendDao.checkFriendAtGroup(user_id, group_name);
		if(count == 0){
			count = groupDao.deleteGroup(user_id, group_name);
			if(count == 1){
				result.setStatus(0);
				result.setMsg("删除分组成功！！！");
			}else{
				result.setStatus(1);
				result.setMsg("删除分组失败!!!");
			}
		}else{
			result.setStatus(1);
			result.setMsg("还有好友在当前分组，将好友转移至其他分组后才可删除！！！");
		}
		return result;
	}

	@Override
	public ResultMap checkFriendAdded(User user) {
		int count = friendDao.checkFriendAdded(user);
		ResultMap resultMap = new ResultMap();
		if(count == 1){
			resultMap.setStatus(1);
			resultMap.setMsg("该用户已经是好友了！！");
		}else{
			resultMap.setStatus(0);
			resultMap.setMsg("该用户还不是好友！！");
		}
		return resultMap;
	}

	@Override
	public ResultMap recommendFriendByTag(String user_id, String tag) {
		List<Friend> friends = friendDao.queryFriend(user_id);
		List<Integer> ids = AccountUtil.getIds(user_id, friends);
		String[] tags = ShareUtil.splitTag(tag);
		List<User> total = new ArrayList<User>();
		for(int i=0; i < tags.length; i++){
			List<User>  recommendFriends = friendDao.recommendFriendByTag(ids, tags[i]);
			total.addAll(recommendFriends);
		}
		System.out.println(total.toString());
		return getReFriendResult(total);
	}
	
	/**
	 * 处理结果集返回
	 * @param total
	 * @return
	 */
	private ResultMap getReFriendResult(List<User> total){
		ResultMap resultMap = new ResultMap();
		if(total != null && total.size() > 0){
			//每次只推荐一名好友
			User user = userDao.queryUserInfo(total.get(0).getUser_id() + "");
			if(user != null){
				resultMap.setStatus(0);
				resultMap.setMsg("你可能和该用户有相同的爱好：");
				resultMap.setData(user);
				return resultMap;
			}
		}
		resultMap.setStatus(1);
		resultMap.setMsg("系统内没有该爱好的用户！！");
		return resultMap;
	}
	

	@Override
	public ResultMap loadReComInfo(String re_friend_id) {
		User reUser = userDao.queryUserInfo(re_friend_id);
		List<Hobby> hobbys = hobbyDao.queryHobby(reUser.getUser_id()+"");
		return getUserInfoResult(reUser, hobbys);
	}

}
