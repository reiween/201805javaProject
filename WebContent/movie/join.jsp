<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
<title>���[�U�o�^</title>
</head>
<body>
<center>
<h1>���[�U�o�^</h1>
<h3>���@�͕K�{�ł�</h3>
<html:errors/>
<html:form action="/JoinAction">
	<table>
		<tr>
			<td>���[�UID ��</td>
			<td><html:text property="id" maxlength="8"></html:text></td>
		</tr>
		<tr>
			<td>�p�X���[�h ��</td>
			<td><html:password property="pw" maxlength="8"/></td>
		</tr>
		<tr>
			<td>�p�X���[�h�i�m�F�p�j</td>
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
    border-radius: 8px;" value="�߂�" />

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