<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<jsp:useBean id = "cbn" scope = "session" class = "model.CrawlOutputBean" />
<jsp:useBean id = "loginForm" scope = "session" class = "action.form.LoginForm" />
<%@ page import="model.CrawlOutputBean,
				action.form.LoginForm"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
<title>上映中映画一覧</title>
</head>
<body background="123.jpg" >
	<h5 style="text-align:right;color:white">「<jsp:getProperty name="loginForm" property="id"/>」ユーザでログイン中</h5>
<h1 style="text-align:center;color:white">上映中映画一覧</h1>
<center>
	<html:form action="/IndexAction">
		<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    border-radius: 8px;" property="pageControl">
			<bean:message  key="logout" />
		</html:submit>
		<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    border-radius: 8px;" property="pageControl">
			<bean:message key="orderbylike" />
		</html:submit>
		<html:submit style=" background-color: #555555;
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    border-radius: 8px;" property="pageControl">
			<bean:message key="goindex" />
		</html:submit>
	</html:form>

		<table style="margin-left: auto; margin-right: auto;">

			<%int i = 0 ;%>
			<logic:iterate id="movieinfo" name="outputlist" type="model.CrawlOutputBean">
				<html:form action="/IndexAction">
					<%i++; %>

						<td>
						<table style="margin-left: auto; margin-right: auto;text-align:center;"bgcolor="#c1c1c1">
							<tr>
								<td><img width="200" src="../softgShin/image/<%=movieinfo.getImgurl() %>" /></td>
							</tr>
							<tr>
								<td style="width:200px; text-overflow:ellipsis; overflow:hidden;white-space:nowrap;">
									<div style="width:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;color:white">
										<%=movieinfo.getTitle() %>
									</div>
								</td>
							</tr>
							<tr>
								<td style="color:white">いいね：<bean:write name="movieinfo" property="likeCount"/></td>
							</tr>
							<tr>
								<td style="color:white"><bean:write name="movieinfo" property="reviewCount"/>件のレビュー</td>
							</tr>
							<tr>
								<td>
									<html:hidden property="movie_id" value="<%=String.valueOf(movieinfo.getMovie_id()) %>"/>
									<html:submit style="background-color: #555555;
   														border: none;
    													color: white;
    													padding: 8px 15px;
    													text-align: center;
    													text-decoration: none;
    													display: inline-block;
    													font-size: 12px;
    													border-radius: 8px;" property="pageControl">
										<bean:message key="choice" />
									</html:submit>
								</td>
							</tr>
						</table>
					</td>
				<%if(i % 5 == 0)  { %>
				</tr>
				<%} %>
				</html:form>
			</logic:iterate>
		</table>
	</center>
</body>
</html:html>