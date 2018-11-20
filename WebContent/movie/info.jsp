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
<title><jsp:getProperty property="movie_title" name="rbn" /></title>
</head>
<body>
<h5 style="text-align:right;">「<jsp:getProperty name="loginForm" property="id"/>」ユーザでログイン中</h5>
<center>
	<html:form action="/InfoAction">

		<table>
			<logic:iterate id="info" name="infoList" type="model.ReviewBean">
			<img src="../softgShin/image/<%=info.getImage_url() %>">
			<h2><bean:write name="info" property="movie_title" /></h2>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td>ジャンル : <bean:write name="info" property="genre"/></td>
				</tr>
				<tr>
					<logic:notEqual name="info" property="duration" value="">
						<td>上映時間 : <bean:write name="info" property="duration"/>分</td>
					</logic:notEqual>
				</tr>
				<tr>
					<td>監督 : <bean:write name="info" property="director"/></td>
				</tr>
			</logic:iterate>
				<tr>
					<td><h3>いいね</h3><jsp:getProperty property="like" name="rbn" /></td>
				</tr>
			<tr>
				<td><h3>レビュー</h3></td>
			</tr>

			<logic:iterate id="review" name="reviewList" type="model.ReviewBean">
			<html:form action="/InfoAction">
			<table>
				<tr>
					<td>
						<html:hidden property="review_id" value="<%=String.valueOf(review.getReview_id()) %>"/>
						<bean:write name="review" property="user_id"/> :
					</td>
					<td>
						<%=review.getReview().replaceAll("\r\n", "<br />") %>
					</td>
					<td>
						<font size="1"><bean:write name="review" property="inp_date"/></font>
					</td>
					<td><logic:equal name="review" property="user_id" value="<%=loginForm.getId() %>">
						<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 4px 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    border-radius: 8px;" property="pageControl">
							<bean:message key="delete" />
						</html:submit>

					</logic:equal></td>
				</tr>
				</table>
				</html:form>
			</logic:iterate>

		</table>
		</html:form>
	<html:form action="/InfoAction">
		<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 8px 15px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    border-radius: 8px;" property="pageControl">
			<bean:message key="back" />
		</html:submit>
		<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 8px 15px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    border-radius: 8px;" property="pageControl">
			<bean:message key="review" />
		</html:submit>
	</html:form>
</center>
</body>
</html:html>