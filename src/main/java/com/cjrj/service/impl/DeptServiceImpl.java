package com.cjrj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjrj.mapper.DeptMapper;
import com.cjrj.model.Dept;
import com.cjrj.service.DeptService;
@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	DeptMapper deptMapper;
	
	/**
	 * 查询部门
	 * @param map
	 * @return
	 */
	public List<Dept> getDeptList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deptMapper.getDeptList(map);
	}

	/**
	 * 添加部门
	 * @param dept
	 * @return
	 */
	public int addDept(Dept dept) {
		// TODO Auto-generated method stub
		return deptMapper.addDept(dept);
	}

	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	public int updateDept(Dept dept) {
		// TODO Auto-generated method stub
		return deptMapper.updateDept(dept);
	}

	/**
	 * 根据部门id删除部门表信息
	 * @param dept_id
	 * @return
	 */
	public int deleteDept(Integer dept_id) {
		// TODO Auto-generated method stub
		return deptMapper.deleteDept(dept_id);
	}

	/**
	 * 检验部门名称是否唯一
	 * @param dept_name
	 * @return
	 */
	public Dept chkDept(String dept_name) {
		// TODO Auto-generated method stub
		return deptMapper.chkDept(dept_name);
	}

	/**
	 * 统计部门总数量
	 * @return
	 */
	public int getDeptCount() {
		// TODO Auto-generated method stub
		return deptMapper.getDeptCount();
	}

}
