package com.yht.findfriend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface GreatDao {

	/**
	 * 根据用户id和动态id在点赞表中查询该用户是否已经 赞过
	 * @param user_id
	 * @param share_id
	 * @return
	 */
	int getGreat(@Param("user_id")String user_id, @Param("share_id")String share_id);

	/**
	 * 删除点赞记录
	 * @param share_id
	 * @param user_id
	 * @return
	 */
	int deleteGreat(@Param("share_id")String share_id, @Param("user_id")String user_id);

	/**
	 * 新增点赞记录
	 * @param share_id
	 * @param user_id
	 * @return
	 */
	int insertGreat(@Param("share_id")String share_id, @Param("user_id")String user_id);

	/**
	 * 根据用户id查询出当前用户赞过的所有动态
	 * @param user_id
	 * @return
	 */
	List<Integer> getShare_id(@Param("user_id")String user_id);
	
}
