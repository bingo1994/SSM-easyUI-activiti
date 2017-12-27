package com.cjrj.model;

public class LeaveBill {

	private Integer req_id;
	private String types;
	private String stime;
	private String etime;
	private String reasons;
	private String status;
	private String otheradd;
	private String uname;
	private String taskId;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getOtheradd() {
		return otheradd;
	}
	public void setOtheradd(String otheradd) {
		this.otheradd = otheradd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getReq_id() {
		return req_id;
	}
	public void setReq_id(Integer req_id) {
		this.req_id = req_id;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
}
