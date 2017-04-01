package com.yht.findfriend.entity;

public class Great {

	private int great_id;
	private int user_id;
	private int share_id;
	public int getGreat_id() {
		return great_id;
	}
	public void setGreat_id(int great_id) {
		this.great_id = great_id;
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
	@Override
	public String toString() {
		return "Great [great_id=" + great_id + ", user_id=" + user_id + ", share_id=" + share_id + "]";
	}
	
	
}
