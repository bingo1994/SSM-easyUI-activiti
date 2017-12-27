<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="jQuery.easyui.1.2.2 Demo/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="jQuery.easyui.1.2.2 Demo/js/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src="jQuery.easyui.1.2.2 Demo/js/outlook2.js"></script>
<link rel="stylesheet" href="jQuery.easyui.1.2.2 Demo/css/default.css">
<link rel="stylesheet" href="jQuery.easyui.1.2.2 Demo/js/themes/icon.css">
<link rel="stylesheet" href="jQuery.easyui.1.2.2 Demo/js/themes/gray/easyui.css">
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript">
	$(function() {
		$("#reg").window({
			width : 400,
			height : 200,
			modal : true,
			title : "注册",
			closed : true
		/* minimizable:false,
		maximizable:false,
		closable:false */
		});

	});

	function chks() {
		$("#reg").window('open');
	};
	
	function log(){
		$("#myform").submit();		
	}
	
</script>
<title>用户登录</title>
</head>
<body>

	<div id="win" class="easyui-dialog" closable="false" modal="true"
		title="用户登录" style="width: 300px; height: 180px;">
		<form id="myform" action="login.do" method="post" style="padding: 10px 20px 10px 40px;">
			<p>
				Name: <input name="user_account" type="text" class="easyui-validatebox" required="true">
			</p>
			<p>
				Pass: <input name="pass" type="password" class="easyui-validatebox"
					required="true">
			</p>
			<div style="padding: 5px; text-align: center;">
				<a href="#" class="easyui-linkbutton" onclick="log()" icon="icon-ok">登录</a> <a
					href="#" class="easyui-linkbutton" onclick="chks()" icon="icon-add">注册</a>
			</div>
		</form>
	</div>

	<div id="reg">
		<center>
			<form action="" method="post">
				<table>
					<tr>
						<td>登录账号：</td>
						<td><input type="text" name="user_account" width="80px"></td>
					</tr>
					<tr>
						<td>用户姓名：</td>
						<td><input type="text" name="user_name" width="100"></td>
					</tr>
					<tr>
						<td>性别：</td>
						<td><input type="radio" value="男" checked="checked"
							name="user_sex">男 <input type="radio" value="女"
							checked="checked" name="user_sex">女</td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password" name="pass" width="100"></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input type="password" name="rpass" width="100"></td>
					</tr>
					<tr align="center">
						<td colspan="2"><input type="submit" value="提交"></td>
					</tr>
				</table>
			</form>
		</center>

	</div>

</body>
</html>