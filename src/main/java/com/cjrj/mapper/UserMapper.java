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
	 * 根据用户名查询用户
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

	/**
	 * 根据用户id查找父级菜单
	 * 
	 * @param id
	 * @return
	 */
	public List<SysMenu> findById(int id);

	/**
	 * 根据父级菜单id找子菜单
	 * 
	 * @param pid
	 * @return
	 */
	public List<SysMenu> findByPid(Map<String,Object>map);
	
	/**
	 * 查找所有用户信息
	 * @return
	 */
	public List<User>getUserList(Map<String,Object>map);
	
	/**
	 * 统计用户总数
	 * @return
	 */
	public int getUserCount();
	
	/**
	 * 查找所有部门表信息
	 * @return
	 */
	public List<Dept>getDeptList();
	/**
	 * 查找所有角色表信息
	 * @return
	 */
	public List<Role>getRoleList();
	
	/**
	 * 添加用户表
	 * @param user
	 * @return
	 */
	@SelectKey(statement = "select user_seq.nextval from dual", keyProperty = "user_id", resultType = Integer.class, before = true)
	@InsertProvider(type = UserSqlProvider.class, method = "addUser")
	public int addUser(User user);
	/**
	 * 根据添加的用户信息添加用户角色表
	 * @param map
	 * @return
	 */
	public int addUser_role(Map<String,Object> map);
	/**
	 * 修改用户表
	 * @param user
	 * @return
	 */
	@UpdateProvider(type = UserSqlProvider.class, method = "updateUser")
	public int updateUser(User user);
	
	
	/**
	 * 根据用户id查找信息
	 * @param user_id
	 * @return
	 */
	@Select("select u.*,d.* from mod_user u,mod_dept d where u.udept_id=d.dept_id and u.user_id=#{user_id}")
	public User selUserById(Integer user_id);
	/**
	 * 查找添加的用户的id
	 * @return
	 */
	public List<User> getUserId();
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
	@Select("select s.menu_id from sysmenu s,role_menu rm,mod_role r,mod_user_role ur where s.menu_id=rm.menu_id and rm.role_id=r.role_id and r.role_id=ur.role_id and ur.user_id=#{user_id} group by s.menu_id order by s.menu_id")
	public List<Integer>getMenuByRole_id(int id);
	
	
	/**
	 * 查询所有用户表  部门表
	 * @return
	 */
	public List<User>getUserDeptList();
	
	/**
	 * 根据用户id查询用户表  角色表
	 * @param id
	 * @return
	 */
	public User getUserDeptByUser_id(int id);
	
	/**
	 * 根据用户id查找角色
	 * @param id
	 * @return
	 */
	@Select("select * from mod_role r,mod_user_role ur where r.role_id=ur.role_id and ur.user_id=#{#user_id}")
	public List<Role> getRoleNameByUser_id(int id);
	
	/**
	 * 表格男数据
	 * @return
	 */
	@Select("select to_char(regtime,'yyyy-mm') mon,count(*) "
			+ "num from mod_user where  regtime  between to_date('2017-01','yyyy-mm') "
			+ "and to_date('2017-12','yyyy-mm') and user_sex='男' "
			+ "group by to_char(regtime,'yyyy-mm') order by to_char(regtime,'yyyy-mm')")
	public List<User> getUserCharNan();
	
	/**
	 * 表格女数据
	 * @return
	 */
	@Select("select to_char(regtime,'yyyy-mm') mon,count(*) "
			+ "num from mod_user where  regtime  between to_date('2017-01','yyyy-mm') "
			+ "and to_date('2017-12','yyyy-mm') and user_sex='女' "
			+ "group by to_char(regtime,'yyyy-mm') order by to_char(regtime,'yyyy-mm')")
	public List<User> getUserCharNv();
}
