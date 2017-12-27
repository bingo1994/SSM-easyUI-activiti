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
	 * 根据用户名登录查询
	 */
	public User getUserLoginByname(String user_count) {
		// TODO Auto-generated method stub
		return userMapper.getUserLoginByname(user_count);
	}

	/**
	 * 根据用户的角色id查询菜单栏
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
	 * 查找所有用户信息
	 * 
	 * @return
	 */
	public List<User> getUserList(Map<String,Object>map) {
		// TODO Auto-generated method stub
		return userMapper.getUserList(map);
	}

	/**
	 * 查找所有部门
	 */
	public List<Dept> getDeptList() {
		// TODO Auto-generated method stub
		return userMapper.getDeptList();
	}

	/**
	 * 查找所有角色
	 */
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		return userMapper.getRoleList();
	}

	/**
	 * 添加用户信息
	 */
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.addUser(user);
	}

	/**
	 * 根据添加的用户信息添加用户角色表
	 */
	public int addUser_role(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.addUser_role(map);
	}

	/**
	 * 查找添加的用户的id
	 * 
	 * @return
	 */
	public int getUserId() {
		// TODO Auto-generated method stub
		return userMapper.getUserId().get(0).getUser_id();
	}

	/**
	 * 根据用户id删除用户表信息
	 */
	public int deleteByUser_id(Integer user_id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByUser_id(user_id);
	}

	/**
	 * 根据用户id删除用户角色表新消息
	 */
	public int deleteUser_roleByUid(Integer user_id) {
		// TODO Auto-generated method stub
		return userMapper.deleteUser_roleByUid(user_id);
	}

	/**
	 * 修改用户
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
	 * 根据用户id查找用户角色表的角色id
	 * @param user_id
	 * @return
	 */
	public List<Role> getRoleByUser_id(Integer user_id) {
		// TODO Auto-generated method stub
		return userMapper.getRoleByUser_id(user_id);
	}
	/**
	 * 统计用户总数
	 * @return
	 */
	public int getUserCount() {
		// TODO Auto-generated method stub
		return userMapper.getUserCount();
	}

	/**
	 * 更新用户密码
	 * @param map
	 * @return
	 */
	public int upUserPass(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.upUserPass(map);
	}

	/**
	 * 条件查询用户信息
	 * @param map
	 * @return
	 */
	public List<User> getUserByCon(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.getUserByCon(map);
	}

	/**
	 * 条件统计数量
	 * @param map
	 * @return
	 */
	public int getCountByCon(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.getCountByCon(map);
	}

	
	/**
	 * 根据用户id查找菜单id
	 * @param role_id
	 * @return
	 */
	public List<Integer> getMenuByRole_id(int id) {
		// TODO Auto-generated method stub
		return userMapper.getMenuByRole_id(id);
	}

	/**
	 * 查询所有用户表  部门表
	 * @return
	 */
	public List<User> getUserDeptList() {
		// TODO Auto-generated method stub
		return userMapper.getUserDeptList();
	}

	/**
	 * 根据用户id查找角色
	 * @param id
	 * @return
	 */
	public List<Role> getRoleNameByUser_id(int id) {
		// TODO Auto-generated method stub
		return userMapper.getRoleNameByUser_id(id);
	}

	
	/**
	 * 根据用户id查询用户表  角色表
	 * @param id
	 * @return
	 */
	public User getUserDeptByUser_id(int id) {
		// TODO Auto-generated method stub
		return userMapper.getUserDeptByUser_id(id);
	}

	/**
	 * 表格数据
	 * @return
	 */
	public List<User> getUserCharNan() {
		// TODO Auto-generated method stub
		return userMapper.getUserCharNan();
	}

	/**
	 * 女
	 */
	public List<User> getUserCharNv() {
		// TODO Auto-generated method stub
		return userMapper.getUserCharNv();
	}
}
