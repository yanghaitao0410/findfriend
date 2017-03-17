package com.yht.findfriend.entity;

import java.util.List;

public class ResultMap {

	private int status;
	private String msg;
	private User user;
	private List list;
	public int getStatus() {
		return status;
	}
	public void setStatus(int state) {
		this.status = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
