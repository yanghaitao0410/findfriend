package com.yht.findfriend.dao;

import java.util.List;

import com.yht.findfriend.entity.Talk;

public interface TalkDao {

	/**
	 * 新增动态评论
	 * @param Talk
	 * @return
	 */
	int sendTalk(Talk Talk);

	/**
	 * 根据动态id将评论查出
	 * @param share_id
	 * @return
	 */
	List<Talk> queryTalk(int share_id);

}
