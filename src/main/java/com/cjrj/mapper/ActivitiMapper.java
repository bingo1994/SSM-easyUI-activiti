package com.cjrj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.cjrj.model.LeaveBill;

@Repository
public interface ActivitiMapper {

	/**
	 * 添加请求
	 * @param req
	 * @return
	 */
	@Insert("insert into mod_req values(#{req_id},to_date(#{stime},'yyyy-mm-dd hh24:mi:ss'),to_date(#{etime},'yyyy-mm-dd hh24:mi:ss'),#{reasons},#{types},#{otheradd},#{status},#{uname})")
	@SelectKey(statement="select req_seq.nextval from dual",before=true,keyProperty="req_id",resultType=Integer.class)
	public int addReq(LeaveBill req);
	
	/**
	 * 查询
	 * @return
	 */
	@Select("select uname,otheradd,status,req_id,to_char(stime,'yyyy-mm-dd  hh24:mi:ss')as stime,to_char(etime,'yyyy-mm-dd  hh24:mi:ss')as etime,types,reasons from mod_req order by req_id desc")
	public List<LeaveBill>getOne();
	
	/**
	 * 查找所有
	 * @return
	 */
	@Select("select uname,otheradd,status,req_id,to_char(stime,'yyyy-mm-dd  hh24:mi:ss')as stime,to_char(etime,'yyyy-mm-dd  hh24:mi:ss')as etime,types,reasons from mod_req where uname=#{uname}")
	public List<LeaveBill>getReqList(String uname);
	
	/**
	 * 更新
	 * @param id
	 * @return
	 */
	@Update("update mod_req set status=#{status} where req_id=#{id}")
	public int updateReq(Map<String,Object>map);
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
	@Select("select uname,otheradd,status,req_id,to_char(stime,'yyyy-mm-dd  hh24:mi:ss')as stime,to_char(etime,'yyyy-mm-dd  hh24:mi:ss')as etime,types,reasons from mod_req where req_id=#{req_id}")
	public LeaveBill getLeaveBillById(int id);
}
