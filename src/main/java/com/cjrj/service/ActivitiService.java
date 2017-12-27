package com.cjrj.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cjrj.model.LeaveBill;

public interface ActivitiService {

	/**
	 * 添加
	 * @param req
	 * @return
	 */
	public int addReq(LeaveBill req);
	
	/**
	 * 查找所有
	 * @return
	 */
	public List<LeaveBill>getReqList(String uname);
	/**
	 * 更新
	 * @param id
	 * @return
	 */
	public int updateReq(Map<String,Object>map);
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public LeaveBill getLeaveBillById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	public List<LeaveBill>getOne();
}
