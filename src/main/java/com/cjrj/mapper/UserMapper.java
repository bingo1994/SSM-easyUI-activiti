package com.cjrj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.cjrj.model.Dept;
import com.cjrj.model.Role;
import com.cjrj.model.SysMenu;
import com.cjrj.model.User;
import com.cjrj.sqlprovider.UserSqlProvider;

@Repository
public interface UserMapper {

	/**
	 * �����û�����ѯ�û�
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

	/**
	 * �����û�id���Ҹ����˵�
	 * 
	 * @param id
	 * @return
	 */
	public List<SysMenu> findById(int id);

	/**
	 * ���ݸ����˵�id���Ӳ˵�
	 * 
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findByPid(Map<String,Object>map);
	
	/**
	 * ���������û���Ϣ
	 * @return
	 */
	public List<User>getUserList(Map<String,Object>map);
	
	/**
	 * ͳ���û�����
	 * @return
	 */
	public int getUserCount();
	
	/**
	 * �������в��ű���Ϣ
	 * @return
	 */
	public List<Dept>getDeptList();
	/**
	 * �������н�ɫ����Ϣ
	 * @return
	 */
	public List<Role>getRoleList();
	
	/**
	 * ����û���
	 * @param user
	 * @return
	 */
	@SelectKey(statement = "select user_seq.nextval from dual", keyProperty = "user_id", resultType = Integer.class, before = true)
	@InsertProvider(type = UserSqlProvider.class, method = "addUser")
	public int addUser(User user);
	/**
	 * ������ӵ��û���Ϣ����û���ɫ��
	 * @param map
	 * @return
	 */
	public int addUser_role(Map<String,Object> map);
	/**
	 * �޸��û���
	 * @param user
	 * @return
	 */
	@UpdateProvider(type = UserSqlProvider.class, method = "updateUser")
	public int updateUser(User user);
	
	
	/**
	 * �����û�id������Ϣ
	 * @param user_id
	 * @return
	 */
	@Select("select u.*,d.* from mod_user u,mod_dept d where u.udept_id=d.dept_id and u.user_id=#{user_id}")
	public User selUserById(Integer user_id);
	/**
	 * ������ӵ��û���id
	 * @return
	 */
	public List<User> getUserId();
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
	@Select("select s.menu_id from sysmenu s,role_menu rm,mod_role r,mod_user_role ur where s.menu_id=rm.menu_id and rm.role_id=r.role_id and r.role_id=ur.role_id and ur.user_id=#{user_id} group by s.menu_id order by s.menu_id")
	public List<Integer>getMenuByRole_id(int id);
	
	
	/**
	 * ��ѯ�����û���  ���ű�
	 * @return
	 */
	public List<User>getUserDeptList();
	
	/**
	 * �����û�id��ѯ�û���  ��ɫ��
	 * @param id
	 * @return
	 */
	public User getUserDeptByUser_id(int id);
	
	/**
	 * �����û�id���ҽ�ɫ
	 * @param id
	 * @return
	 */
	@Select("select * from mod_role r,mod_user_role ur where r.role_id=ur.role_id and ur.user_id=#{#user_id}")
	public List<Role> getRoleNameByUser_id(int id);
	
	/**
	 * ���������
	 * @return
	 */
	@Select("select to_char(regtime,'yyyy-mm') mon,count(*) "
			+ "num from mod_user where  regtime  between to_date('2017-01','yyyy-mm') "
			+ "and to_date('2017-12','yyyy-mm') and user_sex='��' "
			+ "group by to_char(regtime,'yyyy-mm') order by to_char(regtime,'yyyy-mm')")
	public List<User> getUserCharNan();
	
	/**
	 * ���Ů����
	 * @return
	 */
	@Select("select to_char(regtime,'yyyy-mm') mon,count(*) "
			+ "num from mod_user where  regtime  between to_date('2017-01','yyyy-mm') "
			+ "and to_date('2017-12','yyyy-mm') and user_sex='Ů' "
			+ "group by to_char(regtime,'yyyy-mm') order by to_char(regtime,'yyyy-mm')")
	public List<User> getUserCharNv();
}
