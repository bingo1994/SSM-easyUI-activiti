<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.cjrj.model.User"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户</title>
<%@ include file="../../../include/easyui_core.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#dg").datagrid({
							url : path+'/getuserlist.do',
							fitColumns : true,/* 平均列 */
							pagination : true,//分页
							//singleSelect : true,//单选
							//rownumbers : true,//显示行数
							toolbar : "#toolbar",
							pageNumber : 1,
							columns : [ [
									{
										field : 'ck',
										checkbox : true,
										width : 20,
										align : 'center'
									},{
										field : 'user_id',
										title : '编号',
										width : 100,
										align : 'center'
									},{
										field : 'user_name',
										title : '姓名',
										width : 100,
										align : 'center'
									},{
										field : 'user_sex',//field属性
										title : '性别',
										width : 100,
										align : 'center'
									},{
										field : 'dept_name',//field属性
										title : '部门',
										width : 100,
										align : 'center'
									},{
										field : 'user_address',//field属性
										title : '地址',
										width : 100,
										align : 'center'
									},{
										field : 'opt',//field属性
										title : '操作',
										width : 120,
										align : 'center',
										formatter : function(user_id, row,
												index) {
											var str = '<a href="#" onclick="addUser('
													+ row.user_id+ ')">修改</a>&nbsp;'
													+'<a href="#" onclick="getRoleMenu('
													+ row.user_id+ ')">菜单权限</a>&nbsp;'
													+ '<a href="#" onclick="getUserDetail('
													+ row.user_id+ ')">查看详情</a>&nbsp;'
													+ '<a href="#" onclick="delUser('
													+ row.user_id + ')">删除</a>';
											return str;
										}
									} ] ],
						});
	});
	//删除用户
	function delUser(user_id){
		$.messager.confirm('确认','您确定要删除这条数据吗？',function(r){
			if(r){
				location.href=path+"/deleteuser.do?user_id="+user_id;				
			}
		});
	}
	
	//批量删除用户
	function delUserByChk(){
		var checkedItems = $('#dg').datagrid('getChecked');
		var user_ids = [];
		$.each(checkedItems, function(index, item){
			user_ids.push(item.user_id);
		}); 
		if(user_ids.length>0){
			$.messager.confirm('确认','您确定要删除这些选中的数据吗？',function(r){
				if(r){
					location.href=path+"/deleteuser.do?user_ids="+user_ids;
				}
			});
		}else{
			$.messager.alert('提醒','请选中要删除的数据');
		}
	}
	//添加用户  修改用户
	function addUser(user_id){
		$("#uid").val(user_id);
		$.ajax({
			url:path+"/getmap.do",
			type:"POST",
			dataType:'json',
			data:{user_id:user_id},  
			success:function(data){
				if(data.user!=null){j
					$('#myform').form('load',data.user);
					$("#rpass").val(data.user.pass);
					$("#rtime").val(data.user.strtime);
				}else{
					$('#myform').form('clear');
					$("#sexid").prop("checked", true);//attr 1.4不行
				}
				$("#sel").empty();
				$.each(data.dlist,function(i){
					 if(data.user!=null&&data.user.udept_id==this.dept_id){
						 $("#sel").append("<option  selected value='"+this.dept_id+"'>"+this.dept_name+"</option>");
					}else{
					  $("#sel").append("<option   value='"+this.dept_id+"'>"+this.dept_name+"</option>"); 
					}
				  });
				$("#rid").empty();
				var i=0;
				$.each(data.rlist,function(i){
					$("#rid").append("<input type='checkbox' id='roleid' value='"+data.rlist[i].role_id+"' name='rolename'>"+data.rlist[i].role_name+"&nbsp;");
					  i++;
					  if(i%2==0){
						  $("#rid").append("<br>")
					  }
					  if(data.rolelist!=null){
							for(var x in data.rolelist){
								$("input[name='rolename']").each(function(){
									if(this.value==data.rolelist[x].role_id){
										this.checked=true;
									}
								});
							}
						}
				  });
			} 
		});
		if(user_id==undefined){
			$('#win').window('open').window('setTitle','添加用户');
		}else{
			$('#win').window('open').window('setTitle','修改用户');
		}
	}
	//查看详情
	function getUserDetail(user_id){
		$.ajax({
			url:path+"/getmap.do",
			type:"POST",
			dataType:'json',
			data:{user_id:user_id},  
			success:function(data){
					$("#userid").text(data.user.user_id);
					$("#account").text(data.user.user_account);
					$("#upass").text(data.user.pass);
					$("#name").text(data.user.user_name);
					if(data.user.user_address==null){
						$("#address").text("");
					}else{
						$("#address").text(data.user.user_address);
					}
					if(data.user.strtime==null){
						$("#time").text("");
					}else{
						$("#time").text(data.user.strtime);
					}
					$("#sex").text(data.user.user_sex);
					$("#dept").text(data.user.dept_name);
					$("#rolname").empty();
					  if(data.rolelist!=null){
							for(var x=0;x<data.rolelist.length;x++){
								$("#rolname").append(data.rolelist[x].role_name+"&nbsp;")
							}
						}
			} 
		});
		$('#dwin').window('open');
	}
	
	//表单验证
	function chkForm(){
		var u_account=$("#uaccount").val();
		var uid=$("#uid").val();
		//alert($("#myform").form('validate'))
		if($("#myform").form('validate')){//表单验证
			$.ajax({
				url:path+'/chkaccount.do',
				type:'POST',
				dataType:'json',
				data:{user_account:u_account},
				success:function(data){
					if(uid==undefined||uid==""){//添加
						if(data.flag==true){
							$.ajax({
								url:path+'/addUser.do',
								type:'post',
								dataType:'json',
								data:$("#myform").serialize(),
								success:function(i){
									if(i>0){
										$.messager.alert('系统提醒','添加成功！');
									}else{
										$.messager.alert('系统提醒','添加失败！');
									}
									$("#win").window('close');
									$("#dg").datagrid('reload');
								},
								error:function(){
									$.messager.alert('系统提醒','添加请求失败！');
								}
							});
						}else{
							$.messager.alert('提醒','此账号名已存在！');
						}
					}else{//修改
						$.ajax({
							url:path+'/addUser.do',
							type:'post',
							dataType:'json',
							data:$("#myform").serialize(),
							success:function(i){
								if(i>0){
									$.messager.alert('系统提醒','修改成功！');
								}else{
									$.messager.alert('系统提醒','修改失败！');
								}
								$("#win").window('close');
								$("#dg").datagrid('reload');
							},
							error:function(){
								$.messager.alert('系统提醒','修改请求失败！');
							}
						});
					}
				}
			});
		}else{
			return false;
		}
	}
	
	//条件查询
	function getUserByCon(){
		var name=$("#name").val();
		var address=$("#address").val();
		$("#dg").datagrid({
			url : path+"/getuserbycon.do?name="+name+"&address="+address+"",
			fitColumns : true,/* 平均列 */
			pagination : true,//分页
			//singleSelect : true,//单选
			//rownumbers : true,//显示行数
			toolbar : "#toolbar",
			pageNumber : 1,
			columns : [ [
					{
						field : 'ck',
						checkbox : true,
						width : 20,
						align : 'center'
					},{
						field : 'user_id',
						title : '编号',
						width : 100,
						align : 'center'
					},{
						field : 'user_name',
						title : '姓名',
						width : 100,
						align : 'center'
					},{
						field : 'user_sex',//field属性
						title : '性别',
						width : 100,
						align : 'center'
					},{
						field : 'dept_name',//field属性
						title : '部门',
						width : 100,
						align : 'center'
					},{
						field : 'user_address',//field属性
						title : '地址',
						width : 100,
						align : 'center'
					},{
						field : 'opt',//field属性
						title : '操作',
						width : 120,
						align : 'center',
						formatter : function(user_id, row,
								index) {
							var str = '<a href="#" onclick="addUser('
								+ row.user_id+ ')">修改</a>&nbsp;'
								+'<a href="#" onclick="getRoleMenu('
								+ row.user_id+ ')">菜单权限</a>&nbsp;'
								+ '<a href="#" onclick="getUserDetail('
								+ row.user_id+ ')">查看详情</a>&nbsp;'
								+ '<a href="#" onclick="delUser('
								+ row.user_id + ')">删除</a>';
						return str;
						}
					} ] ],
		});
	}
	
	 $.extend($.fn.validatebox.defaults.rules, {
	        /*必须和某个字段相等*/
	        equalTo: { validator: function (value, param) { return $(param[0]).val() == value; }, message: '字段不匹配' }
	       });
	 
	 function helps(){
		 $.messager.alert('帮助','请联系客服人员！');
	 }
	 
	 //权限分配
	 function getRoleMenu(id){
		 $("#menuwin").window('open');
		 $("#menus1").tree({
				multiple:true,
		         //async:true,//异步
				parentField:'parentid',
				checkbox:true,
				cascadeCheck:false,//选中父节点时，选中所有子节点
				url:path+'/getUserTree.do',
		        queryParams:{"user_id":id},
				  onCheck: function (node, checked) {
					  var tt = $("#menus1").tree();
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
				         /* var childNode = tt.tree("getChildren",node.target);
						for(var i= 0;i<childNode.length;i++){
						tt.tree("uncheck", childNode[i].target);
						} */
				            	
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
	 
	 //导出数据
	 function exportExcel(){
		 var checkedItems = $('#dg').datagrid('getChecked');
			var user_ids = [];
			$.each(checkedItems, function(index, item){
				user_ids.push(item.user_id);
			}); 
			/* $.each(user_ids,function(index, item){
				alert(item);
			}) */
			 if(user_ids.length>0){
				$.messager.confirm('确认','您确定要导出这些选中的数据吗？',function(r){
					if(r){
						location.href=path+"/exportexcel.do?user_ids="+user_ids;
					}
				});
			}else{
				$.messager.confirm('确认','您确定要导出所有数据吗？',function(r){
					if(r){
						location.href=path+"/exportexcel.do?user_ids="+user_ids;
					}
				});
			} 
	 }
	 //导入数据
	 function importExcel(){
		 $("#impwin").window('open');
		
	 }
	 function chktExcel(){
		 $("#impform").submit();
	 }
</script>
</head>
<body style="background:#F5FFFA">
<div id="p" class="easyui-panel" title="条件查询" style="width:101.5%;height:60px;">
<table>
<tr align="center">
	<td>姓名：</td>
	<td>
	<input id="name" name="name" type="text" >
	 </td>
	<td>地址：</td>
	<td><input id="address" name="address" type="text" > </td>
	<td><a href="#" id="" class="easyui-linkbutton" onclick="getUserByCon()" icon="icon-search">查询</a></td>
	</tr>
</table>
	</div>
	<div>
		<!-- 用户表 -->
		<table border="1" cellpadding="0" cellspacing="0" id="dg">
			
				<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">修改</a> 
				<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查看角色</a>
				 <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">删除</a> -->
		</table>
		<!--toolbar图标  -->
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addUser()">添加</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
				onclick="delUserByChk()">删除</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"
				onclick="exportExcel()">导出数据</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
				onclick="importExcel()">导入数据</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-help" plain="true"
				onclick="helps()">帮助</a>  
			<!--<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
				onclick="">取消</a> -->
		</div>
	</div>
	
	<!-- 添加用户窗口 -->
	<div id="win" class="easyui-window" title="添加/修改 用户" style="width:500px;height:380px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform"  method="post">
	<center>
	<table>
	<tr align="center">
	<td>登录账号：</td>
	<td>
	<input id="uaccount" class="easyui-validatebox" required="true" name="user_account" type="text" >
	<input id="uid" name="user_id" type="hidden" >
	 </td>
	</tr>
	<tr align="center">
	<td>密码：</td>
	<td><input id="pass" name="pass" validType="minLength[6]" class="easyui-validatebox" required="true" type="password" > </td>
	</tr>
	<tr align="center">
	<td>确认密码：</td>
	<td><input id="rpass" type="password" required="true" class="easyui-validatebox"  validType="equalTo['#pass']" invalidMessage="两次输入密码不匹配"></td>
	</tr>
	<tr align="center">
	<td>用户姓名：</td>
	<td><input id="unname" name="user_name"  class="easyui-validatebox" required="true" type="text" ></td>
	</tr>
	<tr align="center">
	<td>性别：</td>
	<td><input type="radio"  value="男" checked="checked" name="user_sex">男
	<input type="radio" id="sexid" value="女" name="user_sex">女</td>
	</tr>
	<tr align="center">
	<td>地址：</td>
	<td><input id="uaddress" name="user_address" type="text" ></td>
	</tr>
	<tr align="center">
	<td>部门：</td>
	<td><select id="sel"  name="udept_id"> </select> </td>
	</tr>
	<tr align="center">
	<td>入职时间：</td>
	<td>
	<input type="text" id="rtime"  readonly="readonly" onclick="WdatePicker()"  class="Wdate" name="regtime"> </td>
	</tr>
	<tr>
	<td align="center">角色分配：</td>
	<td id="rid" align="left"></td>
	</tr>
	<tr align="center"><td colspan="2">
	<a href="#" id="aid" class="easyui-linkbutton" onclick="chkForm()" icon="icon-ok">提交</a>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
	
	<!-- 用户详情 -->
	<div id="dwin" class="easyui-window" title="用户详情" style="width:450px;height:270px" closed=true
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
	<!-- <tr align="center"><td colspan="2">
	<a href="#" id="aid" class="easyui-linkbutton" onclick="$('#dwin').window('close')" icon="icon-cancel">返回</a>
	</td></tr> -->
	</table>
	</center>
	</div>
	
	
	
	<!-- 修改角色窗口 -->
	<div id="menuwin" class="easyui-window" title="权限分配" style="width:300px;height:250px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="mf" method="post">
	<center>
	<table cellpadding="0" cellspacing="0" width="80%" height="90%">
	<tr align="center">
	<td>&nbsp;</td>
	</tr>
	<tr align="center">
	<td>我的菜单</td>
	</tr>
	<tr align="center">
	<td>&nbsp;</td>
	</tr>
	<tr align="center"><td>
	<div id="menus1"></div>
	<!-- <input id="menus1" name="menu_ids" style="padding:15px;width:150px"> -->
	</td></tr>
	<tr align="center">
	<td>&nbsp;</td>
	</tr>
	<tr align="center"><td>
	<a href="#" id="menuid" class="easyui-linkbutton" onclick="$('#menuwin').window('close')" icon="icon-cancel">关闭</a></td></tr>
	</table>
	</center>
	</form>
	</div>
	
	
	<!-- 添加导入数据窗口 -->
	<div id="impwin" class="easyui-window" title="导入数据" style="width:350px;height:180px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="impform" action="${path }/impexcel.do"  method="post" enctype="multipart/form-data">
	<center>
	<table>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center">
	<td>文件：</td>
	<td>
	<input id="file" class="easyui-validatebox" required="true" name="impexcel" type="file" >
	 </td>
	</tr>
	<tr align="center">
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr align="center"><td colspan="2">
	<a href="#" class="easyui-linkbutton" onclick="chktExcel()" icon="icon-ok">提交</a>
	</td></tr>
	</table>
	</center>
	</form>
	</div>
</body>
</html>