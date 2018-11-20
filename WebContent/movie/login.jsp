<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<style type="text/css">

</style>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
<title>ログイン</title>
</head>
<body>

<center>
<h1 style="text-align:center; color:#1c1c1c">ログイン</h1>
<html:errors/>
<html:form action="/LoginAction">
	<table style="margin-left: auto; margin-right: auto;" >
		<tr>
			<td>ユーザーID ※</td>
			<td><html:text property="id" maxlength="8"></html:text></td>
		</tr>
		<tr>
			<td>パスワード ※</td>
			<td><html:password property="pw" maxlength="8" /></td>
		</tr>
	</table>
		<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    border-radius: 8px;" property="pageControl">
			<bean:message key="login" />
		</html:submit>
		<br />
		<br />
		<h3>初めての方はユーザ登録をしてください。</h3>
		<br />
		<html:submit style=" background-color:#555555;
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    border-radius: 8px;" property="pageControl">
			<bean:message key="join" />
		</html:submit>
</html:form>
</center>
</body>
</html:html>