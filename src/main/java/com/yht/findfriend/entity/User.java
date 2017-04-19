package com.yht.findfriend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

	private int user_id;
	private int friend_id;
	private String user_name;
	private String user_pwd;
	private String nick_name;
	private String user_info;
	private String user_sex;
	private String user_phone_num;
	//返回json字符串时会忽略这个属性
	@JsonIgnore
	private String user_education;
	
	public int getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getUser_info() {
		return user_info;
	}
	public void setUser_info(String user_info) {
		this.user_info = user_info;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public String getUser_phone_num() {
		return user_phone_num;
	}
	public void setUser_phone_num(String user_phone_num) {
		this.user_phone_num = user_phone_num;
	}
	public String getUser_education() {
		return user_education;
	}
	public void setUser_education(String user_education) {
		this.user_education = user_education;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", user_pwd=" + user_pwd + ", nick_name="
				+ nick_name + ", user_info=" + user_info + ", user_sex=" + user_sex + ", user_phone_num="
				+ user_phone_num + ", user_education=" + user_education + "]";
	}
	
	
}
