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
						url : path+'/tasklist.do?uname='+uname,
						fitColumns : true,/* 平均列 */
						pagination : true,//分页
						singleSelect : true,//单选
						toolbar : "#toolbar",
						pageNumber : 1,
						//pageSize:3,
						columns : [ [
								{
									field : 'id',
									title : '任务id',
									width : 80,
									align : 'center'
								},{
									field : 'name',
									title : '任务名称',
									width : 80,
									align : 'center'
								},{
									field : 'createtime',
									title : '创建时间',
									width : 100,
									align : 'center'
								},{
									field : 'assignee',
									title : '任务办理人',
									width : 100,
									align : 'center'
								},{
									field : 'processInstanceId',
									title : '流程实例id',
									width : 120,
									align : 'center'
								},{
									field : 'excutionId',
									title : '执行对象id',
									width : 120,
									align : 'center'
								},{
									field : 'processDefinitionId',
									title : '流程定义id',
									width : 100,
									align : 'center'
								},{
									field : 'opt',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(user_id, row,
											index) {
										 var str = '<a href="#" onclick="getButtonVal('
												+ row.id+')">提交请求</a>&nbsp;'
												+ '|&nbsp;'
												+ '<a href="#" onclick="delRole('
												+ row.role_id + ')">删除</a>'; 
										return str;
									}
								} ] ],
					});
});

//打开添加窗口
function addReq(){
	$("#win").window('open');
}
//处理我的任务
function subActAgain(id){
	$.ajax({
		url:path+'/subreqagain.do',
		type:'post',
		dataType:'json',
		data:{id:id},
		success:function(data){
			if(data){
				$.messager.alert('系统提醒','提交成功！');
				reload();
			}
		},
		error:function(){
			$.messager.alert('系统提醒','请求失败！');
		}
	});
}
//刷新
function reload(){
	$("#dg").datagrid('reload');
	$("#win").window('close');
}
//获取按钮值  回写数据
function getButtonVal(id){
	$.ajax({
		url:path+'/getbuttonval.do',
		type:'post',
		data:'json',
		data:{id:id},
		success:function(data){
			var comment=$("#com").val("");
			$("#nid").text(data.bill.uname);
			$("#qid").text(data.bill.types);
			$("#sid").text(data.bill.stime);
			$("#eid").text(data.bill.etime);
			$("#reaid").text(data.bill.reasons);
			$("#oid").text(data.bill.otheradd);
			$("#stuid").text(data.bill.status);
			$("#did").empty();
			$.each(data.list,function(i){
				$("#did").append("<input type='button'value='"+this+"' onclick='dealreq("+id+",this)' name='rolename'>&nbsp;");
			  });
			$("#win").window('open');
		},
		error:function(){
			$.messager.laert('系统提醒','请求失败！');
		}
	});
}
//批准、驳回
function dealreq(id,c){
	var comment=$("#com").val();
	var name=c.value;//按钮信息
	 $.ajax({
		url:path+'/subreqagain.do',
		type:'post',
		dataType:'json',
		data:{id:id,name:name,comment:comment,uname:uname},
		success:function(data){
			if(data){
				$.messager.alert('系统提醒','提交成功！');
				reload();
			}
		},
		error:function(){
			$.messager.alert('系统提醒','请求失败！');
		}
	}); 
	
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
		
		
		<div id="win" class="easyui-window" title="申请" style="width:450px;height:330px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform" method="post" >
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center">
	<td>申请人：</td>
	<td><span id="nid"></span></td>
	</tr>
	<tr align="center">
	<td>请假类型：</td>
	<td><span id="qid"></span></td>
	</tr>
	<tr align="center">
	<td>起始时间：</td>
	<td><span id="sid"></span></td>
	</tr>
	<tr align="center">
	<td>结束时间：</td>
	<td> <span id="eid"></span></td>
	</tr>
	<tr align="center">
	<td>请假原因：</td>
	<td><span id="reaid"></span></td>
	</tr>
	<tr align="center">
	<td>状态：</td>
	<td><span id="stuid"></span></td>
	</tr>
	<tr align="center">
	<td>请假备注：</td>
	<td><span id="oid"></span></td>
	</tr>
	<tr align="center">
	<td>处理批注：</td>
	<td><textarea rows="2" id="com" name="comment" cols="13"></textarea></td>
	</tr>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr  align="center"><td colspan="2">
	<div align="center" id="did"></div>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
</body>
</html>