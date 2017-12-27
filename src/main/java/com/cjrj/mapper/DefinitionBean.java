package com.cjrj.mapper;


public class DefinitionBean {
	private String id;		
	private String key;
	private String pro_defi_name;
	private String pro_devl_name;	
	private String pro_devl_time;		
	private String version;		
	private String comment;		
	private String deployment_id;
	private String resourcename;
	public String getResourcename() {
		return resourcename;
	}
	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPro_defi_name() {
		return pro_defi_name;
	}
	public void setPro_defi_name(String pro_defi_name) {
		this.pro_defi_name = pro_defi_name;
	}
	public String getPro_devl_name() {
		return pro_devl_name;
	}
	public void setPro_devl_name(String pro_devl_name) {
		this.pro_devl_name = pro_devl_name;
	}
	public String getPro_devl_time() {
		return pro_devl_time;
	}
	public void setPro_devl_time(String pro_devl_time) {
		this.pro_devl_time = pro_devl_time;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDeployment_id() {
		return deployment_id;
	}
	public void setDeployment_id(String deployment_id) {
		this.deployment_id = deployment_id;
	}
}
