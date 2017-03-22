package com.yht.findfriend.entity;

public class Friend {

	private int user_id;
	private int friend_id;
	private int parent_id;
	private String friend_name;
	private int status;
	private String group_name;
	private int friend_sex;
	private String friend_phonenum;
	private String nick_name;
	
	public int getFriend_sex() {
		return friend_sex;
	}
	public void setFriend_sex(int friend_sex) {
		this.friend_sex = friend_sex;
	}
	public String getFriend_phonenum() {
		return friend_phonenum;
	}
	public void setFriend_phonenum(String friend_phonenum) {
		this.friend_phonenum = friend_phonenum;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	@Override
	public String toString() {
		return "Friend [user_id=" + user_id + ", friend_id=" + friend_id + ", parent_id=" + parent_id + ", friend_name="
				+ friend_name + ", status=" + status + ", group_name=" + group_name + ", friend_sex=" + friend_sex
				+ ", friend_phonenum=" + friend_phonenum + ", nick_name=" + nick_name + "]";
	}
	
	
}
