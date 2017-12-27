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
	 * ��ѯ����
	 * @param map
	 * @return
	 */
	public List<Dept> getDeptList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deptMapper.getDeptList(map);
	}

	/**
	 * ��Ӳ���
	 * @param dept
	 * @return
	 */
	public int addDept(Dept dept) {
		// TODO Auto-generated method stub
		return deptMapper.addDept(dept);
	}

	/**
	 * �޸Ĳ���
	 * @param dept
	 * @return
	 */
	public int updateDept(Dept dept) {
		// TODO Auto-generated method stub
		return deptMapper.updateDept(dept);
	}

	/**
	 * ���ݲ���idɾ�����ű���Ϣ
	 * @param dept_id
	 * @return
	 */
	public int deleteDept(Integer dept_id) {
		// TODO Auto-generated method stub
		return deptMapper.deleteDept(dept_id);
	}

	/**
	 * ���鲿�������Ƿ�Ψһ
	 * @param dept_name
	 * @return
	 */
	public Dept chkDept(String dept_name) {
		// TODO Auto-generated method stub
		return deptMapper.chkDept(dept_name);
	}

	/**
	 * ͳ�Ʋ���������
	 * @return
	 */
	public int getDeptCount() {
		// TODO Auto-generated method stub
		return deptMapper.getDeptCount();
	}

}
