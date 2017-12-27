package com.cjrj.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cjrj.model.LeaveBill;

public interface ActivitiService {

	/**
	 * ���
	 * @param req
	 * @return
	 */
	public int addReq(LeaveBill req);
	
	/**
	 * ��������
	 * @return
	 */
	public List<LeaveBill>getReqList(String uname);
	/**
	 * ����
	 * @param id
	 * @return
	 */
	public int updateReq(Map<String,Object>map);
	
	/**
	 * ����id����
	 * @param id
	 * @return
	 */
	public LeaveBill getLeaveBillById(int id);
	
	/**
	 * ��ѯ
	 * @return
	 */
	public List<LeaveBill>getOne();
}
