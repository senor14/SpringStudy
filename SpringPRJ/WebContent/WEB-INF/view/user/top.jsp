<%@ page import="static poly.util.CmmUtil.nvl" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

	String name = nvl((String)session.getAttribute("name"));

%>
<nav class="navbar navbar-light navbar-expand-lg navbar-fixed white no-backgorund bootsnav" style="background-color:gray;">
	<div class="top-search">
		<div class="container">
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-search"></i></span>
				<input type="text" class="form-control" placeholder="Search">
				<span class="input-group-addon close-search" onClick="JavaScript:search();"><i class="fa fa-times">검색</i></span>
			</div>
		</div>
	</div>
	
	<div class="container">
		<div class="attr-nav">
			<ul>
				<li>
					<%if(name.isEmpty()){ %>
					<a href="/user/userLogin.do" style="color:#2b2b2b;">
						Sign in
					</a>
					<%} else { %>
					<a href="/user/logOut.do" style="color:"#2b2b2b;"><%=name  %>님 Sign Out </a>
					<%} %>
				</li>
			</ul>
		</div>
	</div>
</nav>
</html>