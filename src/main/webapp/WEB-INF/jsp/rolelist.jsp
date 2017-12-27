<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色</title>
<%@ include file="../../include/easyui_core.jsp"%>
<script type="text/javascript">
$(function() {
	$("#dg").datagrid({
						url : path+'/role/getrolelist.do',
						fitColumns : true,/* 平均列 */
						pagination : true,//分页
						singleSelect : true,//单选
						toolbar : "#toolbar",
						pageNumber : 1,
						//pageSize:3,
						columns : [ [
								{
									field : 'role_id',
									title : '编号',
									width : 100,
									align : 'center'
								},{
									field : 'role_name',
									title : '角色名称',
									width : 100,
									align : 'center'
								},{
									field : 'role_desc',//field属性
									title : '角色描述',
									width : 100,
									align : 'center'
								},{
									field : 'menuName',//field属性
									title : '菜单权限',
									width : 150,
									align : 'center'
								},{
									field : 'opt',//field属性
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(user_id, row,
											index) {
										var str = '<a href="#" onclick="upRole('
												+ row.role_id+ ')">编辑</a>&nbsp;'
												+ '|&nbsp;'
												+ '<a href="#" onclick="delRole('
												+ row.role_id + ')">删除</a>';
										return str;
									}
								} ] ],
					});
});

//打开添加窗口
function addRole(){
	$("#role_name").val("");
	$("#role_desc").val("");
	$("#win").window('open');
	$("#menus").combotree({
		 multiple:true,
         async:false,
		url:path+'/role/getTree.do',
		parentField:'parentid',
		checkbox:true,
		cascadeCheck:false,
		 /*  onCheck:function(node,checked){//全选全不选
			    var tt = $("#menus").combotree("tree");
			    if(checked){
			    var childNode = tt.tree("getChildren",node.target);
			    for(var i= 0;i<childNode.length;i++){
			    tt.tree("check", childNode[i].target);
			    }
			    }
			    else{
			var childNode = tt.tree("getChildren",node.target);
			    for(var i= 0;i<childNode.length;i++){
			    tt.tree("uncheck", childNode[i].target);
			    }
			    }
			    } */
			    
			  onCheck: function (node, checked) {
				 var tt = $("#menus").combotree("tree");
	            if (checked) {
	            	//1
	            	var childNode = tt.tree("getChildren",node.target);
				    for(var i= 0;i<childNode.length;i++){
				    tt.tree("check", childNode[i].target);
				    }
	            	
				    //2
	                var parentNode = tt.tree('getParent', node.target);
	                if (parentNode != null) {
	                	tt.tree('check', parentNode.target);
	                }
	            } else {
	            	//1
	            	var childNode = tt.tree("getChildren",node.target);
				    for(var i= 0;i<childNode.length;i++){
				    tt.tree("uncheck", childNode[i].target);
				    }
	            	
				    //2
	                var childNode = tt.tree('getChildren', node.target);
	                if (childNode.length > 0) {
	                    for (var i = 0; i < childNode.length; i++) {
	                    	tt.tree('uncheck', childNode[i].target);
	                    }
	                }
	            }
	        } 
	});
}
//验证/添加角色
function chkForm(){
	/* var checkedItems = $('#menus').combotree('getChecked');
	alert(checkedItems) */
	//var checkedItems = $('#menus').tree('getChecked');
	/* var menu_ids = [];
	 $.each(checkedItems, function(index, item){
		menu_ids.push(item.id);
	});  */
	  /*  $.each(menu_ids,function(i){
		alert(this);
	}); */   
	//$("#roles").val(menu_ids);
	if($("#myform").form('validate')){
		var x=$("#role_name").val();
		//验证角色
		 $.ajax({
			url:path+'/role/chkrole.do',
			type:'post',
			dataType:'json',
			data:{role_name:x},
			success:function(data){
				if(data){
					$.ajax({
						url:path+'/role/addrole.do',
						type:'post',
						dataType:'json',
						data:$("#myform").serialize(),
						success:function(i){
							if(i>0){
								$.messager.alert('系统提示','角色添加成功！');
							}else{
								$.messager.alert('系统提示','角色添加失败');								
							}
							$("#win").window('close');
							$("#dg").datagrid('reload');
						},
						error:function(){
							$.messager.alert('系统提示','添加请求失败！');
						}
					});
				}else{
					$.messager.alert('系统提示','此角色已存在！');
				}
			},
			error:function(){
				$.messager.alert('系统提示','验证请求失败！');
			} 
		});
	}else{
		return false;
	}
}

