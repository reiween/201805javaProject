<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
<title>ユーザ登録</title>
</head>
<body>
<center>
<h1>ユーザ登録</h1>
<h3>※　は必須です</h3>
<html:errors/>
<html:form action="/JoinAction">
	<table>
		<tr>
			<td>ユーザID ※</td>
			<td><html:text property="id" maxlength="8"></html:text></td>
		</tr>
		<tr>
			<td>パスワード ※</td>
			<td><html:password property="pw" maxlength="8"/></td>
		</tr>
		<tr>
			<td>パスワード（確認用）</td>
			<td><html:password property="pw_profit" maxlength="8"/></td>
		</tr>
	</table>

	<html:cancel style=" background-color: #555555;
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    border-radius: 8px;" value="戻る" />

 	<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    border-radius: 8px;" property="pageControl">
		<bean:message key="done" />
	</html:submit>
</html:form>
</center>
</body>
</html:html>