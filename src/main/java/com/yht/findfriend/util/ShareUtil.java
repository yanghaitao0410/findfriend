package com.yht.findfriend.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.yht.findfriend.entity.Share;

public class ShareUtil {

	/**
	 * 将tag字符串拆分为一个个tag
	 * @param tag
	 * @return
	 */
	public static String[] splitTag(String tag){
		String[]tags = tag.split(",");
		for (int i = 0; i < tags.length; i++) {
			tags[i] = tags[i].trim();
		}
		return tags;
	}
	
	/**
	 * 将图片地址集合拆分成单张图片地址
	 * @param image_uri
	 * @return
	 */
	public static String[] splitUri(String image_uri){
		String[] result = image_uri.split(",");
//		for (String string : result) {
//			System.out.println(string);
//		}
		return result;
	}
	
	/**
	 * 删除动态的图片
	 * @param share
	 * @return
	 */
	public static boolean deleteImage(Share share){
		boolean result = true;
		String[] images_uri = ShareUtil.splitUri(share.getImage_uri());
		for (String image_uri : images_uri) {
			File file = new File(
					"d:"+ File.separator +"upload" + File.separator + 
					share.getUser_id() + File.separator + image_uri);
			if(file.exists()){
				if(!file.delete()){
					result = false;
				}
			}
		}
		return result;
	}
	
	/**
	 * 为了防止图片重名被覆盖，在图片名前加上不重复的uuid
	 * @param name
	 * @return
	 */
	public static String getSaveName(String name){
		return UUID.randomUUID() + "_" +name;
	}
	
	/**
	 * 将图片存放到服务器，返回图片地址
	 * @param share_image
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static String getImageUri(MultipartFile[] share_images, String user_id) throws IllegalStateException, IOException{
		String savePath = "D:/upload/" + user_id ;
		StringBuilder builder = new StringBuilder();
		for (MultipartFile multipartFile : share_images) {
			String image_name = multipartFile.getOriginalFilename();
			String save_name = ShareUtil.getSaveName(image_name);
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
	 * 为share赋值，做将动态存入数据库之前的准备工作
	 * @param share
	 * @param share_images
	 * @param request
	 * @return
	 */
	public static Share getShare(Share share, MultipartFile[] share_images, HttpServletRequest request){
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		String user_name = (String) session.getAttribute("user_name");
		share.setUser_id(Integer.parseInt(user_id));
		share.setUser_name(user_name);
		if(share_images != null){
			try {
				share.setImage_uri(ShareUtil.getImageUri(share_images, user_id));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {	
				e.printStackTrace();
			}
		}
		share.setShare_creatime(System.currentTimeMillis());
		return share;
	}
	
	
	
}
