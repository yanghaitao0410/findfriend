package com.yht.findfriend.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import com.yht.findfriend.entity.Friend;

public class AccountUtil {

	public static String md5(String user_pwd){
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] input = user_pwd.getBytes();
			byte[] output = md.digest(input);
			result = Base64.encodeBase64String(output);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("密码加密失败！！！");
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String str = "123qwe";
		System.out.println(md5(str));
	}
	
	/**
	 * 返回当前用户的id和当前用户的所有好友id的集合
	 * @param user_id
	 * @param friends
	 * @return
	 */
	public static List<Integer> getIds(String user_id, List<Friend> friends){
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(Integer.parseInt(user_id));
		if(friends != null && friends.size() > 0){
			for (Friend friend : friends) {
				ids.add(friend.getFriend_id());
			}
		}
		return ids;
	}
	
}
