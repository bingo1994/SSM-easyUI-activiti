package com.cjrj.model;

public class Dept {
	private Integer dept_id;
	private String dept_name;
	private String dept_desc;
	private Integer dept_parent;
	private Integer dept_level;
	private Integer sort_id;
	private Integer page;
	private Integer rows;
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

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_desc() {
		return dept_desc;
	}

	public void setDept_desc(String dept_desc) {
		this.dept_desc = dept_desc;
	}

	public Integer getDept_parent() {
		return dept_parent;
	}

	public void setDept_parent(Integer dept_parent) {
		this.dept_parent = dept_parent;
	}

	public Integer getDept_level() {
		return dept_level;
	}

	public void setDept_level(Integer dept_level) {
		this.dept_level = dept_level;
	}

	public Integer getSort_id() {
		return sort_id;
	}

	public void setSort_id(Integer sort_id) {
		this.sort_id = sort_id;
	}
}
