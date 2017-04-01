package com.yht.findfriend.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.Talk;
import com.yht.findfriend.service.TalkService;

@Controller
@RequestMapping("/talk")
public class TalkController {

	@Resource
	private TalkService talkService;
	/**
	 * 用户发布评论
	 * 实际传入的参数
	 * @param talk_info  share_id
	 * @return
	 */
	@RequestMapping("sendTalk")
	@ResponseBody
	public ResultMap sendTalk(Talk talk, HttpServletRequest request){
		ResultMap resultMap = talkService.sendTalk(talk, request);
		return resultMap;
	}
	
	
}