//打开修改
function upRole(role_id){
	//var row=$("#dg").datagrid('getSelected');  //影响操作
	$.ajax({
		url:path+'/role/getroleinfo.do',
		type:'post',
		dataType:'json',
		data:{role_id:role_id},
		success:function(data){
			 //$("#myform1").form('load',data);  //影响操作
			  $("#role_name1").val(data.role_name);
			$("#role_desc1").val(data.role_desc);
			$("#role_id1").val(data.role_id);  
		}
	});
		$("#win1").window('open').window('setTitle','修改角色');
		 $("#menus1").combotree({
			multiple:true,
	         //async:true,//异步
			parentField:'parentid',
			checkbox:true,
			cascadeCheck:false,//选中父节点时，选中所有子节点
			url:path+'/role/getRoleTree.do',
	        queryParams:{"role_id":role_id},
			  onCheck: function (node, checked) {
				var tt = $("#menus1").combotree("tree");
		         if (checked) {
		         //1
		       var childNode = tt.tree("getChildren",node.target);
				for(var i= 0;i<childNode.length;i++){
				tt.tree("check", childNode[i].target);
					}
		            	
				//2
		        var parentNode = tt.tree('getParent', node.target);
		        if (parentNode != null) {
		          tt.tree('check', parentNode.target);
		           }
		         } else {
		         //1
		         var childNode = tt.tree("getChildren",node.target);
				for(var i= 0;i<childNode.length;i++){
				tt.tree("uncheck", childNode[i].target);
				}
		            	
				//2
		        var childNode = tt.tree('getChildren', node.target);
		        if (childNode.length > 0) {
		        for (var i = 0; i < childNode.length; i++) {
		          tt.tree('uncheck', childNode[i].target);
		            }
		          }
		        }
		     } 
		}); 
	
}
//修改
function updateRole(){
	if($("#myform1").form('validate')){
					$.ajax({
						url:path+'/role/updaterole.do',
						type:'post',
						dataType:'json',
						data:$("#myform1").serialize(),
						success:function(i){
							if(i>0){
								$.messager.alert('系统提示','角色修改成功！');
							}else{
								$.messager.alert('系统提示','角色修改失败！');
							}
							$("#win1").window('close');
							$("#dg").datagrid('reload');
						},
						error:function(){
							$.messager.alert('系统提示','修改请求失败！');
						} 
					});
			}else{
				return false;
			}
}


//删除角色
function delRole(role_id){
	$.messager.confirm('确认','您确定要删除这条信息吗？',function(r){
		if(r){
			$.ajax({
				url:path+'/role/delrole.do',
				type:'post',
				dataType:'json',
				data:{role_id:role_id},
				success:function(i){
					if(i>0){
						$.messager.alert('系统提示','角色删除成功！');
					}else{
						$.messager.alert('系统提示','角色删除失败！');
					}
					$("#dg").datagrid('reload');
				}
			});
		}
	});
}


function helps(){
	$.messager.alert('帮助','<center><br>请联系客服人员！<br>0553-78459</center>');
}
</script>
</head>
<body style="background:#F5FFFA">
<div>
		<!-- 角色表 -->
		<table border="1" cellpadding="0" cellspacing="0" id="dg"></table>
		<!--toolbar图标  -->
		<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
				onclick="$('#dg').datagrid('reload')">刷新</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addRole()">添加</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-help" plain="true"
				onclick="helps()">帮助</a> 
		</div>
	</div>
	
	<!-- 添加角色窗口 -->
	<div id="win" class="easyui-window" title="添加角色" style="width:400px;height:250px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform" method="post">
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center">
	<td>角色名称：</td>
	<td><input id="role_name" name="role_name" class="easyui-validatebox" required="true" type="text" > </td>
	</tr>
	<tr align="center">
	<td>角色描述：</td>
	<td>
	<!-- <input id="roles" name="menu_ids" type="hidden"> -->
	<textarea rows="3" id="role_desc" cols="13" required="true" class="easyui-validatebox"  name="role_desc"></textarea></td>
	</tr>
	<tr align="center">
	<td>权限分配：</td>
	<td >
	<input id="menus" name="menu_ids" style="padding:15px;width:150px">
	<!-- <div id="menus" style="padding:15px;width:150px" ></div> -->
	</td>
	</tr>
	<tr align="center"><td colspan="2">
	<a href="#" id="aid" class="easyui-linkbutton" onclick="chkForm()" icon="icon-ok">提交</a>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
	
	
	<!-- 修改角色窗口 -->
	<div id="win1" class="easyui-window" title="添加角色" style="width:400px;height:250px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform1" method="post">
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center">
	<td>角色名称：</td>
	<td><input id="role_name1" name="role_name" class="easyui-validatebox" required="true" type="text" >
	<input id="role_id1" name="role_id" type="hidden" > 
	 </td>
	</tr>
	<tr align="center">
	<td>角色描述：</td>
	<td><textarea rows="3" id="role_desc1" cols="13" required="true" class="easyui-validatebox"  name="role_desc"></textarea></td>
	</tr>
	<tr align="center">
	<td>权限分配：</td>
	<td>
	<input id="menus1" name="menu_ids" style="padding:15px;width:150px">
	</td>
	</tr>
	<tr align="center"><td colspan="2">
	<a href="#" id="aid1" class="easyui-linkbutton" onclick="updateRole()" icon="icon-ok">提交</a>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
	
</body>
</html>