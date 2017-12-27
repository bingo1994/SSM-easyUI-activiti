<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门</title>
<%@ include file="../../include/easyui_core.jsp"%>
<script type="text/javascript">
$(function() {
	$("#dg").datagrid({
						url : path+'/getdeptlist.do',
						fitColumns : true,/* 平均列 */
						pagination : true,//分页
						singleSelect : true,//单选
						toolbar : "#toolbar",
						pageNumber : 1,
						//pageSize:3,
						columns : [ [
								{
									field : 'dept_id',
									title : '部门编号',
									width : 100,
									align : 'center'
								},{
									field : 'dept_name',
									title : '部门名称',
									width : 100,
									align : 'center'
								},{
									field : 'dept_desc',//field属性
									title : '部门描述',
									width : 100,
									align : 'center'
								},{
									field : 'opt',//field属性
									title : '操作',
									width : 120,
									align : 'center',
									formatter : function(user_id, row,
											index) {
										var str = '<a href="#" class="easyui-linkbutton"  onclick="updateGetDept('
												+ row.dept_id+ ')">编辑</a>&nbsp;'
												+ '|&nbsp;'
												+ '<a href="#" onclick="delDept('
												+ row.dept_id + ')">删除</a>';
										return str;
									}
								} ] ],
					});
});

//添加
function addDept(){
	$("#dept_name").val("");
	$("#dept_desc").val("");
	$("#win").window('open').window("setTitle","添加部门");
}
//部门验证
function chkForm(){
	if($("#myform").form('validate')){
		var x=$("#dept_name").val()
		$.ajax({
			url:path+'/chkdept.do',
			type:'post',
			dataType:'json',
			data:{dept_name:x},
			success:function(data){
				if(data){
					$.ajax({
						url:path+'/adddept.do',
						type:'POST',
						dataType:'json',
						data:$("#myform").serialize(),
						success:function(data){
							if(data>0){
								$.messager.show({
									title:'系统提示',
									msg:'添加成功！'
								})
							}else{
								$.messager.show({
									title:'系统提示',
									msg:'添加失败！'
								});
							}
							$("#win").window('close');
							$("#dg").datagrid('reload');
						}
					});
				}else{
					$.messager.alert('系统提醒','此部门已存在！');
				}
			},
			error:function(){
				$.messager.alert('系统提醒','请求失败')
			}
		});
	}else{
		return false;
	}
}

//修改
function updateGetDept(){
	var row=$("#dg").datagrid('getSelected');
	if(row){
		$("#win1").window('open').window('setTitle','修改部门');
		$("#myform1").form('load',row);
	}
}
function updateDept(){
	if($("#myform1").form('validate')){
		$.ajax({
			url:path+'/updatedept.do',
			type:'post',
			dataType:'json',
			data:$("#myform1").serialize(),
			success: function(data){
				if(data>0){
					$.messager.show({
						title:'系统提示',
						msg:'修改成功！'
					});
				}else{
					$.messager.show({
						title:'系统提示',
						msg:'修改失败！'
					});
				}
				$("#win1").window('close');
				$("#dg").datagrid('reload');
			},
			error:function(){
				$.messager.alert('系统提醒','请求失败')
			}
		});
	}else{
		return false;
	}
}

//删除部门
function delDept(deptid){
	$.messager.confirm('系统提示','您确定要删除这条数据吗？',function(r){
		if(r){
			$.ajax({
				url:path+'/deldept.do',
				type:'POST',
				dataType:'json',
				data:{dept_id:deptid},
				success:function(data){
					if(data>0){
						$.messager.show({
							title:'系统提示',
							msg:'删除成功！'
						});
					}else{
						$.messager.show({
							title:'系统提示',
							msg:'删除失败！'
						});
					}
					$("#dg").datagrid('reload');
				}
			});
		}
	});
}

//帮助
function helps(){
	$.messager.alert("系统提示","请联系客服人员！")
}
</script>
</head>
<body style="background:#F5FFFA">
<div>
		<!-- 部门表 -->
		<table border="1" cellpadding="0" cellspacing="0" id="dg">
		</table>
		<!--toolbar图标  -->
		<div id="toolbar">
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
				onclick="$('#dg').datagrid('reload')">刷新</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addDept()">添加</a> 
			<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
				onclick="delUserByChk()">删除</a>  -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-help" plain="true"
				onclick="helps()">帮助</a> 
		</div>
	</div>
	
	<!-- 添加部门窗口 -->
	<div id="win" class="easyui-window" title="添加/修改 部门" style="width:450px;height:250px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform" action="${path }/adddept.do" method="post">
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp; </td>
	</tr>
	<tr align="center">
	<td>部门名称：</td>
	<td>
	<input id="dept_name" class="easyui-validatebox" required="true" name="dept_name" type="text" >
	 </td>
	</tr>
	<tr align="center">
	<td>部门描述：</td>
	<td><textarea id="dept_desc" class="easyui-validatebox" required="true" name="dept_desc" rows="4" cols="13"></textarea> </td>
	</tr>
	<tr align="center"><td colspan="2">
	<a href="#" id="aid" class="easyui-linkbutton" onclick="chkForm()" icon="icon-ok">提交</a>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
	
	<!-- 修改部门窗口 -->
	<div id="win1" class="easyui-window" title="修改部门" style="width:450px;height:250px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform1"  method="post">
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp; </td>
	</tr>
	<tr align="center">
	<td>部门编号：</td>
	<td>
	<input id="dept_id" readonly="readonly" class="easyui-validatebox" required="true" name="dept_id" type="text" >
	 </td>
	</tr>
	<tr align="center">
	<td>部门名称：</td>
	<td>
	<input id="dept_name1" class="easyui-validatebox" required="true" name="dept_name" type="text" >
	 </td>
	</tr>
	<tr align="center">
	<td>部门描述：</td>
	<td><textarea id="dept_desc" class="easyui-validatebox" required="true" name="dept_desc" rows="4" cols="13"></textarea> </td>
	</tr>
	<tr align="center"><td colspan="2">
	<a href="#" id="aid" class="easyui-linkbutton" onclick="updateDept()" icon="icon-ok">提交</a>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
	
	
	<!-- 用户详情 -->
	<div id="dwin" class="easyui-window" title="用户详情" style="width:500px;height:270px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<center>
	<table  width="60%" >
	<tr align="center">
	<td>编号：</td>
	<td id="userid"></td>
	</tr>
	<tr align="center">
	<td>登录账号：</td>
	<td id="account"></td>
	</tr>
	<tr align="center">
	<td>密码：</td>
	<td id="upass"></td>
	</tr>
	<tr align="center">
	<td>用户姓名：</td>
	<td id="name"></td>
	</tr>
	<tr align="center">
	<td>性别：</td>
	<td id="sex"></td>
	</tr>
	<tr align="center">
	<td>地址：</td>
	<td id="address"></td>
	</tr>
	<tr align="center">
	<td>部门：</td>
	<td id="dept"></td>
	</tr>
	<tr align="center">
	<td>入职时间：</td>
	<td id="time"></td>
	</tr>
	<tr align="center">
	<td>角色：</td>
	<td id="rolname"></td>
	</tr>
	</table>
	</center>
	</div>
</body>
</html>