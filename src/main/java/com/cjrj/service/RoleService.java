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
	 * ��ѯ��ɫ��
	 * @param map
	 * @return
	 */
	public List<Role>getRoleList(Map<String,Object>map);
	
	/**
	 * ͳ������
	 * @return
	 */
	public int getRoleCount();
	
	/**
	 * �������и����˵�
	 * @return
	 */
	public List<SysMenu>getPMenu();
	
	/**
	 * �������и����˵������Ӳ˵�
	 * @param parentid
	 * @return
	 */
	public List<SysMenu>getCMenu(Integer parentid);
	
	/**
	 * ��֤��ӵĽ�ɫ�Ƿ����
	 * @param name
	 * @return
	 */
	public Role getRoleByName(String name);
	
	/**
	 * ��ӽ�ɫ
	 * @param role
	 * @return
	 */
	public int addRole(Role role);
	
	/**
	 * ���Ҹո���ӵĽ�ɫ
	 * @return
	 */
	public List<Role> getRoleByAdd();
	
	/**
	 * ������ӵĽ�ɫ��ӽ�ɫ�˵���
	 * @param map
	 * @return
	 */
	public int addRole_Menu(Map<String,Object>map);
	
	/**
	 * ���ݽ�ɫidɾ����ɫ��Ϣ
	 * @param id
	 * @return
	 */
	public int delRoleById(int id);
	
	/**
	 * ���ݽ�ɫidɾ����ɫ�˵���
	 * @param id
	 * @return
	 */
	public int delRoleMenuByRole_id(int id);
	
	/**
	 * ���ݽ�ɫidɾ���û���ɫ��
	 * @param id
	 * @return
	 */
	public int delUserRoleByRole_id(int id);
	
	/**
	 * ���ݽ�ɫid���Ҳ˵�id
	 * @param role_id
	 * @return
	 */
	public List<SysMenu>getMenuByRole_id(int role_id);
	
	
	/**
	 * �޸Ľ�ɫ
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);
	
	/**
	 * ����û���ɫ��
	 * @param map
	 */
	public int addUser_Role(Map<String,Object>map);
	
	/**
	 * ���ݽ�ɫid��ѯ����Ӧ�ĸ��˵�
	 * @param id
	 * @return
	 */
	public List<SysMenu>getMenuByRoleId(int id);
	
	/**
	 * ���ݽ�ɫid��ѯ��ɫ��Ϣ
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id);
}
