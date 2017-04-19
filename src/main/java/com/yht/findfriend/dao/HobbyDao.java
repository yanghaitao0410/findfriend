package com.yht.findfriend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yht.findfriend.entity.Hobby;

public interface HobbyDao {

	List<Hobby> queryHobby(@Param("user_id")String user_id);

	int deleteHobby(@Param("user_id")int user_id);

	int insertHobby(@Param("user_id")int user_id, @Param("hobby_name")String hobby);
	
}
