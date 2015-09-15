package com.towery.beans;
/**
 * 
 * @author 
 *
 */
public class QualifyData {

	private String taskid;
	
	private String questtionid;
	
	private String answer;
	
	private String addrname;
	
	private String x;
	
	private String y;
	
	private String remark;
	
	private String gPSx;
	
	private String gPSy;
	
	private String gPStime;
	
	private String mx;
	
	private String my;
	
	private String maddrname;
	
	private String result;
	
	private String ucode;
	
	private String sid;

	public QualifyData(String taskid, String questtionid, String answer,
			String addrname, String x, String y, String remark, String gPSx,
			String gPSy, String gPStime, String mx, String my,
			String maddrname, String result, String ucode, String sid) {
		super();
		this.taskid = taskid;
		this.questtionid = questtionid;
		this.answer = answer;
		this.addrname = addrname;
		this.x = x;
		this.y = y;
		this.remark = remark;
		this.gPSx = gPSx;
		this.gPSy = gPSy;
		this.gPStime = gPStime;
		this.mx = mx;
		this.my = my;
		this.maddrname = maddrname;
		this.result = result;
		this.ucode = ucode;
		this.sid = sid;
	}

	public QualifyData() {
		super();
	}

	@Override
	public String toString() {
		return "QualifyData [taskid=" + taskid + ", questtionid=" + questtionid
				+ ", answer=" + answer + ", addrname=" + addrname + ", x=" + x
				+ ", y=" + y + ", remark=" + remark + ", gPSx=" + gPSx
				+ ", gPSy=" + gPSy + ", gPStime=" + gPStime + ", mx=" + mx
				+ ", my=" + my + ", maddrname=" + maddrname + ", result="
				+ result + ", ucode=" + ucode + ", sid=" + sid + "]";
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getQuesttionid() {
		return questtionid;
	}

	public void setQuesttionid(String questtionid) {
		this.questtionid = questtionid;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAddrname() {
		return addrname;
	}

	public void setAddrname(String addrname) {
		this.addrname = addrname;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getgPSx() {
		return gPSx;
	}

	public void setgPSx(String gPSx) {
		this.gPSx = gPSx;
	}

	public String getgPSy() {
		return gPSy;
	}

	public void setgPSy(String gPSy) {
		this.gPSy = gPSy;
	}

	public String getgPStime() {
		return gPStime;
	}

	public void setgPStime(String gPStime) {
		this.gPStime = gPStime;
	}

	public String getMx() {
		return mx;
	}

	public void setMx(String mx) {
		this.mx = mx;
	}

	public String getMy() {
		return my;
	}

	public void setMy(String my) {
		this.my = my;
	}

	public String getMaddrname() {
		return maddrname;
	}

	public void setMaddrname(String maddrname) {
		this.maddrname = maddrname;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUcode() {
		return ucode;
	}

	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	
}
