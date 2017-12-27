package com.cjrj.model;

import java.util.List;

public class SysMenu {

	private Integer menu_id;
	private String menu_name;
	private Integer parentid;
	private String iconCls;
	private String url;
	private Integer memu_seq;
	private Integer menu_enable;
	private Integer childcount;
	private Integer role_id;
	private List<SysMenu>children;
	public List<SysMenu> getChildren() {
		return children;
	}
	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}
	public Integer getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getMemu_seq() {
		return memu_seq;
	}
	public void setMemu_seq(Integer memu_seq) {
		this.memu_seq = memu_seq;
	}
	public Integer getMenu_enable() {
		return menu_enable;
	}
	public void setMenu_enable(Integer menu_enable) {
		this.menu_enable = menu_enable;
	}
	public Integer getChildcount() {
		return childcount;
	}
	public void setChildcount(Integer childcount) {
		this.childcount = childcount;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	
}
