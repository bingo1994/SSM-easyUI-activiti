package com.cjrj.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.cjrj.model.Dept;

public interface DeptService {

	/**
	 * ��ѯ����
	 * @param map
	 * @return
	 */
	public List<Dept>getDeptList(Map<String,Object>map);
	
	/**
	 * ͳ�Ʋ���������
	 * @return
	 */
	public int getDeptCount();
	
	/**
	 * ��Ӳ���
	 * @param dept
	 * @return
	 */
	public int addDept(Dept dept);
	
	/**
	 * �޸Ĳ���
	 * @param dept
	 * @return
	 */
	public int updateDept(Dept dept);
	
	/**
	 * ���ݲ���idɾ�����ű���Ϣ
	 * @param dept_id
	 * @return
	 */
	public int deleteDept(Integer dept_id);
	
	/**
	 * ���鲿�������Ƿ�Ψһ
	 * @param dept_name
	 * @return
	 */
	public Dept chkDept(String dept_name);
}
