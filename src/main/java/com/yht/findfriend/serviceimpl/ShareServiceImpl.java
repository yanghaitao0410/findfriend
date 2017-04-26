package com.yht.findfriend.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yht.findfriend.dao.FriendDao;
import com.yht.findfriend.dao.GreatDao;
import com.yht.findfriend.dao.ShareDao;
import com.yht.findfriend.dao.TalkDao;
import com.yht.findfriend.dao.UserDao;
import com.yht.findfriend.entity.Friend;
import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.Share;
import com.yht.findfriend.entity.Talk;
import com.yht.findfriend.entity.User;
import com.yht.findfriend.service.FriendService;
import com.yht.findfriend.service.ShareService;
import com.yht.findfriend.util.AccountUtil;
import com.yht.findfriend.util.ShareUtil;

@Service
public class ShareServiceImpl implements ShareService {

	@Resource
	private ShareDao shareDao;
	@Resource
	private UserDao userDao;
	@Resource
	private FriendDao friendDao;
	@Resource
	private GreatDao greatDao;
	@Resource
	private TalkDao talkDao;
	@Resource 
	private FriendService friendService;
	
	@Override
	public ResultMap sendShare(Share share, MultipartFile[] share_images, HttpServletRequest request) {
		ResultMap result = new ResultMap();
		int count = shareDao.sendShare(ShareUtil.getShare(share, share_images, request));
		if(count == 1){
			result.setStatus(0);
			result.setMsg("发布动态成功！！！");
		}else{
			result.setStatus(1);
			result.setMsg("发布动态失败！！！");
		}
		return result;
	}
	
	@Override
	public ResultMap loadShare(String user_id, int index) {
		List<Share> data = shareDao.loadShare(user_id, index);
		return getResultMap(data);
	}
	
	/**
	 * Share处理图片路径，存入返回实体类的封装<br>
	 * 处理评论信息，<br>
	 * TODO 处理动态tag
	 * @param data
	 * @return
	 */
	private ResultMap getResultMap(List<Share> data) {
		for (Share share : data) {
			share.setImages(ShareUtil.splitUri(share.getImage_uri()));
			//将share对应的talk信息查出存入share
			share = setTalks(share);
			if(share.getShare_tag() != null){
				share.setTags(ShareUtil.splitTag(share.getShare_tag()));
			}
		}
		ResultMap resultMap = new ResultMap();
		if(data.size() > 0){
			resultMap.setStatus(0);
			resultMap.setMsg("加载动态成功");
			resultMap.setData(data);
		}else{
			resultMap.setStatus(1);
			resultMap.setMsg("没有更多动态了");
		}
		return resultMap;
	}

	
	
	
	/**
	 * 为动态设置评论信息
	 * @param share
	 * @return
	 */
	private Share setTalks(Share share){
		List<Talk> talks = queryTalk(share.getShare_id());
		if(talks != null && talks.size() > 0){
			List<Map<String, String>> talk_list = new ArrayList<Map<String, String>>();
			for (Talk talk : talks) {
				Map<String, String> talk_map = new HashMap<String, String>();
				talk_map.put("talk_id", String.valueOf(talk.getTalk_id()));
				talk_map.put("talk_user_name", talk.getTalk_user_name());
				talk_map.put("talk_info", talk.getTalk_info());
				talk_map.put("talk_creatime", talk.getCreatime());
				talk_list.add(talk_map);
			}
			share.setTalk_list(talk_list);
		}
		return share;
	}
	
	/**
	 * 查询动态评论
	 * @param share_id
	 * @return
	 */
	private List<Talk> queryTalk(int share_id){
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("share_id", share_id);
		Talk talk = new Talk();
		talk.setShare_id(share_id);
		List<Talk> talks = talkDao.queryTalk(talk);
		return talks;
	}
	


	@Override
	public ResultMap loadHotShare(String user_id, int index) {
		List<Share> data = shareDao.loadHotShare(user_id, index);
		
		return getResultMap(data);
	}

