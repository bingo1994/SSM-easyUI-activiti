package com.cjrj.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

public class User {
	private Integer []arr;
	private String mon;
	private Integer num;
	private String str;
	private List<String> role_name;
	private String info;
	private boolean flag=false;
	private String outpass;
	private Integer page;
	private Integer rows;
	private Integer role_id;
	private Integer user_id;
	private String user_name;
	private String user_account;//µÇÂ¼ÕËºÅ
	private String pass;
	private String user_desc;
	private String user_address;
	private String user_sex;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regtime;
	private Integer udept_id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date create_date;
	private String strtime;
	private List<Role> role;
	private String dept_name;
	private Integer[] rolename;
	private Integer[] user_ids;
	public Integer[] getArr() {
		return arr;
	}
	public void setArr(Integer[] arr) {
		this.arr = arr;
	}
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public List<String> getRole_name() {
		return role_name;
	}
	public void setRole_name(List<String> role_name) {
		this.role_name = role_name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getOutpass() {
		return outpass;
	}
	public void setOutpass(String outpass) {
		this.outpass = outpass;
	}
	public String getStrtime() {
		return strtime;
	}
	public void setStrtime(String strtime) {
		this.strtime = strtime;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer[] getUser_ids() {
		return user_ids;
	}
	public void setUser_ids(Integer[] user_ids) {
		this.user_ids = user_ids;
	}
	public Integer[] getRolename() {
		return rolename;
	}
	public void setRolename(Integer[] rolename) {
		this.rolename = rolename;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getUser_desc() {
		return user_desc;
	}
	public void setUser_desc(String user_desc) {
		this.user_desc = user_desc;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public Date getRegtime() {
		return regtime;
	}
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	public Integer getUdept_id() {
		return udept_id;
	}
	public void setUdept_id(Integer udept_id) {
		this.udept_id = udept_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public List<Role> getRole() {
		return role;
	}
	public void setRole(List<Role> role) {
		this.role = role;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	
}
