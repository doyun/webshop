<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/tags.jspf"%>
<%@ include file="/WEB-INF/fragment/l10n.jspf"%>
<!DOCTYPE HTML>
<html>
<webshop:head title="Free Snow Bootstrap Website Template | Register ::
	w3layouts"></webshop:head>
<body>
	<%@ include file="/WEB-INF/fragment/navbar.jspf"%>
	
	<div class="main">
		<div class="shop_top">
			<div class="container">
				<c:if test="${bean.status != null}">
					<div class="alert alert-danger alert-dismissible" role="alert">
 						    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					    <fmt:message key="${bean.status}"/>
					</div>
				</c:if>
				<form  method="POST" name="registerForm" id="registerForm" onsubmit="return validateSignUpFormjQuery()" enctype="multipart/form-data">
					<fieldset class="input">
						<div class="row">
							<h3><fmt:message key="registration.personalInfo"/></h3>
							<div class="col-md-6">
								<label for="firstName" id="firstNameLabel"><fmt:message key="registration.firstName"/>*</label> 
								<input type="text" id="firstName" class="inputbox" name="firstName" size="18" autocomplete="off" value="${bean.firstName}">
								<p class="help-block"><fmt:message key="registration.restrict.name"/></p>
							</div>
							<div class="col-md-6">
								<label for="lastName" id="lastNameLabel"><fmt:message key="registration.lastName"/>*</label> 
								<input type="text" id="lastName" class="inputbox" name="lastName" size="18" autocomplete="off" value="${bean.lastName}">
								<p class="help-block"><fmt:message key="registration.restrict.name"/></p>
							</div>
							<div class="col-md-6">
								<label for="email" id="emailLabel"><fmt:message key="email"/>*</label> 
								<input type="text" id="email" class="inputbox" name="email" size="18" autocomplete="off" value="${bean.email}">
							</div>
							<div class="col-md-6" >
								<label for="avatar" id="avatarLabel"><fmt:message key="registration.avatar"/></label> 
			                    <input id="avatar" type="file" class="button" name="avatar">
							</div>
						</div>
						<div class="row">
							<br>
							<h3><fmt:message key="registration.loginInfo"/></h3>
						</div>
							
						<div class="row">
							<div class="col-md-6">
								<label for="password" id="passwordLabel"><fmt:message key="password"/>*</label> 
								<input type="password" id="password" class="inputbox" name="password" size="18" autocomplete="off">
								<p class="help-block"><fmt:message key="registration.restrict.password"/></p>
							</div>
							<div class="col-md-6">
								<label for="passwordConfirmation" id="passwordConfirmationLabel"><fmt:message key="confirmPassword"/>*</label> 
								<input type="password" class="inputbox"  id="passwordConfirmation" name="passwordConfirmation" autocomplete="off">
							</div>
						</div>
						<div class="row">
							<webshop:captcha image="${image}" idCaptcha="${idCaptcha}" useHidden="${useHidden}"/>
						</div>
						<div class="row">
						<br>
							<br>
							<input type="submit" name="submit" class="button" value="Sign up">
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
	<%@ include file="/WEB-INF/fragment/scripts.jspf"%>
</body>
</html>