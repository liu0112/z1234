package com.towery.beans;

/**
 * @author lar
 * 
 */
public class SetUp {

	private int _id;
	private String djms;
	private String userid;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	private String tzms;
	private String dwrwq;
	private String xzbz;
	private String xsbz;
	private String qhrwdw;
	private String xspz;
	private String sjkgx;
	private String sjksj;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getDjms() {
		return djms;
	}
	public void setDjms(String djms) {
		this.djms = djms;
	}
	public String getTzms() {
		return tzms;
	}
	public void setTzms(String tzms) {
		this.tzms = tzms;
	}
	public String getDwrwq() {
		return dwrwq;
	}
	public void setDwrwq(String dwrwq) {
		this.dwrwq = dwrwq;
	}
	public String getXzbz() {
		return xzbz;
	}
	public void setXzbz(String xzbz) {
		this.xzbz = xzbz;
	}
	public String getXsbz() {
		return xsbz;
	}
	public void setXsbz(String xsbz) {
		this.xsbz = xsbz;
	}
	public String getQhrwdw() {
		return qhrwdw;
	}
	public void setQhrwdw(String qhrwdw) {
		this.qhrwdw = qhrwdw;
	}
	public String getXspz() {
		return xspz;
	}
	public void setXspz(String xspz) {
		this.xspz = xspz;
	}
	public String getSjkgx() {
		return sjkgx;
	}
	public void setSjkgx(String sjkgx) {
		this.sjkgx = sjkgx;
	}
	public String getSjksj() {
		return sjksj;
	}
	public void setSjksj(String sjksj) {
		this.sjksj = sjksj;
	}

	public SetUp(int _id, String djms, String userid, String tzms,
			String dwrwq, String xzbz, String xsbz, String qhrwdw, String xspz,
			String sjkgx, String sjksj) {
		super();
		this._id = _id;
		this.djms = djms;
		this.userid = userid;
		this.tzms = tzms;
		this.dwrwq = dwrwq;
		this.xzbz = xzbz;
		this.xsbz = xsbz;
		this.qhrwdw = qhrwdw;
		this.xspz = xspz;
		this.sjkgx = sjkgx;
		this.sjksj = sjksj;
	}
	@Override
	public String toString() {
		return "SetUp [_id=" + _id + ", djms=" + djms + ", userid=" + userid
				+ ", tzms=" + tzms + ", dwrwq=" + dwrwq + ", xzbz=" + xzbz
				+ ", xsbz=" + xsbz + ", qhrwdw=" + qhrwdw + ", xspz=" + xspz
				+ ", sjkgx=" + sjkgx + ", sjksj=" + sjksj + "]";
	}
	public SetUp() {
		super();
	}
	

}
