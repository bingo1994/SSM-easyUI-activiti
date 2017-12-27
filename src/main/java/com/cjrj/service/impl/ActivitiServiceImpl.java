package com.cjrj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjrj.mapper.ActivitiMapper;
import com.cjrj.model.LeaveBill;
import com.cjrj.service.ActivitiService;

@Service
public class ActivitiServiceImpl implements ActivitiService {

	@Autowired
	ActivitiMapper activitiMapper;
	/**
	 * 添加
	 * @param req
	 * @return
	 */
	public int addReq(LeaveBill req) {
		// TODO Auto-generated method stub
		return activitiMapper.addReq(req);
	}
	
	/**
	 * 查找所有
	 * @return
	 */
	public List<LeaveBill> getReqList(String uname) {
		// TODO Auto-generated method stub
		return activitiMapper.getReqList(uname);
	}

	/**
	 * 更新
	 * @param id
	 * @return
	 */
	public int updateReq(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return activitiMapper.updateReq(map);
	}

	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	public LeaveBill getLeaveBillById(int id) {
		// TODO Auto-generated method stub
		return activitiMapper.getLeaveBillById(id);
	}

	public List<LeaveBill> getOne() {
		// TODO Auto-generated method stub
		return activitiMapper.getOne();
	}

}
