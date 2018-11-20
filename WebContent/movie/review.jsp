<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<jsp:useBean id = "loginForm" scope = "session" class = "action.form.LoginForm" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
<title>レビュー作成</title>
</head>
<body>
<h5 style="text-align:right;">「<jsp:getProperty name="loginForm" property="id"/>」ユーザでログイン中</h5>
<center>
	<html:form action="/ReviewAction">
		<table>
			<logic:iterate id="info" name="infoList" type="model.ReviewBean">
				<tr>
					<td><img width="320" src="../softgShin/image/<%=info.getImage_url() %>"></td>
				</tr>
				<tr>
					<td><bean:write name="info" property="movie_title" /></td>
				</tr>
			<html:errors/>
			</logic:iterate>
			<tr>
				<td>いいね<html:checkbox property="like" value="1"/></td>
			</tr>
			<tr>
				<td><html:textarea property="review" rows="20" cols="40"></html:textarea></td>
			</tr>

		</table>
		<html:cancel style=" background-color: #555555;
    border: none;
    color: white;
    padding: 8px 15px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    border-radius: 8px;" value="戻る" />
		<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 8px 15px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    border-radius: 8px;" property="pageControl">
			<bean:message key="reviewEnd" />
		</html:submit>
	</html:form>
	</center>
</body>
</html:html>