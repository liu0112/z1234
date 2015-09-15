package com.towery.beans;

/**
 * 
 * @author 
 *
 */
public class CollectData {

	private String taskid;
	
	private String questionid;
	
	private String content;
	
	private String answer;
	
	private String x;
	
	private String y;
	
	private String GPSx;
	
	private String GPSy;
	
	private String GPStime;
	
	private String remark;
	
	private String syscollectuuid;

	public CollectData() {
		super();
	}

	public CollectData(String taskid, String questionid, String content,
			String answer, String x, String y, String gPSx, String gPSy,
			String gPStime, String remark, String syscollectuuid) {
		super();
		this.taskid = taskid;
		this.questionid = questionid;
		this.content = content;
		this.answer = answer;
		this.x = x;
		this.y = y;
		GPSx = gPSx;
		GPSy = gPSy;
		GPStime = gPStime;
		this.remark = remark;
		this.syscollectuuid = syscollectuuid;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getGPSx() {
		return GPSx;
	}

	public void setGPSx(String gPSx) {
		GPSx = gPSx;
	}

	public String getGPSy() {
		return GPSy;
	}

	public void setGPSy(String gPSy) {
		GPSy = gPSy;
	}

	public String getGPStime() {
		return GPStime;
	}

	public void setGPStime(String gPStime) {
		GPStime = gPStime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSyscollectuuid() {
		return syscollectuuid;
	}

	public void setSyscollectuuid(String sysCollectuuid) {
		this.syscollectuuid = sysCollectuuid;
	}

	@Override
	public String toString() {
		return "CollectData [taskid=" + taskid + ", questionid=" + questionid
				+ ", content=" + content + ", answer=" + answer + ", x=" + x
				+ ", y=" + y + ", GPSx=" + GPSx + ", GPSy=" + GPSy
				+ ", GPStime=" + GPStime + ", remark=" + remark
				+ ", syscollectuuid=" + syscollectuuid + "]";
	}

	
}
