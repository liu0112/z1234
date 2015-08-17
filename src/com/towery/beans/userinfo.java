package com.towery.beans;

public class userinfo {
	private String RecNo;
	private String userid;
	private String userpswd;
	private String username;

	public String getRecNo() {
		return RecNo;
	}

	public void setRecNo(String recNo) {
		RecNo = recNo;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpswd() {
		return userpswd;
	}

	public void setUserpswd(String userpswd) {
		this.userpswd = userpswd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "userinfo [RecNo=" + RecNo + ", userid=" + userid
				+ ", userpswd=" + userpswd + ", username=" + username + "]";
	}

	public userinfo(String recNo, String userid, String userpswd,
			String username) {
		super();
		RecNo = recNo;
		this.userid = userid;
		this.userpswd = userpswd;
		this.username = username;
	}

	public userinfo() {
		super();
	}

}
