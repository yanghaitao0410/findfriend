package com.yht.findfriend.serviceimpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.yht.findfriend.dao.TalkDao;
import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.Talk;
import com.yht.findfriend.service.TalkService;

@Service
public class TalkServiceImpl implements TalkService {

	@Resource
	private TalkDao talkDao;
	
	@Override
	public ResultMap sendTalk(Talk talk, HttpServletRequest request) {
		int count = talkDao.sendTalk(geTalk(talk, request));
		ResultMap resultMap = new ResultMap();
		if(count == 1){
			resultMap.setStatus(0);
			resultMap.setMsg("发布评论成功！！");
		}else{
			resultMap.setStatus(1);
			resultMap.setMsg("发布评论失败");
		}
		return resultMap;
	}
	
	/**
	 * 为talk对象赋值，做存放到数据库之前的准备工作
	 * @param talk
	 * @param request
	 * @return
	 */
	private Talk geTalk(Talk talk, HttpServletRequest request){
		HttpSession session = request.getSession();
		String talk_user_id = (String) session.getAttribute("user_id");
		String talk_user_name = (String) session.getAttribute("user_name");
		talk.setTalk_user_id(Integer.parseInt(talk_user_id));
		talk.setTalk_user_name(talk_user_name);
		talk.setTalk_creatime(System.currentTimeMillis());
		return talk;
	}
	

}
