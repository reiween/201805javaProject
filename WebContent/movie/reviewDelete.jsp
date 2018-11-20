<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<jsp:useBean id = "rbn" scope = "session" class = "model.ReviewBean" />
<jsp:useBean id = "loginForm" scope = "session" class = "action.form.LoginForm" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
<title>レビュー削除</title>
</head>
<body>
<h5 style="text-align:right;">「<jsp:getProperty name="loginForm" property="id"/>」ユーザでログイン中</h5>
<center>
<html:form action="/ReviewDeleteAction">
			<logic:iterate id="review" name="reviewFind" type="model.ReviewBean">
				<tr>
					<td><bean:write name="review" property="user_id"/> :
					<bean:write name="review" property="review"/>
					<html:hidden property="review_id" value="<%=String.valueOf(review.getReview_id()) %>"/>
					<font size="1"><bean:write name="review" property="inp_date"/></font></td>

				</tr>
			</logic:iterate>
			<br />
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
				<bean:message key="delete" />
			</html:submit>
</html:form>
</center>
</body>
</html:html>