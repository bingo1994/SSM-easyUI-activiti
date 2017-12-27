package com.cjrj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.cjrj.model.Role;
import com.cjrj.model.SysMenu;

@Repository
public interface RoleMapper {

	/**
	 * 查询角色表
	 * @param map
	 * @return
	 */
	@Select("select * from(select r.*,rownum rn from mod_role r order by r.role_id desc)where rn>#{stime} and rn<=#{etime} ")
	public List<Role>getRoleList(Map<String,Object>map);
	
	/**
	 * 统计数量
	 * @return
	 */
	@Select("select count(*)  from mod_role  ")
	public int getRoleCount();
	
	/**
	 * 查找所有父级菜单
	 * @return
	 */
	@Select("select t.* from sysmenu t where t.parentid=0")
	public List<SysMenu>getPMenu();
	
	/**
	 * 查找所有父级菜单查找子菜单
	 * @param parentid
	 * @return
	 */
	@Select("select t.* from sysmenu t where t.parentid=#{parentid}")
	public List<SysMenu>getCMenu(Integer parentid);
	
	/**
	 * 验证添加的角色是否存在
	 * @param name
	 * @return
	 */
	@Select("select * from mod_role where role_name=#{role_name}")
	public Role getRoleByName(String name);
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	@Insert("insert into mod_role values(#{role_id},#{role_name},#{role_desc})")
	@SelectKey(statement="select role_seq.nextval from dual",before=true,resultType=Integer.class,keyProperty="role_id")
	public int addRole(Role role);
	
	/**
	 * 查找刚刚添加的角色
	 * @return
	 */
	@Select("select * from mod_role order by role_id desc")
	public List<Role> getRoleByAdd();
	
	/**
	 * 根据添加的角色添加角色菜单表
	 * @param map
	 * @return
	 */
	@Insert("insert into role_menu values(#{id},#{role_id},#{menu_id})")
	@SelectKey(statement="select role_menu_seq.nextval from dual",before=true,keyProperty="id",resultType=Integer.class)
	public int addRole_Menu(Map<String,Object>map);
	
	/**
	 * 根据角色id删除角色信息
	 * @param id
	 * @return
	 */
	@Delete("delete from mod_role where role_id=#{role_id}")
	public int delRoleById(int id);
	
	/**
	 * 根据角色id删除角色菜单表
	 * @param id
	 * @return
	 */
	@Delete("delete from role_menu where role_id=#{role_id}")
	public int delRoleMenuByRole_id(int id);
	
	/**
	 * 根据角色id删除用户角色表
	 * @param id
	 * @return
	 */
	@Delete("delete from mod_user_role where role_id=#{role_id}")
	public int delUserRoleByRole_id(int id);
	
	/**
	 * 根据角色id查找菜单id
	 * @param role_id
	 * @return
	 */
	@Select("select s.* from role_menu m,sysmenu s where s.menu_id=m.menu_id and m.role_id=#{role_id}")
	public List<SysMenu>getMenuByRole_id(int role_id);
	
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@Update("update mod_role set role_name=#{role_name},role_desc=#{role_desc} where role_id=#{role_id}")
	public int updateRole(Role role);
	
	/**
	 * 添加用户角色表
	 * @param map
	 */
	@Insert("insert into mod_user_role values(#{id},#{user_id},#{role_id})")
	@SelectKey(statement="select user_role.nextval from dual",before=true,keyProperty="id",resultType=Integer.class)
	public int addUser_Role(Map<String,Object>map);
	
	/**
	 * 根据角色id查询所对应的父菜单
	 * @param id
	 * @return
	 */
	@Select("select s.* from role_menu m,sysmenu s where s.menu_id = m.menu_id and m.role_id=#{role_id} and s.parentid=0")
	public List<SysMenu>getMenuByRoleId(int id);
	
	/**
	 * 根据角色id查询角色信息
	 * @param id
	 * @return
	 */
	@Select("select * from mod_role where role_id=#{role_id}")
	public Role getRoleById(int id);
}
