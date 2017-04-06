package com.yht.findfriend.dao;

import java.util.List;
import java.util.Map;

import com.yht.findfriend.entity.Talk;

public interface TalkDao {

	/**
	 * 新增动态评论
	 * @param Talk
	 * @return
	 */
	int sendTalk(Talk Talk);

	/**
	 * 根据动态id或用户名将评论查出
	 * @param share_id
	 * @return
	 */
//	List<Talk> queryTalk(Map<String, Object> data);
	List<Talk> queryTalk(Talk talk);

	/**
	 * 根据评论用户名和评论时间删除评论
	 * @param talk
	 * @return
	 */
	int deleteTalk(Talk talk);

}
