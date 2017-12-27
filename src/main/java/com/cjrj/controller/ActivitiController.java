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
	 * �ҵ�����
	 * @return
	 */
	@RequestMapping("/gomyreq.do")
	public String gotoReq(){
		return "myrequest";
	}
	
	/**
	 * ��ת����������
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
			//��������ʵ��id��ѯ����ʵ��
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			//�������̶���id��ѯ���̶���
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
			String resourceName=processDefinition.getDiagramResourceName();
			//��������Դ��
			resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
			runtimeService.getActiveActivityIds(processInstance.getId());
			//����������
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
	 * �������
	 * @param file
	 * @param req
	 */
	@RequestMapping("/addact.do")
	public String addAct(@RequestParam("filename")String filename,@RequestParam("file")MultipartFile file,HttpServletRequest req ){
		ZipInputStream zip;
		//��classpath·���¶�ȡ��Դ�ļ�  
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
	 * ��ѯ����
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
	 * �鿴����ͼ
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
	 * ɾ������
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
	 * ����������
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addreq.do")
	public int addReq(LeaveBill req){
		req.setStatus("��ʼ¼��");
		int i=activitiService.addReq(req);
		List<LeaveBill> lblist=activitiService.getOne();
		
		Map<String, Object> variables = new HashMap<String,Object>();
		String objId=lblist.get(0).getClass().getSimpleName()+"."+lblist.get(0).getReq_id();
		variables.put("objId", objId);
		variables.put("user", req.getUname());
		ProcessInstance	 pi=runtimeService.startProcessInstanceByKey("leavebill",objId,variables);//����
		return i;
	}
	
	/**
	 * �ύ�ҵ�������ͨԱ����
	 * @param id  Ա�����id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/subreq.do")
	public boolean submitReq(@RequestParam("taskId")String taskId, @RequestParam("id")int id,@RequestParam("uname")String uname){
		boolean flag=false;
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", "�����");
		int i=activitiService.updateReq(map);
		
		//������������
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("time", "4");
		//Map<String, Object> variables = new HashMap<String,Object>();
		//variables.put("inputUser", "aa");
		//��ʽ��LeaveBill.id����ʽ��ʹ�����̱�����
		if(i>0){
			flag=true;
			taskService.complete(taskId);
			//taskService.complete(taskId,variables);
		}
		return flag;
	}
	
	/**
	 * �ύ����   ���ž����������
	 * @param id  ����id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/subreqagain.do")
	public boolean subreqagain(@RequestParam("uname")String uname,@RequestParam("id")String id,@RequestParam("name")String name,@RequestParam("comment")String comment){
		boolean flag=true;
		//�ڶ��ַ�ʽָ����һ��������
		//Map<String, Object> variables = new HashMap<String,Object>();
		//variables.put("inputAdmin", "admin");
		//taskService.complete(id);
		Task task=taskService.createTaskQuery().taskId(id).singleResult();
		//����ʵ��id
		String ifs=task.getProcessInstanceId();
		//����ID����������ʵ��ID�����۵���Ϣ�����������������Ϣ
		Comment c=taskService.addComment(id, ifs, comment);
		Map<String, Object> variables = new HashMap<String,Object>();
		
		ProcessInstance pi=runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		 String bkey=pi.getBusinessKey();
		  String[] s=bkey.split("\\.");
		  int billid=Integer.parseInt(s[1]);
		  //�������Ϣ
		LeaveBill bill= activitiService.getLeaveBillById(billid);
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("id", bill.getReq_id());
		variables.put("outcome", name);//��ť
		
		taskService.complete(id, variables);
		if(name.equals("��׼")){
			if(uname.equals("admin")){
				map.put("status", "�ܾ�������׼");
				activitiService.updateReq(map);
			}else{
				map.put("status", "���ž�������׼");
				activitiService.updateReq(map);
			}
		}else{
			if(uname.equals("admin")){
				map.put("status", "�ܾ����Ѳ���");
				activitiService.updateReq(map);
			}else{
				map.put("status", "���ž����Ѳ���");
				activitiService.updateReq(map);
			}
		}
		return flag;
	}
	
	/**
	 * ���ı���    ��ȡ��ťֵ
	 * @param id ����id
	 */
	@ResponseBody
	@RequestMapping("/getbuttonval.do")
	public Map<String,Object> dealReq(@RequestParam("id")String id){
		List<String> list =new ArrayList<String>();
		
		Task task=taskService.createTaskQuery().taskId(id).singleResult();
		//����ʵ��id
		String ifs=task.getProcessInstanceId();
		  ProcessInstance pi=runtimeService.createProcessInstanceQuery().processInstanceId(ifs).singleResult();
		  
		  String bkey=pi.getBusinessKey();
		  String[] s=bkey.split("\\.");
		  int billid=Integer.parseInt(s[1]);
		  //�������Ϣ
		  LeaveBill bill= activitiService.getLeaveBillById(billid);
		  
		 
		  String actid=pi.getActivityId();//��ǰ���ID
		  ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
		  //��ǰ�����
		  ActivityImpl activityImpl = processDefinitionEntity.findActivity(actid);
		  // ͨ��������ҵ�ǰ������г���
	       List<PvmTransition> transitions =  activityImpl.getOutgoingTransitions();
	    // 6.��ȡ���г��ڵ����ƣ���װ�ɼ���
	         for (PvmTransition trans : transitions) {
	             String transName = (String) trans.getProperty("name");
	             if(StringUtils.isNotBlank(transName)){
	            	 list.add(transName);
	             }
	        }
	         if(list.size()==0){
	        	 list.add("��׼");//Ĭ��
	         }
	         
	         Map<String,Object>valmap=new HashMap<String, Object>();
	         valmap.put("bill", bill);
	         valmap.put("list", list);
	         return valmap;
	}
	
	
	/**
	 * ��ѯ�û�ӵ�е�������������
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reqlist.do")
	public List<LeaveBill> getReqList(@RequestParam("uname")String uname){
		//List<LeaveBill>list=activitiService.getReqList(uname);
		List<LeaveBill>leaveList=new ArrayList<LeaveBill>();
		//��ѯ��������
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
			if(!(lists.get(i).getName().equals("Ա���ύ����"))){
				lists.remove(i);
			}
		}
		for(HistoricTaskInstance h:lists){
			//����ʵ��id
			String ifs=h.getProcessInstanceId();
			  HistoricProcessInstance hpro=historyService.createHistoricProcessInstanceQuery().processInstanceId(h.getProcessInstanceId()).singleResult();
			  String bkey=hpro.getBusinessKey();
			  String[] s=bkey.split("\\.");
			  int billid=Integer.parseInt(s[1]);
			  //�������Ϣ
			  LeaveBill bill= activitiService.getLeaveBillById(billid);
			  if(h!=null){
				  bill.setTaskId(h.getId());
			  }
			 
			  leaveList.add(bill);
		}
		/*List<Task>taskList=taskService.createTaskQuery()//���������ѯ����
				.taskAssignee(uname)//ָ�����������ѯ��ָ��������
				.list();
		for(Task t:taskList){
			//����ʵ��id
			String ifs=t.getProcessInstanceId();
			  ProcessInstance pro=runtimeService.createProcessInstanceQuery().processInstanceId(ifs).singleResult();
			  String bkey=pro.getBusinessKey();
			  String[] s=bkey.split("\\.");
			  int billid=Integer.parseInt(s[1]);
			  //�������Ϣ
			  LeaveBill bill= activitiService.getLeaveBillById(billid);
			  if(t!=null){
				  bill.setTaskId(t.getId());
			  }
			 
			  leaveList.add(bill);
		}*/
		return leaveList;
	}
	
	/**
	 * ��ѯ��������
	 * @param uname
	 */
	@ResponseBody
	@RequestMapping("/tasklist.do")
	public List<PersonTask> getTaskList(@RequestParam("uname")String uname){
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Task>taskList=taskService.createTaskQuery()//���������ѯ����
				.taskAssignee(uname)//ָ�����������ѯ��ָ��������
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
	 * ��ѯ����������
	 * @param uname
	 */
	@ResponseBody
	@RequestMapping("/taskGroupList.do")
	public List<PersonTask> getTaskGroupList(@RequestParam("uname")String uname){
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Task>taskList=taskService.createTaskQuery()//���������ѯ����
				.taskCandidateUser(uname)//ָ�����������ѯ��ָ��������
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
