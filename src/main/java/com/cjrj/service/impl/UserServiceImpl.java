package com.cjrj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjrj.mapper.UserMapper;
import com.cjrj.model.Dept;
import com.cjrj.model.Role;
import com.cjrj.model.SysMenu;
import com.cjrj.model.Tree;
import com.cjrj.model.User;
import com.cjrj.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	/**
	 * �����û�����¼��ѯ
	 */
	public User getUserLoginByname(String user_count) {
		// TODO Auto-generated method stub
		return userMapper.getUserLoginByname(user_count);
	}

	/**
	 * �����û��Ľ�ɫid��ѯ�˵���
	 * 
	 * @param id
	 * @return
	 */
	public List<SysMenu> getMenuByURoleId(int id) {
		return userMapper.getMenuByURoleId(id);
	}

	public List<SysMenu> findByPid(Map<String,Object>map) {
		// TODO Auto-generated method stub
		return userMapper.findByPid(map);
	}

	public List<SysMenu> findById(int id) {
		// TODO Auto-generated method stub
		return userMapper.findById(id);
	}

	/**
	 * ���������û���Ϣ
	 * 
	 * @return
	 */
	public List<User> getUserList(Map<String,Object>map) {
		// TODO Auto-generated method stub
		return userMapper.getUserList(map);
	}

	/**
	 * �������в���
	 */
	public List<Dept> getDeptList() {
		// TODO Auto-generated method stub
		return userMapper.getDeptList();
	}

	/**
	 * �������н�ɫ
	 */
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		return userMapper.getRoleList();
	}

	/**
	 * ����û���Ϣ
	 */
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.addUser(user);
	}

	/**
	 * ������ӵ��û���Ϣ����û���ɫ��
	 */
	public int addUser_role(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.addUser_role(map);
	}

	/**
	 * ������ӵ��û���id
	 * 
	 * @return
	 */
	public int getUserId() {
		// TODO Auto-generated method stub
		return userMapper.getUserId().get(0).getUser_id();
	}

	/**
	 * �����û�idɾ���û�����Ϣ
	 */
	public int deleteByUser_id(Integer user_id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByUser_id(user_id);
	}

	/**
	 * �����û�idɾ���û���ɫ������Ϣ
	 */
	public int deleteUser_roleByUid(Integer user_id) {
		// TODO Auto-generated method stub
		return userMapper.deleteUser_roleByUid(user_id);
	}

	/**
	 * �޸��û�
	 */
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(user);
	}

	public User selUserById(Integer user_id) {
		// TODO Auto-generated method stub
		return userMapper.selUserById(user_id);
	}

	/**
	 * �����û�id�����û���ɫ��Ľ�ɫid
	 * @param user_id
	 * @return
	 */
	public List<Role> getRoleByUser_id(Integer user_id) {
		// TODO Auto-generated method stub
		return userMapper.getRoleByUser_id(user_id);
	}
	/**
	 * ͳ���û�����
	 * @return
	 */
	public int getUserCount() {
		// TODO Auto-generated method stub
		return userMapper.getUserCount();
	}

	/**
	 * �����û�����
	 * @param map
	 * @return
	 */
	public int upUserPass(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.upUserPass(map);
	}

	/**
	 * ������ѯ�û���Ϣ
	 * @param map
	 * @return
	 */
	public List<User> getUserByCon(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.getUserByCon(map);
	}

	/**
	 * ����ͳ������
	 * @param map
	 * @return
	 */
	public int getCountByCon(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.getCountByCon(map);
	}

	
	/**
	 * �����û�id���Ҳ˵�id
	 * @param role_id
	 * @return
	 */
	public List<Integer> getMenuByRole_id(int id) {
		// TODO Auto-generated method stub
		return userMapper.getMenuByRole_id(id);
	}

	/**
	 * ��ѯ�����û���  ���ű�
	 * @return
	 */
	public List<User> getUserDeptList() {
		// TODO Auto-generated method stub
		return userMapper.getUserDeptList();
	}

	/**
	 * �����û�id���ҽ�ɫ
	 * @param id
	 * @return
	 */
	public List<Role> getRoleNameByUser_id(int id) {
		// TODO Auto-generated method stub
		return userMapper.getRoleNameByUser_id(id);
	}

	
	/**
	 * �����û�id��ѯ�û���  ��ɫ��
	 * @param id
	 * @return
	 */
	public User getUserDeptByUser_id(int id) {
		// TODO Auto-generated method stub
		return userMapper.getUserDeptByUser_id(id);
	}

	/**
	 * �������
	 * @return
	 */
	public List<User> getUserCharNan() {
		// TODO Auto-generated method stub
		return userMapper.getUserCharNan();
	}

	/**
	 * Ů
	 */
	public List<User> getUserCharNv() {
		// TODO Auto-generated method stub
		return userMapper.getUserCharNv();
	}
}
