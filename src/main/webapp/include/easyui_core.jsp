<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.cjrj.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath }" var="path"></c:set>

 <%
User user=(User)request.getSession().getAttribute("USER");
 String username=user.getUser_account();
 request.setAttribute("uname", username);
 String upass=user.getPass();
 request.setAttribute("upass", upass);
%> 
<c:set value="${upass}" var="upass"></c:set>
<c:set value="${uname}" var="uname"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
var path="${path}";
var uname="${uname}";
</script>
<script type="text/javascript" src="${path}/js/login.js"></script>
<script type="text/javascript" src="${path}/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="${path}/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="${path}/jquery-easyui-1.5.2/themes/material/easyui.css">
<link rel="stylesheet" href="${path}/jquery-easyui-1.5.2/themes/icon.css">
<link rel="stylesheet" href="${path}/jquery-easyui-1.5.2/themes/color.css">
<link rel="stylesheet" href="${path}/jquery-easyui-1.5.2/themes/default/easyui.css">
<link rel="stylesheet" href="${path}/jquery-easyui-1.5.2/demo/demo.css">

<script type="text/javascript" src="${path}/charjs/highcharts.js"></script>
<script type="text/javascript" src="${path}/charjs/exporting.js"></script>
<script type="text/javascript" src="${path}/charjs/highcharts-zh_CN.js"></script>
</head>
<body>

</body>
</html>