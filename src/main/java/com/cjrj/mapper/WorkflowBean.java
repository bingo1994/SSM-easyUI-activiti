package com.cjrj.mapper;

import java.io.File;


public class WorkflowBean {

	private File file;		//���̶��岿���ļ�
	private String filename;//���̶�������
	
	private Long id;//���뵥ID
	
	private String deploymentId;//�������ID
	private String imageName;	//��Դ�ļ�����
	private String taskId;		//����ID
	private String outcome;		//��������
	private String comment;		//��ע
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getFilename() {
		return filename;
	}
	/*public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}*/
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
