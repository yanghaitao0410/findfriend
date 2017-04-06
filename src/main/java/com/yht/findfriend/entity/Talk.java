package com.yht.findfriend.entity;

import java.text.SimpleDateFormat;
import java.util.Date;





public class Talk {

	private int talk_id;
	private int share_id;
	private int talk_user_id;
	private String talk_user_name;
	private String talk_info;
	private Long talk_creatime;
	//格式化后的时间
	private String creatime_str;
	
	public String getCreatime(){
		if(talk_creatime != null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
			Date date = new Date(talk_creatime);
			return format.format(date);
		}
		return "";
	}
	
	public int getTalk_id() {
		return talk_id;
	}
	public void setTalk_id(int talk_id) {
		this.talk_id = talk_id;
	}
	public int getShare_id() {
		return share_id;
	}
	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}
	public int getTalk_user_id() {
		return talk_user_id;
	}
	public void setTalk_user_id(int talk_user_id) {
		this.talk_user_id = talk_user_id;
	}
	public String getTalk_user_name() {
		return talk_user_name;
	}
	public void setTalk_user_name(String talk_user_name) {
		this.talk_user_name = talk_user_name;
	}
	public String getTalk_info() {
		return talk_info;
	}
	public void setTalk_info(String talk_info) {
		this.talk_info = talk_info;
	}
	public Long getTalk_creatime() {
		return talk_creatime;
	}
	public void setTalk_creatime(Long talk_creatime) {
		this.talk_creatime = talk_creatime;
	}

	public String getCreatime_str() {
		return creatime_str;
	}

	public void setCreatime_str(String creatime_str) {
		this.creatime_str = creatime_str;
	}

	@Override
	public String toString() {
		return "Talk [talk_id=" + talk_id + ", share_id=" + share_id + ", talk_user_id=" + talk_user_id
				+ ", talk_user_name=" + talk_user_name + ", talk_info=" + talk_info + ", talk_creatime=" + talk_creatime
				+ ", creatime_str=" + creatime_str + "]";
	}
	
	
}
