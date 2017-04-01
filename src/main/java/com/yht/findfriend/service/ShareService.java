package com.yht.findfriend.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yht.findfriend.entity.ResultMap;
import com.yht.findfriend.entity.Share;

@Service
public interface ShareService {

	/**
	 * 发布动态的服务方法
	 * @param share
	 * @param share_image
	 * @param request
	 * @return
	 */
	ResultMap sendShare(Share share, MultipartFile[] share_image, HttpServletRequest request);

	/**
	 * 加载动态的服务方法
	 * @param user_id
	 * @param index
	 * @return
	 */
	ResultMap loadShare(String user_id, int index);

	/**
	 * 加载热门动态的服务方法
	 * @param user_id
	 * @param index 
	 * @return
	 */
	ResultMap loadHotShare(String user_id, int index);

	/**
	 * 加载好友动态的服务方法
	 * @param user_id
	 * @param index 
	 * @return
	 */
	ResultMap loadFriendShare(String user_id, int index);

	/**
	 * 点赞功能的服务方法
	 * @param request 
	 */
	ResultMap clickGreed(String share_id, String user_id, HttpServletRequest request);

	/**
	 * 加载好友动态的服务方法
	 * @param friend_id
	 * @param index 
	 * @return
	 */
	ResultMap loadSpecificShare(String friend_id, int index);


/*	//重置动态载入状态的服务方法
	ResultMap resetShare(String user_id);*/



}