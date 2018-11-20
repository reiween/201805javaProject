<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<jsp:useBean id = "jfm" scope = "session" class = "action.form.JoinForm" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
<title>ユーザ登録完了</title>
</head>
<body>
<center>
<h1>ユーザ登録完了</h1>
<table>
	<tr>
		<td>ユーザID</td>
		<td><jsp:getProperty property="id" name="jfm"/></td>
	</tr>
</table>
<h4>上記IDでユーザが登録されました。</h4>
<html:form action="/JoinEndAction">
	<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 8px 15px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    border-radius: 8px;" property="pageControl">
		<bean:message key="gologin" />
	</html:submit>
</html:form>
</center>
</body>
</html>