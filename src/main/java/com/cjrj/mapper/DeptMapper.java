package com.cjrj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.cjrj.model.Dept;
import com.cjrj.sqlprovider.DeptSqlProvider;

@Repository
public interface DeptMapper {

	/**
	 * 查询部门
	 * @param map
	 * @return
	 */
	@Select("select * from(select d.*,rownum rn from mod_dept d)where rn>#{stime} and rn<=#{etime}")
	public List<Dept>getDeptList(Map<String,Object>map);
	
	/**
	 * 统计部门总数量
	 * @return
	 */
	@Select("select count(*) from mod_dept ")
	public int getDeptCount();
	
	/**
	 * 添加部门
	 * @param dept
	 * @return
	 */
	@InsertProvider(type=DeptSqlProvider.class,method="addDept")
	@SelectKey(statement="select dept_seq.nextval from dual",before=true,resultType=Integer.class,keyProperty="dept_id")
	public int addDept(Dept dept);
	
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	@UpdateProvider(type=DeptSqlProvider.class,method="updateDept")
	public int updateDept(Dept dept);
	
	/**
	 * 根据部门id删除部门表信息
	 * @param dept_id
	 * @return
	 */
	@Delete("delete from mod_dept where dept_id=#{dept_id}")
	public int deleteDept(Integer dept_id);
	
	/**
	 * 检验部门是否唯一
	 * @param dept
	 * @return
	 */
	@Select("select * from mod_dept where dept_name=#{dept_name}")
	public Dept chkDept(String dept_name);
}
