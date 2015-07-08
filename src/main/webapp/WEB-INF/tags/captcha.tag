<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="image" required="true"%>
<%@ attribute name="useHidden" type="java.lang.Boolean" required="true"%>
<%@ attribute name="idCaptcha" type="java.lang.Integer" required="true"%>
<div class="col-md-3 col-md-offset-3">
	<img alt="captcha" src="data:image/png;base64,${image}">
</div>
<div class="col-md-6">
	<label for="captcha" id="captchaLabel" ><fmt:message key="registration.enterSymbol"/></label> 
	<input type="text" class="inputbox" id="captcha" name="captcha"
		autocomplete="off">
	<c:if test="${useHidden}">
		<input type="hidden" class="inputbox" id="idCaptcha" name="idCaptcha"
			value="${idCaptcha}">
	</c:if>
</div>