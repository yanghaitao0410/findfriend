package com.yht.findfriend.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.Talk;

@Service
public interface TalkService {

	/**
	 * 发表评论的服务方法
	 * @param talk
	 * @param request 
	 * @return
	 */
	ResultMap sendTalk(Talk talk, HttpServletRequest request);

}
