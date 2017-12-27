package com.cjrj.model;

import java.util.List;

public class Role {

	private Integer role_id;
	private String role_name;
	private String role_desc;
	private Integer page;
	private Integer rows;
	private Integer[] menu_ids;
	private List<String> menuName;
	public List<String> getMenuName() {
		return menuName;
	}
	public void setMenuName(List<String> menuName) {
		this.menuName = menuName;
	}
	public Integer[] getMenu_ids() {
		return menu_ids;
	}
	public void setMenu_ids(Integer[] menu_ids) {
		this.menu_ids = menu_ids;
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
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_desc() {
		return role_desc;
	}
	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	} 
}
