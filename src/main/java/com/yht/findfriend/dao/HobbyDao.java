package com.yht.findfriend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yht.findfriend.entity.Hobby;

public interface HobbyDao {

	List<Hobby> queryHobby(@Param("user_id")String user_id);

	int deleteHobby(@Param("user_id")int user_id);

	int insertHobby(@Param("user_id")int user_id, @Param("hobby_name")String hobby);

	/**
	 * 根据爱好查询好友
	 * @param user_hobby_name
	 * @param hobby_name 
	 * @return
	 */
	List<String> queryUserIdByHobby(@Param("ids")List<String> ids, @Param("hobby_names")List<String> user_hobby_name);
	
}
