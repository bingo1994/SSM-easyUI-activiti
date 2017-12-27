<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>菜单页面</title>
<head>
<%@ include file="include/easyui_core.jsp" %>
<script type="text/javascript">

//菜单tree+tabs选项卡
$(function(){  
	$("#menus").tree({
		url:'getTree.do',
		parentField:'parentid',
		animate:true,
		onClick:function(node){
			var src=path+node.url;
			if(!node.url.substring(0,1)=='/'){
				src=node.url;
			} 
			var tabs=$('#tabs');
			var opts={
					title:node.text,
					closable:true,
					iconCls:node.iconCls,
					content:"<iframe frameborder='0' src='"+src+"' scrolling='auto' style='width:100%;height:100%;' ></iframe>",
					border:false,
					fit:true
			};
			if(tabs.tabs('exists',opts.title)){
				tabs.tabs('select',opts.title);
			}else{
				tabs.tabs('add',opts);
			}
		}});
  }); 

$.extend($.fn.validatebox.defaults.rules, {
    /*必须和某个字段相等*/
    equalTo: { validator: function (value, param) { 
    	return $(param[0]).val() == value; 
    	}, message: '字段不匹配' 
    	}
   });

	//修改密码
	function upPass(){
		$("#win").window('open');
	}
	function chkForm(){
		$("#uname").val(uname);
		var oldpass=$("#outpass").val();
		$.ajax({
			url:"getoldpass.do",
			type:"POST",
			dataType:"json",
			data:{user_account:uname},
			success:function(data){
				if($("#myform").form('validate')){
					if(oldpass==data.pass){
						$("#myform").submit();
					}else{
						$.messager.alert('提醒',"输入旧密码有误！")
					}
				}else{
					return false;
				}
			}
		});
	}
</script>
</head>
<body class="easyui-layout">

	<!-- 北边 -->
	 <div region="north" split="true" border="false" style="overflow: hidden; height: 85px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        
        <span style="padding:20px;overflow:hidden; font-size: 30px; "> <br>
       &nbsp; &nbsp;OA管理系统</span>
        <span style="float:right;"><table cellpadding="0"cellspacing="0"> 
        <tr><td>
        <span border="false" class="easyui-panel" style="padding:5px;background:#7f99be">
        <font size="3" color="#B0E0E6">欢迎 ${uname }</font>&nbsp; 
		<a id="btn-edit" href="#" class="easyui-menubutton" data-options="menu:'#mm1'"><font color="#7FFF00">修改密码</font></a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm2'"><font color="#7FFF00">离开</font></a>
        </span>
        <iframe frameborder='0' scrolling='auto' src='include/time.html' style='padding:0px;width:100%;height:25%;' ></iframe>
        </td></tr>
        </table></span>
    </div>
		
		<!--西边  -->
	<div data-options="region:'west',title:'导航菜单',split:false" 
		style="width: 150px;background:#E6E6FA">
		<div id="menus" style="padding:15px;"></div>
	</div>
	
	<!-- 中间 -->
	<div data-options="region:'center'"  border="false" style="overflow-y:hidden;">
		 <div id="tabs" class="easyui-tabs" fit="true" border="0">
			<div align="center" title="首页" style="padding:200px;background:#F5FFFA">
				<center>
					<font size="6px" color="#00FA9A"><strong>欢迎进入OA管理系统</strong></font>
				</center>
			</div>
		</div> 
	</div>
	
	
	<!-- 离开，修改密码 -->
	<div id="mm1" style="width:60px;">
		<div data-options="iconCls:'icon-edit'" onclick="upPass()">修改密码</div>
	</div>
        <div id="mm2" style="width:60px;">
        <div data-options="iconCls:'icon-undo'" onclick="location.href='login.jsp'">安全离开</div>
	</div>
	
	<!--修改密码 窗口 -->
	<div id="win" class="easyui-window" title="修改密码" style="width:450px;height:250px" closed=true
	data-options="iconCls:'icon-add',modal:true">
	<form id="myform" action="${path }/upUserPass.do" method="post">
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
	<td>旧密码：</td>
	<td>
	<input id="outpass" name="outpass"  class="easyui-validatebox" required="true" type="password" >
	<input id="uname" name="user_account"  type="hidden" > </td>
	</tr>
	<tr align="center">
	<td>新密码：</td>
	<td><input id="newpass" name="pass"  class="easyui-validatebox" required="true" type="password" > </td>
	</tr>
	<tr align="center">
	<td>确认密码：</td>
	<td><input id="rpass" type="password" required="true" class="easyui-validatebox"  validType="equalTo['#newpass']" invalidMessage="两次输入密码不匹配"></td>
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
</body>
</html>