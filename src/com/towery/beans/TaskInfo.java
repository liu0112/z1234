package com.towery.beans;

/**
 * 
 * @author
 * 
 */
public class TaskInfo {

	private String taskid;

	private String userid;

	private String taskdesc;

	private String tasktype;

	private String taskstatus;

	private String taskfinish;

	private String taskname;

	private String rollbaskData = "";

	// 用来恢复图片表的ucode；
	private String rollBackImgUcode = "";

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTaskdesc() {
		return taskdesc;
	}

	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getTaskstatus() {
		return taskstatus;
	}

	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}

	public String getTaskfinish() {
		return taskfinish;
	}

	public void setTaskfinish(String taskfinish) {
		this.taskfinish = taskfinish;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getRollbaskData() {
		return rollbaskData;
	}

	public void setRollbaskData(String rollbaskData) {
		this.rollbaskData = rollbaskData;
	}

	public String getRollBackImgUcode() {
		return rollBackImgUcode;
	}

	public void setRollBackImgUcode(String rollBackImgUcode) {
		this.rollBackImgUcode = rollBackImgUcode;
	}

	@Override
	public String toString() {
		return "TaskInfo [taskid=" + taskid + ",  userid=" + userid
				+ ", taskdesc=" + taskdesc + ", tasktype=" + tasktype
				+ ", taskstatus=" + taskstatus + ", taskfinish=" + taskfinish
				+ ", taskname=" + taskname + ", rollbaskData=" + rollbaskData
				+ ", rollBackImgUcode=" + rollBackImgUcode + "]";
	}

	public TaskInfo(String taskid, String usetid, String userid,
			String taskdesc, String tasktype, String taskstatus,
			String taskfinish, String taskname, String rollbaskData,
			String rollBackImgUcode) {
		super();
		this.taskid = taskid;
		this.userid = userid;
		this.taskdesc = taskdesc;
		this.tasktype = tasktype;
		this.taskstatus = taskstatus;
		this.taskfinish = taskfinish;
		this.taskname = taskname;
		this.rollbaskData = rollbaskData;
		this.rollBackImgUcode = rollBackImgUcode;
	}

	public TaskInfo() {
		super();
	}

}
