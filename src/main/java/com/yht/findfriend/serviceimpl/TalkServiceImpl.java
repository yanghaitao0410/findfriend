package com.yht.findfriend.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		int talk_id = talkDao.sendTalk(geTalk(talk, request));
		ResultMap resultMap = new ResultMap();
		if(talk_id > 0){
			resultMap.setStatus(0);
			resultMap.setMsg("发布评论成功！！");
			Map map = new HashMap<String, Integer>();
			map.put("talk_id", talk_id);
			resultMap.setData(map);
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

	@Override
	public ResultMap deleteTalk(Talk talk) {
		int count = talkDao.deleteTalk(talk);
		ResultMap resultMap = new ResultMap();
		if(count == 1){
			resultMap.setStatus(0);
			resultMap.setMsg("删除评论成功");
		}else{
			resultMap.setStatus(1);
			resultMap.setMsg("删除评论失败");
		}
		return resultMap;
	}
	
	/**
	 * 将评论格式化时间转化为对应的毫秒数
	 * @param talk
	 * @return
	 */
	@SuppressWarnings("unused")
	private Talk setalkCreatime(Talk talk){
		String time_str = talk.getCreatime_str();
		System.out.println(time_str);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		try {
			Date date = format.parse(time_str);
			talk.setTalk_creatime(date.getTime());
			System.out.println(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return talk;
	}

}
