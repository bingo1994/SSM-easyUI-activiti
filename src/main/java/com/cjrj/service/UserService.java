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
	 * �����û�����¼��ѯ
	 * 
	 * @return
	 */
	public User getUserLoginByname(String user_account);

	/**
	 * �����û��Ľ�ɫid��ѯ�˵���
	 * 
	 * @param id
	 * @return
	 */
	public List<SysMenu> getMenuByURoleId(int id);

	public List<SysMenu> findByPid(Map<String,Object>map);

	public List<SysMenu> findById(int id);

	/**
	 * ���������û���Ϣ
	 * 
	 * @return
	 */
	public List<User> getUserList(Map<String,Object>map);

	/**
	 * ͳ���û�����
	 * @return
	 */
	public int getUserCount();
	/**
	 * �������в��ű���Ϣ
	 * 
	 * @return
	 */
	public List<Dept> getDeptList();

	/**
	 * �������н�ɫ����Ϣ
	 * 
	 * @return
	 */
	public List<Role> getRoleList();
	/**
	 * ����û�����Ϣ
	 * @param user
	 * @return
	 */
	public int addUser(User user);
	
	/**
	 * ������ӵ��û���Ϣ����û���ɫ��
	 * @param map
	 * @return
	 */
	public int addUser_role(Map<String,Object> map);
	/**
	 * ������ӵ��û���id
	 * @return
	 */
	public int getUserId();
	/**
	 * �����û�idɾ���û�����Ϣ
	 * @return
	 */
	public int deleteByUser_id(Integer user_id);
	/**
	 * �����û�idɾ���û���ɫ����Ϣ
	 * @return
	 */
	public int deleteUser_roleByUid(Integer user_id);

	/**
	 * �޸��û�
	 * @param user
	 * @return
	 */
	public int updateUser(User user);
	public User selUserById(Integer user_id);
	
	/**
	 * �����û�id�����û���ɫ��Ľ�ɫid
	 * @param user_id
	 * @return
	 */
	public List<Role> getRoleByUser_id(Integer user_id);
	
	/**
	 * �����û�����
	 * @param map
	 * @return
	 */
	public int upUserPass(Map<String,Object>map);
	
	/**
	 * ������ѯ�û���Ϣ
	 * @param map
	 * @return
	 */
	public List<User>getUserByCon(Map<String,Object>map);
	
	/**
	 * ����ͳ������
	 * @param map
	 * @return
	 */
	public int getCountByCon(Map<String,Object>map);
	
	/**
	 * �����û�id���Ҳ˵�id
	 * @param role_id
	 * @return
	 */
	public List<Integer>getMenuByRole_id(int id);
	
	/**
	 * ��ѯ�����û���  ���ű�
	 * @return
	 */
	public List<User>getUserDeptList();
	
	/**
	 * �����û�id���ҽ�ɫ
	 * @param id
	 * @return
	 */
	public List<Role> getRoleNameByUser_id(int id);
	
	/**
	 * �����û�id��ѯ�û���  ��ɫ��
	 * @param id
	 * @return
	 */
	public User getUserDeptByUser_id(int id);
	
	/**
	 * ���������
	 * @return
	 */
	public List<User> getUserCharNan();
	
	/**
	 * ���Ů����
	 * @return
	 */
	public List<User> getUserCharNv();
}
