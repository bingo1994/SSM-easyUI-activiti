package com.cjrj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjrj.model.Dept;
import com.cjrj.service.DeptService;

@Controller
public class DeptController {

	@Autowired
	DeptService deptService;
	
	/**
	 * 查询部门
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getdeptlist.do")
	public Map<String,Object> getDeptList(@RequestParam(value="page",defaultValue="1",required=false)Integer page,Integer rows){
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("stime", (page-1)*rows);
		map.put("etime", page*rows);
		List<Dept>deptlist=deptService.getDeptList(map);
		int count=deptService.getDeptCount();
		Map<String,Object>maps=new HashMap<String,Object>();
		maps.put("rows", deptlist);
		maps.put("total", count);
		return maps;
	}
	
	/**
	 * 添加部门
	 * @param dept
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/adddept.do")
	public int addDept(Dept dept){
		int i=deptService.addDept(dept);
		return i;
	}
	
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatedept.do")
	public int updateDept(Dept dept){
		int i=deptService.updateDept(dept);
		return i;
	}
	/**
	 * 根据部门id删除部门
	 * @param dept_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deldept.do")
	public int delDeptById(Integer dept_id){
		int i=deptService.deleteDept(dept_id);
		return i;
	}
	
	/**
	 * 检验部门名称是否唯一
	 * @param dept_name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/chkdept.do")
	public boolean chkDept(String dept_name){
		boolean flag=true;
		Dept dept=deptService.chkDept(dept_name);
		if(dept!=null){
			flag=false;
		}
		return flag;
	}
}