	@Override
	public ResultMap loadFriendShare(String user_id, int index) {
		List<Share> shares = shareDao.loadFriendShare(user_id, index);		
		return getResultMap(shares);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultMap clickGreed(String share_id, String user_id, HttpServletRequest request) {
		Share share = shareDao.loadShareByShare_id(share_id);
		Map<String ,Object> data = new HashMap<String, Object>();
		int count = greatDao.getGreat(user_id, share_id);
		if(count == 1){//该用户已经赞过
			share.setEndorse_count(share.getEndorse_count() - 1);
			greatDao.deleteGreat(share_id, user_id);
		}else if(count == 0){ //该用户没有赞过
			share.setEndorse_count(share.getEndorse_count() + 1);
			greatDao.insertGreat(share_id, user_id);
			//推荐好友
			ResultMap result = friendService.loadReComInfo(share.getUser_id()+"");
			if(result.getStatus() == 0){
				data = (Map<String, Object>)result.getData();
			}
		}
		request.setAttribute("endorse_count", share.getEndorse_count());
		count = shareDao.updateShare(share);
		ResultMap resultMap = new ResultMap();
		if(count == 1){
			resultMap.setStatus(0);
			resultMap.setMsg("点赞成功");
			data.put("endorse_count", share.getEndorse_count());
			resultMap.setData(data);
		}else{
			resultMap.setStatus(1);
			resultMap.setMsg("点赞失败");
		}
		return resultMap;
	}

	@Override
	public ResultMap loadSpecificShare(String friend_id, int index) {
		List<Share> shares = shareDao.loadSpecificShare(friend_id, index);
		return getResultMap(shares);
	}

	@Override
	public ResultMap loadTalkedShare(int talk_user_id, int index) {
		
		Talk talk = new Talk();
		talk.setTalk_user_id(talk_user_id);
		
		/*Map<String, Object> data = new HashMap<String, Object>();
		data.put("talk_user_id", talk_user_id);
		List<Talk> talks = talkDao.queryTalk(data);*/
		List<Talk> talks = talkDao.queryTalk(talk);
		
		if(talks != null && talks.size() > 0){
			List<Integer> share_ids = new ArrayList<Integer>();
			for (Talk t : talks) {
				share_ids.add(t.getShare_id());
			}
			System.out.println("index: "+index);
			List<Share> shares = shareDao.loadShareByTalkORGreat(share_ids, index);
			return getResultMap(shares);
		}
		return null;
	}

	@Override
	public ResultMap loadGreatShare(String user_id, int index) {
		List<Integer> share_ids = greatDao.getShare_id(user_id);
		if(share_ids != null && share_ids.size() > 0){
			List<Share> shares = shareDao.loadShareByTalkORGreat(share_ids, index);
			return getResultMap(shares);
		}
		
		return null;
	}

	@Override
	public ResultMap Share2Recycle(String user_id, String share_id) {
		int count = shareDao.Share2Recycle(user_id, share_id);
		ResultMap resultMap = new ResultMap();
		if(count == 1){
			resultMap.setStatus(0);
			resultMap.setMsg("删除动态成功！！您可以在回收站彻底删除该动态");
		}else{
			resultMap.setStatus(1);
			resultMap.setMsg("删除动态失败");
		}
		return resultMap;
	}

	@Override
	public ResultMap loadRecycleShare(String user_id) {
		List<Share> shares = shareDao.loadRecycleShare(user_id);
		return getResultMap(shares);
	}

	@Override
	public ResultMap resetShare(String user_id, String share_id) {
		int count = shareDao.resetShare(user_id, share_id);
		ResultMap resultMap = new ResultMap();
		if(count == 1){
			resultMap.setStatus(0);
			resultMap.setMsg("动态已撤回！！！");
		}else{
			resultMap.setStatus(1);
			resultMap.setMsg("动态撤回失败！！！");
		}
		return resultMap;
	}

	@Override
	public ResultMap deleteShare(String user_id, String share_id) {
		Share share = shareDao.loadShareByShare_id(share_id);
		ResultMap resultMap = new ResultMap();
		
		if(ShareUtil.deleteImage(share)){
			int count = shareDao.deleteShare(share_id);
			greatDao.deleteGreat(share_id, user_id);
			Talk talk = new Talk();
			talk.setTalk_user_id(Integer.parseInt(user_id));
			talk.setShare_id(Integer.parseInt(share_id));
			talkDao.deleteTalk(talk);
			if(count == 1){
				resultMap.setStatus(0);
				resultMap.setMsg("删除动态成功！！！！！");
				return resultMap;
			}
		}
		resultMap.setStatus(1);
		resultMap.setMsg("删除动态失败！！！！");
		return resultMap;
	}



	@Override
	public ResultMap loadShareByTag(String tag) {
		List<Share> shares = shareDao.loadShareByTag(tag);
		return getResultMap(shares);
	}


}
