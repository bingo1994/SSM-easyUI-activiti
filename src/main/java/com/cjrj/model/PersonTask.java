package com.cjrj.model;

public class PersonTask {

	private String id;
	private String name;
	private String createtime;
	private String assignee;//���������
	private String processInstanceId;//����ʵ��id
	private String excutionId;//ִ�ж���id
	private String processDefinitionId;//���̶���id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getExcutionId() {
		return excutionId;
	}
	public void setExcutionId(String excutionId) {
		this.excutionId = excutionId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
}
