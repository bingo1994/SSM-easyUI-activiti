<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录界面</title>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="jquery-easyui-1.5.2/themes/material/easyui.css">
<link rel="stylesheet" href="jquery-easyui-1.5.2/themes/icon.css">
<link rel="stylesheet" href="jquery-easyui-1.5.2/themes/color.css">
<!-- 引入css文件 -->
<link href="css/public.css" rel="stylesheet" type="text/css">
<link href="css/login.css" rel="stylesheet"  type="text/css">
<script type="text/javascript">
function loginchk(){
	if($("#myform").form('validate')){
		$.ajax({
			url:'login.do',
			type:'post',
			dataType:'json',
			data:$("#myform").serialize(),
			success:function(data){
				 if(data.info=="登录成功"){//登录不成功
					location.href="layout.jsp";
				}else{
					$.messager.alert('系统提示',data.info);
				} 
			},
			error:function(){
				$.messager.alert('系统提示','登录请求失败！');
			}
		});
	}else{
		return false;
	}
}

</script>
</head>
<body>
<form id="myform" name="loginform"  method="post">
<DIV id=div1>
  <TABLE id=login height="600" cellSpacing=0 cellPadding=0 width=800 
align=center>
    <TBODY>
      <TR id=main>
        <TD>
          <TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
              <TR>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
              <TR height=30>
                <TD width=380>&nbsp;</TD>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD>
                <TD>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD rowSpan=4>&nbsp;</TD>
                <TD>用户名：</TD>
                <TD>
                  <INPUT class="easyui-validatebox" type="text" required="true" id=txtUserName name="user_account">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=40>
                <TD>密&nbsp;&nbsp;码：</TD>
                <TD>
                  <INPUT class="easyui-validatebox" required="true" id=txtUserPassword type="password" 
            name=pass>
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
             
              <TR height=40>
                <TD></TD>
                <TD align=right>
                  <INPUT  id="bid" type="button" onclick="loginchk()"  value="登 录 " name="btnLogin">
                  <INPUT id=btnLogin type="reset" value="重置">
                </TD>
                <TD width=120>&nbsp;</TD>
              </TR>
              <TR height=110>
                <TD colSpan=4>&nbsp;</TD>
              </TR>
            </TBODY>
          </TABLE>
        
        </TD>
      </TR>
      <TR id=root height=104>
        <TD>&nbsp;</TD>
      </TR>
    </TBODY>
  </TABLE>
</DIV>
  </form>
<DIV id=div2 style="DISPLAY: none"></DIV>
</body>
</html>