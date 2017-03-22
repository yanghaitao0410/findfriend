package com.yht.findfriend.entity;

public class ResultMap {

	private int status;
	private String msg;
	private User user;
	private Object data;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "ResultMap [status=" + status + ", msg=" + msg + ", user=" + user + ", data=" + data + "]";
	}
	
	
}
