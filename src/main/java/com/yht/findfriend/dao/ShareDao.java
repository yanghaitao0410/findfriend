package com.yht.findfriend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;
import com.yht.findfriend.entity.Friend;
import com.yht.findfriend.entity.Share;
import com.yht.findfriend.entity.Talk;

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

	/**
	 * 根据评论表中的动态id或点赞表中的share_id <br>
	 * 查询动态，一次加载5条
	 * @param share_ids  查询条件
	 * @param index 查询开始位置
	 * @return
	 */
	List<Share> loadShareByTalkORGreat(@Param("list")List<Integer> share_ids, @Param("index2")int index);

	/**
	 * 将动态表中对应的那条动态的share_status从0改为1
	 * @param user_id
	 * @param share_id
	 * @return
	 */
	int Share2Recycle(@Param("user_id")String user_id, @Param("share_id")String share_id);

	/**
	 * 根据用户id加载share_status = 1的动态
	 * @param user_id
	 * @return
	 */
	List<Share> loadRecycleShare(@Param("user_id")String user_id);

	/**
	 * 将动态的share_status 从1变为0
	 * @param user_id
	 * @param share_id
	 * @return
	 */
	int resetShare(@Param("user_id")String user_id, @Param("share_id")String share_id);

	/**
	 * 根据动态id删除动态
	 * @param share_id
	 * @return
	 */
	int deleteShare(String share_id);

	/**
	 * 根据tag加载share
	 * @param tag
	 * @return
	 */
	List<Share> loadShareByTag(@Param("tag")String tag);
	

	


//	void resetShare(String user_id);

}
