package com.cjrj.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.cjrj.model.Dept;

public class DeptSqlProvider {

	/**
	 * 添加部门
	 * @param dept
	 * @return
	 */
	public String addDept(final Dept dept){
		return new SQL(){
			{
				INSERT_INTO("mod_dept");
				if(dept.getDept_id()!=null){
					VALUES("dept_id", "#{dept_id}");
				}
				if(dept.getDept_name()!=null){
					VALUES("dept_name", "#{dept_name}");
				}
				if(dept.getDept_desc()!=null){
					VALUES("dept_desc", "#{dept_desc}");
				}
				if(dept.getDept_parent()!=null){
					VALUES("dept_parent", "#{dept_parent}");
				}
				if(dept.getDept_level()!=null){
					VALUES("dept_level", "#{dept_level}");
				}
				if(dept.getSort_id()!=null){
					VALUES("sort_id", "#{sort_id}");
				}
			}
		}.toString();
	}
	
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	public String updateDept(final Dept dept){
		return new SQL(){
			{
				UPDATE("mod_dept");
				if(dept.getDept_name()!=null){
					SET("dept_name=#{dept_name}");
				}
				if(dept.getDept_desc()!=null){
					SET("dept_desc=#{dept_desc}");
				}
				if(dept.getDept_parent()!=null){
					SET("dept_parent=#{dept_parent}");
				}
				if(dept.getDept_level()!=null){
					SET("dept_level=#{dept_level}");
				}
				if(dept.getSort_id()!=null){
					SET("sort_id=#{sort_id}");
				}
				WHERE("dept_id=#{dept_id}");
			}
		}.toString();
	}
}
