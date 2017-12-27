package com.cjrj.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.cjrj.model.Dept;

public interface DeptService {

	/**
	 * 查询部门
	 * @param map
	 * @return
	 */
	public List<Dept>getDeptList(Map<String,Object>map);
	
	/**
	 * 统计部门总数量
	 * @return
	 */
	public int getDeptCount();
	
	/**
	 * 添加部门
	 * @param dept
	 * @return
	 */
	public int addDept(Dept dept);
	
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	public int updateDept(Dept dept);
	
	/**
	 * 根据部门id删除部门表信息
	 * @param dept_id
	 * @return
	 */
	public int deleteDept(Integer dept_id);
	
	/**
	 * 检验部门名称是否唯一
	 * @param dept_name
	 * @return
	 */
	public Dept chkDept(String dept_name);
}
