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
	 * ��ѯ����
	 * @param map
	 * @return
	 */
	@Select("select * from(select d.*,rownum rn from mod_dept d)where rn>#{stime} and rn<=#{etime}")
	public List<Dept>getDeptList(Map<String,Object>map);
	
	/**
	 * ͳ�Ʋ���������
	 * @return
	 */
	@Select("select count(*) from mod_dept ")
	public int getDeptCount();
	
	/**
	 * ��Ӳ���
	 * @param dept
	 * @return
	 */
	@InsertProvider(type=DeptSqlProvider.class,method="addDept")
	@SelectKey(statement="select dept_seq.nextval from dual",before=true,resultType=Integer.class,keyProperty="dept_id")
	public int addDept(Dept dept);
	
	/**
	 * �޸Ĳ���
	 * @param dept
	 * @return
	 */
	@UpdateProvider(type=DeptSqlProvider.class,method="updateDept")
	public int updateDept(Dept dept);
	
	/**
	 * ���ݲ���idɾ�����ű���Ϣ
	 * @param dept_id
	 * @return
	 */
	@Delete("delete from mod_dept where dept_id=#{dept_id}")
	public int deleteDept(Integer dept_id);
	
	/**
	 * ���鲿���Ƿ�Ψһ
	 * @param dept
	 * @return
	 */
	@Select("select * from mod_dept where dept_name=#{dept_name}")
	public Dept chkDept(String dept_name);
}
