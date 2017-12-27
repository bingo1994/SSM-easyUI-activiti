<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门</title>
<%@ include file="../../include/easyui_core.jsp"%>
<script type="text/javascript">
function addmenu(){
	$("#win").window('open');
}
</script>
</head>
<body style="background:#F5FFFA">
<div>
<table border="0" id="tt" class="easyui-treegrid"  
        data-options="url:'${path }/getpmenu.do',idField:'id',toolbar:'#toolbar',treeField:'text',fitColumns : 'true'">   
    <thead>   
        <tr>   
            <th data-options="field:'id',width:40">编号</th>
					<th align="center" data-options="field:'text',width:150">资源名称</th>
					<th align="center" data-options="field:'url',width:200">资源路径</th>
					<th align="center" data-options="field:'sequence',width:50">排序</th>
					<th align="center" data-options="field:'iconCls',width:80">图标</th>
					<th align="center" data-options="field:'parentid',width:80">上级资源ID</th>
					<th align="center" data-options="field:'enable',width:40">状态</th>
					<th align="center" data-options="field:'action',width:120">操作</th>
        </tr>   
    </thead>   
</table>  
		<!--toolbar图标  -->
		<div id="toolbar">
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
				onclick="$('#tt').treegrid('reload')">刷新</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addmenu()">添加</a> 
		</div>
	</div>
	
	<!-- 添加部门窗口 -->
	<div id="win" class="easyui-window" title="添加菜单" style="width:450px;height:250px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform" action="" method="post">
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp; </td>
	</tr>
	<tr align="center">
	<td>菜单名称：</td>
	<td>
	<input id="dept_name" class="easyui-validatebox" required="true" name="dept_name" type="text" >
	 </td>
	</tr>
	<tr align="center">
	<td>上级ID：</td>
	<td><input id="dept_name" class="easyui-validatebox" required="true" name="dept_name" type="text" > </td>
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
	
</body>
</html>