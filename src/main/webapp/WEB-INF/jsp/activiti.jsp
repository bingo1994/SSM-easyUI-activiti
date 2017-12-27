<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../../include/easyui_core.jsp"%>
<script type="text/javascript">
$(function() {
	$("#dg").datagrid({
						url : path+'/getactinfo.do',
						fitColumns : true,/* 平均列 */
						pagination : true,//分页
						singleSelect : true,//单选
						toolbar : "#toolbar",
						pageNumber : 1,
						//pageSize:3,
						columns : [ [
								{
									field : 'deployment_id',
									title : 'dep_id',
									width : 150,
									align : 'center'
								},{
									field : 'pro_devl_name',
									title : '工作流程名称',
									width : 150,
									align : 'center'
								},{
									field : 'pro_devl_time',
									title : '时间',
									width : 150,
									align : 'center'
								},{
									field : 'pro_defi_name',
									title : '流程名称',
									width : 150,
									align : 'center'
								},{
									field : 'comment',
									title : '批注',
									width : 100,
									align : 'center'
								},{
									field : 'version',
									title : '版本',
									width : 80,
									align : 'center'
								},{
									field : 'resourcename',
									title : '资源名称',
									width : 100,
									align : 'center'
								},{
									field : 'opt',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(user_id, row,
											index) {
										 var str = '<a href="#" onclick="getAct('
												+ row.deployment_id+')">查看流程图</a>&nbsp;'
												+ '|&nbsp;'
												+ '<a href="#" onclick="delAct('
												+ row.deployment_id + ')">删除</a>'; 
										return str;
									}
								} ] ],
					});
});

//部署流程
function addActiviti(){
	$("#win").window('open');
}
function chkForm(){
	if($("#myform").form('validate')){
		$("#myform").submit();
	}
}

//查看流程图
function getAct(id){
	var row=$("#dg").datagrid('getSelected');
	var name=row.resourcename;
	//$("#tid").val(id);
	//$("#tid1").val(name);
	//$("#win1").window('open');
	location.href=path+"/getActPic.do?id="+id+"&name="+name;
}

function delAct(id){
	$.messager.confirm('系统提示','您确定要删除这条数据吗？',function(r){
		if(r){
			$.ajax({
				url:path+"/delact.do",
				type:'post',
				dataType:'json',
				data:{deployment_id:id},
				success:function(){
					$("#dg").datagrid('reload');
				},
				error:function(){
					$.messager.alert('系统提示','请求失败！');
				}
				
			});
		}
	})
}
</script>
</head>
<body>
<table border="1" cellpadding="0" cellspacing="0" id="dg"></table>
<!--toolbar图标  -->
		<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
				onclick="$('#dg').datagrid('reload')">刷新</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addActiviti()">添加流程</a> 
		</div>
		
	<!-- 添加流程 -->
	<div id="win" class="easyui-window" title="添加流程" style="width:350px;height:180px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform" action="${path }/addact.do" method="post" enctype="multipart/form-data">
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center">
	<td>文件：</td>
	<td><input id="files" width="120" name="file"  type="file" > </td>
	</tr>
	<tr align="center">
	<td>流程名称：</td>
	<td><input type="text" width="120" id="filename" name="filename" class="easyui-validatebox" required="true"></td>
	</tr>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center"><td colspan="2">
	<a href="#" id="aid" class="easyui-linkbutton" onclick="chkForm()" icon="icon-ok">提交</a>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
	
	<!-- 查看流程图 -->
	<div id="win1" class="easyui-window" title="查看流程图" style="width:350px;height:180px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	</div>
	
	<div id="win2" class="easyui-window" title="查看流程图" style="width:350px;height:180px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	</div>
</body>
</html>