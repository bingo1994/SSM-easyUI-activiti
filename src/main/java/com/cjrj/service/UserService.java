package com.cjrj.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.cjrj.model.Dept;
import com.cjrj.model.Role;
import com.cjrj.model.SysMenu;
import com.cjrj.model.User;

public interface UserService {
	/**
	 * 根据用户名登录查询
	 * 
	 * @return
	 */
	public User getUserLoginByname(String user_account);

	/**
	 * 根据用户的角色id查询菜单栏
	 * 
	 * @param id
	 * @return
	 */
	public List<SysMenu> getMenuByURoleId(int id);

	public List<SysMenu> findByPid(Map<String,Object>map);

	public List<SysMenu> findById(int id);

	/**
	 * 查找所有用户信息
	 * 
	 * @return
	 */
	public List<User> getUserList(Map<String,Object>map);

	/**
	 * 统计用户总数
	 * @return
	 */
	public int getUserCount();
	/**
	 * 查找所有部门表信息
	 * 
	 * @return
	 */
	public List<Dept> getDeptList();

	/**
	 * 查找所有角色表信息
	 * 
	 * @return
	 */
	public List<Role> getRoleList();
	/**
	 * 添加用户的信息
	 * @param user
	 * @return
	 */
	public int addUser(User user);
	
	/**
	 * 根据添加的用户信息添加用户角色表
	 * @param map
	 * @return
	 */
	public int addUser_role(Map<String,Object> map);
	/**
	 * 查找添加的用户的id
	 * @return
	 */
	public int getUserId();
	/**
	 * 根据用户id删除用户表信息
	 * @return
	 */
	public int deleteByUser_id(Integer user_id);
	/**
	 * 根据用户id删除用户角色表信息
	 * @return
	 */
	public int deleteUser_roleByUid(Integer user_id);

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(User user);
	public User selUserById(Integer user_id);
	
	/**
	 * 根据用户id查找用户角色表的角色id
	 * @param user_id
	 * @return
	 */
	public List<Role> getRoleByUser_id(Integer user_id);
	
	/**
	 * 更新用户密码
	 * @param map
	 * @return
	 */
	public int upUserPass(Map<String,Object>map);
	
	/**
	 * 条件查询用户信息
	 * @param map
	 * @return
	 */
	public List<User>getUserByCon(Map<String,Object>map);
	
	/**
	 * 条件统计数量
	 * @param map
	 * @return
	 */
	public int getCountByCon(Map<String,Object>map);
	
	/**
	 * 根据用户id查找菜单id
	 * @param role_id
	 * @return
	 */
	public List<Integer>getMenuByRole_id(int id);
	
	/**
	 * 查询所有用户表  部门表
	 * @return
	 */
	public List<User>getUserDeptList();
	
	/**
	 * 根据用户id查找角色
	 * @param id
	 * @return
	 */
	public List<Role> getRoleNameByUser_id(int id);
	
	/**
	 * 根据用户id查询用户表  角色表
	 * @param id
	 * @return
	 */
	public User getUserDeptByUser_id(int id);
	
	/**
	 * 表格男数据
	 * @return
	 */
	public List<User> getUserCharNan();
	
	/**
	 * 表格女数据
	 * @return
	 */
	public List<User> getUserCharNv();
}
