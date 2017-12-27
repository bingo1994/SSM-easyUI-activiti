package com.cjrj.controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cjrj.mapper.DefinitionBean;
import com.cjrj.model.LeaveBill;
import com.cjrj.model.PersonTask;
import com.cjrj.service.ActivitiService;

@Controller
public class ActivitiController {

	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	ActivitiService activitiService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	TaskService taskService;
	/**
	 * 我的请求
	 * @return
	 */
	@RequestMapping("/gomyreq.do")
	public String gotoReq(){
		return "myrequest";
	}
	
	/**
	 * 跳转到待办事宜
	 * @return
	 */
	@RequestMapping("/gopersontask.do")
	public String gotoPersonTask(){
		return "persontask";
	}
	
	
	@RequestMapping("/image.do")
	public String gotoImage(){
		return "showimage";
	}
	
	@RequestMapping("/getImage.do")
	public void viewProcessImageView(@RequestParam("taskId")String taskId,HttpServletRequest request, HttpServletResponse response){
		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId=task.getProcessInstanceId();
		InputStream resourceAsStream = null;
		try {
			//根据流程实例id查询流程实例
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			//根据流程定义id查询流程定义
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
			String resourceName=processDefinition.getDiagramResourceName();
			//打开流程资源流
			resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
			runtimeService.getActiveActivityIds(processInstance.getId());
			//输出到浏览器
			byte[] byteArray = IOUtils.toByteArray(resourceAsStream);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(byteArray, 0, byteArray.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	 
	 
	/**
	 * 添加流程
	 * @param file
	 * @param req
	 */
	@RequestMapping("/addact.do")
	public String addAct(@RequestParam("filename")String filename,@RequestParam("file")MultipartFile file,HttpServletRequest req ){
		ZipInputStream zip;
		//从classpath路径下读取资源文件  
	  // InputStream in = this.getClass().getClassLoader().getResourceAsStream("Myprocess.zip");  
		try {
			InputStream in =file.getInputStream();
			zip = new ZipInputStream(in);
			repositoryService.createDeployment()
			.name(filename)
			.addZipInputStream(zip)
			.deploy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "activiti";
	}
	/**
	 * 查询流程
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getactinfo.do")
	public List<DefinitionBean>getactinfo(){
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ProcessDefinition> list=repositoryService.createProcessDefinitionQuery().list();
		 List<DefinitionBean> beans = new ArrayList<DefinitionBean>();
		 if(list!=null){
			 for (ProcessDefinition processDefinition : list) {
                 DefinitionBean definitionBean = new DefinitionBean();
                 Deployment singleResult = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();
                 definitionBean.setId(processDefinition.getId());
                 definitionBean.setKey(processDefinition.getKey());
                 definitionBean.setPro_defi_name(processDefinition.getName());
                 definitionBean.setPro_devl_name(singleResult.getName());
                 definitionBean.setPro_devl_time(s.format(singleResult.getDeploymentTime()));
                 definitionBean.setVersion(processDefinition.getVersion()+"");
                 definitionBean.setDeployment_id(processDefinition.getDeploymentId());
                 definitionBean.setResourcename(processDefinition.getDiagramResourceName());
                 beans.add(definitionBean);
            }
		 }
		return beans;
	}
	
	/**
	 * 查看流程图
	 * @param id
	 * @return
	 */
	@RequestMapping("/getActPic.do")
	public void getActPic(@RequestParam("id")String id,@RequestParam("name")String name,HttpServletResponse rep){
		InputStream in=repositoryService.getResourceAsStream(id,name);
		if(in!=null){
			byte[] bt = new byte[1024];
			int len=-1;
			try {
				OutputStream out= rep.getOutputStream();
				while((len=(in.read(bt))) != -1) {
					out.write(bt,0,len); 
					}
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除流程
	 * @param deployment_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delact.do")
	public int delActById(@RequestParam("deployment_id")String deployment_id){
		repositoryService.deleteDeployment(deployment_id, true);
		return 1;
	}
	/**
	 * 添加请假请求
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addreq.do")
	public int addReq(LeaveBill req){
		req.setStatus("初始录入");
		int i=activitiService.addReq(req);
		List<LeaveBill> lblist=activitiService.getOne();
		
		Map<String, Object> variables = new HashMap<String,Object>();
		String objId=lblist.get(0).getClass().getSimpleName()+"."+lblist.get(0).getReq_id();
		variables.put("objId", objId);
		variables.put("user", req.getUname());
		ProcessInstance	 pi=runtimeService.startProcessInstanceByKey("leavebill",objId,variables);//启动
		return i;
	}
	
	/**
	 * 提交我的请求（普通员工）
	 * @param id  员工请假id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/subreq.do")
	public boolean submitReq(@RequestParam("taskId")String taskId, @RequestParam("id")int id,@RequestParam("uname")String uname){
		boolean flag=false;
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", "审核中");
		int i=activitiService.updateReq(map);
		
		//测试排他官网
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("time", "4");
		//Map<String, Object> variables = new HashMap<String,Object>();
		//variables.put("inputUser", "aa");
		//格式：LeaveBill.id的形式（使用流程变量）
		if(i>0){
			flag=true;
			taskService.complete(taskId);
			//taskService.complete(taskId,variables);
		}
		return flag;
	}
	
	/**
	 * 提交请求   部门经理办理任务
	 * @param id  任务id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/subreqagain.do")
	public boolean subreqagain(@RequestParam("uname")String uname,@RequestParam("id")String id,@RequestParam("name")String name,@RequestParam("comment")String comment){
		boolean flag=true;
		//第二种方式指派下一个任务人
		//Map<String, Object> variables = new HashMap<String,Object>();
		//variables.put("inputAdmin", "admin");
		//taskService.complete(id);
		Task task=taskService.createTaskQuery().taskId(id).singleResult();
		//流程实例id
		String ifs=task.getProcessInstanceId();
		//任务ID，根据流程实例ID，评论的消息，保存申请的评论信息
		Comment c=taskService.addComment(id, ifs, comment);
		Map<String, Object> variables = new HashMap<String,Object>();
		
		ProcessInstance pi=runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		 String bkey=pi.getBusinessKey();
		  String[] s=bkey.split("\\.");
		  int billid=Integer.parseInt(s[1]);
		  //请假人信息
		LeaveBill bill= activitiService.getLeaveBillById(billid);
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("id", bill.getReq_id());
		variables.put("outcome", name);//按钮
		
		taskService.complete(id, variables);
		if(name.equals("批准")){
			if(uname.equals("admin")){
				map.put("status", "总经理已批准");
				activitiService.updateReq(map);
			}else{
				map.put("status", "部门经理已批准");
				activitiService.updateReq(map);
			}
		}else{
			if(uname.equals("admin")){
				map.put("status", "总经理已驳回");
				activitiService.updateReq(map);
			}else{
				map.put("status", "部门经理已驳回");
				activitiService.updateReq(map);
			}
		}
		return flag;
	}
	
	/**
	 * 打开文本框    获取按钮值
	 * @param id 任务id
	 */
	@ResponseBody
	@RequestMapping("/getbuttonval.do")
	public Map<String,Object> dealReq(@RequestParam("id")String id){
		List<String> list =new ArrayList<String>();
		
		Task task=taskService.createTaskQuery().taskId(id).singleResult();
		//流程实例id
		String ifs=task.getProcessInstanceId();
		  ProcessInstance pi=runtimeService.createProcessInstanceQuery().processInstanceId(ifs).singleResult();
		  
		  String bkey=pi.getBusinessKey();
		  String[] s=bkey.split("\\.");
		  int billid=Integer.parseInt(s[1]);
		  //请假人信息
		  LeaveBill bill= activitiService.getLeaveBillById(billid);
		  
		 
		  String actid=pi.getActivityId();//当前活动的ID
		  ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
		  //当前活动对象
		  ActivityImpl activityImpl = processDefinitionEntity.findActivity(actid);
		  // 通过活动对象找当前活动的所有出口
	       List<PvmTransition> transitions =  activityImpl.getOutgoingTransitions();
	    // 6.提取所有出口的名称，封装成集合
	         for (PvmTransition trans : transitions) {
	             String transName = (String) trans.getProperty("name");
	             if(StringUtils.isNotBlank(transName)){
	            	 list.add(transName);
	             }
	        }
	         if(list.size()==0){
	        	 list.add("批准");//默认
	         }
	         
	         Map<String,Object>valmap=new HashMap<String, Object>();
	         valmap.put("bill", bill);
	         valmap.put("list", list);
	         return valmap;
	}
	
	
	/**
	 * 查询用户拥有的所有流程请求
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reqlist.do")
	public List<LeaveBill> getReqList(@RequestParam("uname")String uname){
		//List<LeaveBill>list=activitiService.getReqList(uname);
		List<LeaveBill>leaveList=new ArrayList<LeaveBill>();
		//查询个人任务
		List<HistoricTaskInstance>lists=historyService.createHistoricTaskInstanceQuery().taskAssignee(uname).list();
		for (int i = 0; i < lists.size() - 1; i++) {
            for (int j = lists.size() - 1; j > i; j--) {
                if (lists.get(j).getProcessInstanceId().equals(lists.get(i).getProcessInstanceId())) {
                	if(Integer.parseInt(lists.get(j).getId())>Integer.parseInt(lists.get(i).getId())){
                		lists.remove(i);
                	}else{
                		lists.remove(j);
                	}
                }
            }
    	}
		for (int i = 0; i < lists.size() - 1; i++) {
			if(!(lists.get(i).getName().equals("员工提交申请"))){
				lists.remove(i);
			}
		}
		for(HistoricTaskInstance h:lists){
			//流程实例id
			String ifs=h.getProcessInstanceId();
			  HistoricProcessInstance hpro=historyService.createHistoricProcessInstanceQuery().processInstanceId(h.getProcessInstanceId()).singleResult();
			  String bkey=hpro.getBusinessKey();
			  String[] s=bkey.split("\\.");
			  int billid=Integer.parseInt(s[1]);
			  //请假人信息
			  LeaveBill bill= activitiService.getLeaveBillById(billid);
			  if(h!=null){
				  bill.setTaskId(h.getId());
			  }
			 
			  leaveList.add(bill);
		}
		/*List<Task>taskList=taskService.createTaskQuery()//创建任务查询对象
				.taskAssignee(uname)//指定个人任务查询，指定办理人
				.list();
		for(Task t:taskList){
			//流程实例id
			String ifs=t.getProcessInstanceId();
			  ProcessInstance pro=runtimeService.createProcessInstanceQuery().processInstanceId(ifs).singleResult();
			  String bkey=pro.getBusinessKey();
			  String[] s=bkey.split("\\.");
			  int billid=Integer.parseInt(s[1]);
			  //请假人信息
			  LeaveBill bill= activitiService.getLeaveBillById(billid);
			  if(t!=null){
				  bill.setTaskId(t.getId());
			  }
			 
			  leaveList.add(bill);
		}*/
		return leaveList;
	}
	
	/**
	 * 查询个人任务
	 * @param uname
	 */
	@ResponseBody
	@RequestMapping("/tasklist.do")
	public List<PersonTask> getTaskList(@RequestParam("uname")String uname){
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Task>taskList=taskService.createTaskQuery()//创建任务查询对象
				.taskAssignee(uname)//指定个人任务查询，指定办理人
				.list();
		List<PersonTask>list=new ArrayList<PersonTask>();
		for(Task t:taskList){
			PersonTask p=new PersonTask();
			p.setId(t.getId());
			p.setAssignee(t.getAssignee());
			p.setCreatetime(s.format(t.getCreateTime()));
			p.setExcutionId(t.getExecutionId());
			p.setName(t.getName());
			p.setProcessDefinitionId(t.getProcessDefinitionId());
			p.setProcessInstanceId(t.getProcessInstanceId());
			list.add(p);
		}
		return list;
	}
	
	/**
	 * 查询个人组任务
	 * @param uname
	 */
	@ResponseBody
	@RequestMapping("/taskGroupList.do")
	public List<PersonTask> getTaskGroupList(@RequestParam("uname")String uname){
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Task>taskList=taskService.createTaskQuery()//创建任务查询对象
				.taskCandidateUser(uname)//指定个人任务查询，指定办理人
				.list();
		List<PersonTask>list=new ArrayList<PersonTask>();
		for(Task t:taskList){
			PersonTask p=new PersonTask();
			p.setId(t.getId());
			p.setAssignee(t.getAssignee());
			p.setCreatetime(s.format(t.getCreateTime()));
			p.setExcutionId(t.getExecutionId());
			p.setName(t.getName());
			p.setProcessDefinitionId(t.getProcessDefinitionId());
			p.setProcessInstanceId(t.getProcessInstanceId());
			list.add(p);
		}
		return list;
	}
}
