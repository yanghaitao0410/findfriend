package com.yht.findfriend.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yht.findfriend.dao.FriendDao;
import com.yht.findfriend.dao.GreatDao;
import com.yht.findfriend.dao.ShareDao;
import com.yht.findfriend.dao.TalkDao;
import com.yht.findfriend.dao.UserDao;
import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.Share;
import com.yht.findfriend.entity.Talk;
import com.yht.findfriend.service.ShareService;

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
	
	@Override
	public ResultMap sendShare(Share share, MultipartFile[] share_images, HttpServletRequest request) {
		ResultMap result = new ResultMap();
		int count = shareDao.sendShare(getShare(share, share_images, request));
		if(count == 1){
			result.setStatus(0);
			result.setMsg("发布动态成功！！！");
		}else{
			result.setStatus(1);
			result.setMsg("发布动态失败！！！");
		}
		return result;
	}
	
	/**
	 * 为share赋值，做将动态存入数据库之前的准备工作
	 * @param share
	 * @param share_images
	 * @param request
	 * @return
	 */
	private Share getShare(Share share, MultipartFile[] share_images, HttpServletRequest request){
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		String user_name = (String) session.getAttribute("user_name");
		share.setUser_id(Integer.parseInt(user_id));
		share.setUser_name(user_name);
		if(share_images != null){
			try {
				share.setImage_uri(getImageUri(share_images, user_id));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {	
				e.printStackTrace();
			}
		}
		share.setShare_creatime(System.currentTimeMillis());
		return share;
	}
	
	/**
	 * 将图片存放到服务器，返回图片地址
	 * @param share_image
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	private String getImageUri(MultipartFile[] share_images, String user_id) throws IllegalStateException, IOException{
		String savePath = "D:/upload/" + user_id ;
		StringBuilder builder = new StringBuilder();
		for (MultipartFile multipartFile : share_images) {
			String image_name = multipartFile.getOriginalFilename();
			String save_name = getSaveName(image_name);
			builder.append(save_name);
			builder.append(",");
			File file = new File(savePath, save_name);
			if(!file.exists()){
				file.mkdirs();
			}
			multipartFile.transferTo(file);
		}
		return builder.toString();
	}

	/**
	 * 为了防止图片重名被覆盖，在图片名前加上不重复的uuid
	 * @param name
	 * @return
	 */
	private String getSaveName(String name){
		return UUID.randomUUID() + "_" +name;
	}

	@Override
	public ResultMap loadShare(String user_id, int index) {
		List<Share> data = shareDao.loadShare(user_id, index);
		return getResultMap(data);
	}
	
	/**
	 * Share处理图片路径，存入返回实体类的封装
	 * 处理评论信息，
	 * TODO 处理动态tag
	 * @param data
	 * @return
	 */
	private ResultMap getResultMap(List<Share> data) {
		for (Share share : data) {
			share.setImages(splitUri(share.getImage_uri()));
			//将share对应的talk信息查出存入share
			share = setTalks(share);
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
		if(talks.size() > 0){
			List<Map<String, String>> talk_list = new ArrayList<Map<String, String>>();
			for (Talk talk : talks) {
				Map<String, String> talk_map = new HashMap<String, String>();
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
		List<Talk> talks = talkDao.queryTalk(share_id);
		return talks;
	}
	
	/**
	 * 将图片地址集合拆分成单张图片地址
	 * @param image_uri
	 * @return
	 */
	private String[] splitUri(String image_uri){
		String[] result = image_uri.split(",");
//		for (String string : result) {
//			System.out.println(string);
//		}
		return result;
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

	@Override
	public ResultMap clickGreed(String share_id, String user_id, HttpServletRequest request) {
		Share share = shareDao.loadShareByShare_id(share_id); 
		int count = greatDao.getGreat(user_id, share_id);
		if(count == 1){//该用户已经赞过
			share.setEndorse_count(share.getEndorse_count() - 1);
			greatDao.deleteGreat(share_id, user_id);
		}else if(count == 0){ //该用户没有赞过
			share.setEndorse_count(share.getEndorse_count() + 1);
			greatDao.insertGreat(share_id, user_id);
		}
		request.setAttribute("endorse_count", share.getEndorse_count());
		count = shareDao.updateShare(share);
		ResultMap resultMap = new ResultMap();
		if(count == 1){
			resultMap.setStatus(0);
			resultMap.setMsg("点赞成功");
			resultMap.setData(share.getEndorse_count());
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

	
	/*@Override
	public ResultMap resetShare(String user_id) {
		shareDao.resetShare(user_id);
		ResultMap result = new ResultMap();
		result.setStatus(0);
		result.setMsg("重置动态状态成功");
		return result;
	}*/

}
