<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="user" type="com.epam.doiun.entity.User"%>
<%@ attribute name="disableLoginInfo"  type="java.lang.Boolean"%>
<c:if test="${empty disableLoginInfo or disableLoginInfo eq false}">
	<c:choose>
		<c:when test="${user == null}">
			<form action='<c:url value="login"/>' class="navbar-form navbar-right" method="POST" name="loginForm" id="loginForm" >
				<div class="form-group">
					<input id="email" type="text" name="email" class="form-control inputbox" placeholder='<fmt:message key="email"/>'> 
					<input id="password" type="password" name="password" class="form-control inputbox" placeholder='<fmt:message key="password"/>'>
				</div>
				<button type="submit" name="Submit" class="form-control btn btn-default button-white"><fmt:message key="login"/></button>
			</form>
		</c:when>
		<c:otherwise>
		  <ul class="nav navbar-nav navbar-right">
	        <li><a href='<c:url value="logout"/>'> <fmt:message key="logout"/></a></li>
	      </ul>
	      <p class="navbar-text navbar-right">${user.firstName} ${user.lastName}</p>
	      <p class="navbar-text navbar-right"><img src="<c:url value="media?path=${user.avatarPath}"></c:url>" class="img-responsive" alt="" style="height: 40px;"/></p>
		</c:otherwise>
	</c:choose>
</c:if>