package com.cjrj.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.cjrj.model.Role;
import com.cjrj.model.SysMenu;

public interface RoleService {

	/**
	 * 查询角色表
	 * @param map
	 * @return
	 */
	public List<Role>getRoleList(Map<String,Object>map);
	
	/**
	 * 统计数量
	 * @return
	 */
	public int getRoleCount();
	
	/**
	 * 查找所有父级菜单
	 * @return
	 */
	public List<SysMenu>getPMenu();
	
	/**
	 * 查找所有父级菜单查找子菜单
	 * @param parentid
	 * @return
	 */
	public List<SysMenu>getCMenu(Integer parentid);
	
	/**
	 * 验证添加的角色是否存在
	 * @param name
	 * @return
	 */
	public Role getRoleByName(String name);
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public int addRole(Role role);
	
	/**
	 * 查找刚刚添加的角色
	 * @return
	 */
	public List<Role> getRoleByAdd();
	
	/**
	 * 根据添加的角色添加角色菜单表
	 * @param map
	 * @return
	 */
	public int addRole_Menu(Map<String,Object>map);
	
	/**
	 * 根据角色id删除角色信息
	 * @param id
	 * @return
	 */
	public int delRoleById(int id);
	
	/**
	 * 根据角色id删除角色菜单表
	 * @param id
	 * @return
	 */
	public int delRoleMenuByRole_id(int id);
	
	/**
	 * 根据角色id删除用户角色表
	 * @param id
	 * @return
	 */
	public int delUserRoleByRole_id(int id);
	
	/**
	 * 根据角色id查找菜单id
	 * @param role_id
	 * @return
	 */
	public List<SysMenu>getMenuByRole_id(int role_id);
	
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);
	
	/**
	 * 添加用户角色表
	 * @param map
	 */
	public int addUser_Role(Map<String,Object>map);
	
	/**
	 * 根据角色id查询所对应的父菜单
	 * @param id
	 * @return
	 */
	public List<SysMenu>getMenuByRoleId(int id);
	
	/**
	 * 根据角色id查询角色信息
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id);
}
