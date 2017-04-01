package com.yht.findfriend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yht.findfriend.entity.Friend;
import com.yht.findfriend.entity.Share;

public interface ShareDao {

	int sendShare(Share share);

	/**
	 * 从索引位置开始查询5条动态
	 * @param user_id
	 * @param index
	 * @return
	 */
	List<Share> loadShare(@Param("user_id")String user_id, @Param("index")int index);

	/**
	 * 加载热门动态
	 * @param user_id
	 * @param index 
	 * @return
	 */
	List<Share> loadHotShare(@Param("user_id")String user_id, @Param("index")int index);

	/**
	 * 根据用户id查询好友动态
	 * @param user_id
	 * @param index
	 * @return
	 */
	List<Share> loadFriendShare(@Param("user_id")String user_id, @Param("index")int index);

	/**
	 * 根据动态id查询动态
	 * @param share_id
	 * @return
	 */
	Share loadShareByShare_id(@Param("share_id")String share_id);

	/**
	 * 更新动态信息
	 * @param share
	 * @return
	 */
	int updateShare(Share share);

	/**
	 * 根据好友id加载该用户所有动态
	 * @param friend_id
	 * @param index 
	 * @return
	 */
	List<Share> loadSpecificShare(@Param("user_id")String friend_id, @Param("index")int index);

	


//	void resetShare(String user_id);

}
