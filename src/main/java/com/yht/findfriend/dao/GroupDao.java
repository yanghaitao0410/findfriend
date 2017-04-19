package com.yht.findfriend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yht.findfriend.entity.Friend;

public interface GroupDao {

	/**
	 * 查询出当前用户的分组
	 * @param user_id
	 * @return
	 */
	List<String> queryGroup(String user_id);
		
	/**
	 * 检查该分组是否在数据库中存在
	 * @param user_id
	 * @param group_name
	 * @return
	 */
	int checkGroup(@Param("user_id")String user_id, @Param("group_name")String group_name);
	
	/**
	 * 根据组名查询改组的id
	 * @param group_name
	 * @return
	 */
	int queryGroupIdByName(@Param("group_name")String group_name, @Param("user_id")int user_id);
	
	
	int addGroup(@Param("user_id")String user_id, @Param("group_name")String group_name);
	
	/**
	 * 更新分组名称（分组表）
	 * @param user_id
	 * @param old_group_name
	 * @param new_group_name
	 * @return
	 */
	int groupRenameAtGroup(
			@Param("user_id")String user_id, 
			@Param("old_group_name")String old_group_name, 
			@Param("new_group_name")String new_group_name);
	
	/**
	 * 删除分组
	 * @param user_id
	 * @param group_name
	 * @return 
	 */
	int deleteGroup(@Param("user_id")String user_id, @Param("group_name")String group_name);
	
}
