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
	 * �������и����˵�
	 * @return
	 */
	public List<SysMenu> getPMenu() {
		// TODO Auto-generated method stub
		return roleMapper.getPMenu();
	}

	/**
	 * �������и����˵������Ӳ˵�
	 * @param parentid
	 * @return
	 */
	public List<SysMenu> getCMenu(Integer parentid) {
		// TODO Auto-generated method stub
		return roleMapper.getCMenu(parentid);
	}

	/**
	 * ��֤��ӵĽ�ɫ�Ƿ����
	 * @param name
	 * @return
	 */
	public Role getRoleByName(String name) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByName(name);
	}

	/**
	 * ��ӽ�ɫ
	 * @param role
	 * @return
	 */
	public int addRole(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.addRole(role);
	}

	/**
	 * ͳ������
	 * @return
	 */
	public int getRoleCount() {
		// TODO Auto-generated method stub
		return roleMapper.getRoleCount();
	}

	/**
	 * ���Ҹո���ӵĽ�ɫ
	 * @return
	 */
	public List<Role> getRoleByAdd() {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByAdd();
	}

	/**
	 * ������ӵĽ�ɫ��ӽ�ɫ�˵���
	 * @param map
	 * @return
	 */
	public int addRole_Menu(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roleMapper.addRole_Menu(map);
	}

	/**
	 * ���ݽ�ɫidɾ����ɫ��Ϣ
	 * @param id
	 * @return
	 */
	public int delRoleById(int id) {
		// TODO Auto-generated method stub
		return roleMapper.delRoleById(id);
	}

	/**
	 * ���ݽ�ɫidɾ����ɫ�˵���
	 */
	public int delRoleMenuByRole_id(int id) {
		// TODO Auto-generated method stub
		return roleMapper.delRoleMenuByRole_id(id);
	}

	/**
	 * ���ݽ�ɫidɾ���û���ɫ��
	 * @param id
	 * @return
	 */
	public int delUserRoleByRole_id(int id) {
		// TODO Auto-generated method stub
		return roleMapper.delUserRoleByRole_id(id);
	}

	/**
	 * ���ݽ�ɫid���Ҳ˵�id
	 * @param role_id
	 * @return
	 */
	public List<SysMenu> getMenuByRole_id(int role_id) {
		// TODO Auto-generated method stub
		return roleMapper.getMenuByRole_id(role_id);
	}

	/**
	 * �޸Ľ�ɫ
	 */
	public int updateRole(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.updateRole(role);
	}

	/**
	 * ����û���ɫ��
	 */
	public int addUser_Role(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return roleMapper.addUser_Role(map);
	}

	/**
	 * ���ݽ�ɫid��ѯ����Ӧ�ĸ��˵�
	 * @param id
	 * @return
	 */
	public List<SysMenu> getMenuByRoleId(int id) {
		// TODO Auto-generated method stub
		return roleMapper.getMenuByRoleId(id);
	}

	/**
	 * ���ݽ�ɫid��ѯ��ɫ��Ϣ
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleById(id);
	}

}
