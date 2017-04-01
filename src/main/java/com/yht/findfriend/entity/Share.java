package com.yht.findfriend.entity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Share {

	private int user_id;
	private String user_name;
	private int share_id;
	private String share_msg;
	private String image_uri;
	private String[] images;
	private int share_status;
	private String share_tag;
	private int endorse_count;
	private Long share_creatime;
	private List<Map<String, String>> talk_list;
	
	public List<Map<String, String>> getTalk_list() {
		return talk_list;
	}

	public void setTalk_list(List<Map<String, String>> talk_list) {
		this.talk_list = talk_list;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCreatime(){
		if(share_creatime != null){
			Date date = new Date(share_creatime);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd : hh-mm-ss");
			String result = format.format(date);
			return result;
		}else{
			return "";
		}
		
	}
	
	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getShare_id() {
		return share_id;
	}
	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}
	public String getShare_msg() {
		return share_msg;
	}
	public void setShare_msg(String share_msg) {
		this.share_msg = share_msg;
	}
	
	public String getImage_uri() {
		return image_uri;
	}
	public void setImage_uri(String image_uri) {
		this.image_uri = image_uri;
	}
	public int getShare_status() {
		return share_status;
	}
	public void setShare_status(int share_status) {
		this.share_status = share_status;
	}
	public String getShare_tag() {
		return share_tag;
	}
	public void setShare_tag(String share_tag) {
		this.share_tag = share_tag;
	}
	public int getEndorse_count() {
		return endorse_count;
	}
	public void setEndorse_count(int endorse_count) {
		this.endorse_count = endorse_count;
	}

	public Long getShare_creatime() {
		return share_creatime;
	}
	public void setShare_creatime(Long share_creatime) {
		this.share_creatime = share_creatime;
	}
	@Override
	public String toString() {
		return "Share [user_id=" + user_id + ", user_name=" + user_name + ", share_id=" + share_id + ", share_msg="
				+ share_msg + ", image_uri=" + image_uri + ", images=" + Arrays.toString(images) + ", share_status="
				+ share_status + ", share_tag=" + share_tag + ", endorse_count=" + endorse_count + ", share_creatime="
				+ share_creatime + ", talk_list=" + talk_list + "]";
	}
	
	
	
}
