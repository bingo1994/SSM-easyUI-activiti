<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%@ include file="../../include/easyui_core.jsp"%>
<script type="text/javascript">
$(function() {
	$("#dg").datagrid({
						url : path+'/reqlist.do?uname='+uname,
						fitColumns : true,/* 平均列 */
						pagination : true,//分页
						singleSelect : true,//单选
						toolbar : "#toolbar",
						pageNumber : 1,
						//pageSize:3,
						columns : [ [{
							field : 'taskId',
							title : '任务id',
							width : 80,
							align : 'center'
						},
								{
									field : 'req_id',
									title : '序号',
									width : 80,
									align : 'center'
								},{
									field : 'types',
									title : '类型',
									width : 80,
									align : 'center'
								},{
									field : 'stime',
									title : '起始时间',
									width : 120,
									align : 'center'
								},{
									field : 'etime',
									title : '结束时间',
									width : 120,
									align : 'center'
								},{
									field : 'reasons',
									title : '原因',
									width : 120,
									align : 'center'
								},{
									field : 'status',
									title : '状态',
									width : 100,
									align : 'center'
								},{
									field : 'uname',
									title : '申请人',
									width : 100,
									align : 'center'
								},{
									field : 'opt',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(user_id, row,
											index) {
										 var str = '<a href="#" onclick="subAct('
												+ row.req_id+')">提交请求</a>&nbsp;'
												+ '|&nbsp;'
												+ '<a href="#" onclick="showImage('
												+ row.taskId + ')">删除</a>'; 
										return str;
									}
								} ] ],
					});
});

//打开添加窗口
function addReq(){
	$("#win").window('open');
}
function addreq(){
	if($("#myform").form('validate')){
		$.ajax({
			url:path+'/addreq.do',
			type:'post',
			dataType:'json',
			data:$("#myform").serialize(),
			success:function(data){
				if(data>0){
					$.messager.alert('系统提示','添加成功！');
				}else{
					$.messager.alert('系统提示','添加失败！');
				}
				$("#win").window('close');
				$("#dg").datagrid('reload');
			},
			error:function(){
				$.messager.alert('系统提示','请求失败！');
			}
		});
	}else{
		return false;
	}
}

function subAct(id){
	var row=$("#dg").datagrid('getSelected');
	var status=row.status;
	var taskId=row.taskId;
	if(status=='初始录入'||status=='部门经理已驳回'||status=='总经理已驳回'){
		$.ajax({
			url:path+'/subreq.do',
			type:'post',
			dataType:'json',
			data:{id:id,uname:uname,taskId:taskId},
			success:function(data){
				if(data>0){
					$.messager.alert('系统提示','提交成功！');
					reload();
				}else{
					$.messager.alert('系统提示','提交失败！');
				}
			},
			error:function(){
				$.messager.alert('系统提示','请求失败！');
			}
		});
	}else{
		$.messager.alert('系统提示','请求已发送')
	}
	
}
function reload(){
	$("#dg").datagrid('reload');
}

function showImage(id){
	location.href=path+"/getImage.do?taskId="+id;
}
</script>
<body>
		<table border="1" cellpadding="0" cellspacing="0" id="dg"></table>
		<!--toolbar图标  -->
		<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addReq()">填写请求</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
				onclick="$('#dg').datagrid('reload')">刷新</a> 
		</div>

		<!-- 填写申请 -->
	<div id="win" class="easyui-window" title="填写申请" style="width:450px;height:330px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform" method="post" >
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center">
	<td>请假类型：</td>
	<td><input type="text" width="120" id="types" name="types" class="easyui-validatebox" required="true">
	<input type="hidden"  name="uname" value="${uname }">
	</td>
	</tr>
	<tr align="center">
	<td>起始时间：</td>
	<td><input type="text" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" width="120" id="stime" name="stime" class="easyui-validatebox" required="true"> </td>
	</tr>
	<tr align="center">
	<td>结束时间：</td>
	<td><input type="text" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" width="120" id="etime" name="etime" class="easyui-validatebox" required="true"> </td>
	</tr>
	
	<tr align="center">
	<td>请假原因：</td>
	<td><textarea rows="2" cols="13" name="reasons" id="reasons" class="easyui-validatebox" required="true"></textarea></td>
	</tr>
	<tr align="center">
	<td>请假备注：</td>
	<td><textarea rows="2" cols="13" name="otheradd" id="otheradd" class="easyui-validatebox" required="true"></textarea></td>
	</tr>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center"><td colspan="2">
	<a href="#" id="aid" class="easyui-linkbutton" onclick="addreq()" icon="icon-ok">提交</a>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
</body>
</html>