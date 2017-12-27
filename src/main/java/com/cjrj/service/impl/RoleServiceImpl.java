package com.cjrj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjrj.mapper.RoleMapper;
import com.cjrj.model.Role;
import com.cjrj.model.SysMenu;
import com.cjrj.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;
	
	public List<Role> getRoleList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleList(map);
	}

	/**
	 * 查找所有父级菜单
	 * @return
	 */
	public List<SysMenu> getPMenu() {
		// TODO Auto-generated method stub
		return roleMapper.getPMenu();
	}

	/**
	 * 查找所有父级菜单查找子菜单
	 * @param parentid
	 * @return
	 */
	public List<SysMenu> getCMenu(Integer parentid) {
		// TODO Auto-generated method stub
		return roleMapper.getCMenu(parentid);
	}

	/**
	 * 验证添加的角色是否存在
	 * @param name
	 * @return
	 */
	public Role getRoleByName(String name) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByName(name);
	}

	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public int addRole(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.addRole(role);
	}

	/**
	 * 统计数量
	 * @return
	 */
	public int getRoleCount() {
		// TODO Auto-generated method stub
		return roleMapper.getRoleCount();
	}

	/**
	 * 查找刚刚添加的角色
	 * @return
	 */
	public List<Role> getRoleByAdd() {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByAdd();
	}

	/**
	 * 根据添加的角色添加角色菜单表
	 * @param map
	 * @return
	 */
	public int addRole_Menu(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roleMapper.addRole_Menu(map);
	}

	/**
	 * 根据角色id删除角色信息
	 * @param id
	 * @return
	 */
	public int delRoleById(int id) {
		// TODO Auto-generated method stub
		return roleMapper.delRoleById(id);
	}

	/**
	 * 根据角色id删除角色菜单表
	 */
	public int delRoleMenuByRole_id(int id) {
		// TODO Auto-generated method stub
		return roleMapper.delRoleMenuByRole_id(id);
	}

	/**
	 * 根据角色id删除用户角色表
	 * @param id
	 * @return
	 */
	public int delUserRoleByRole_id(int id) {
		// TODO Auto-generated method stub
		return roleMapper.delUserRoleByRole_id(id);
	}

	/**
	 * 根据角色id查找菜单id
	 * @param role_id
	 * @return
	 */
	public List<SysMenu> getMenuByRole_id(int role_id) {
		// TODO Auto-generated method stub
		return roleMapper.getMenuByRole_id(role_id);
	}

	/**
	 * 修改角色
	 */
	public int updateRole(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.updateRole(role);
	}

	/**
	 * 添加用户角色表
	 */
	public int addUser_Role(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roleMapper.addUser_Role(map);
	}

	/**
	 * 根据角色id查询所对应的父菜单
	 * @param id
	 * @return
	 */
	public List<SysMenu> getMenuByRoleId(int id) {
		// TODO Auto-generated method stub
		return roleMapper.getMenuByRoleId(id);
	}

	/**
	 * 根据角色id查询角色信息
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleById(id);
	}

}
