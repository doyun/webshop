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
					    ${bean.status}
					</div>
				</c:if>
				<div class="col-md-6">
					<div class="login-page">
						<h4 class="title">New Customers</h4>
						<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
							sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna
							aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud
							exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea
							commodo consequat. Duis autem vel eum iriure dolor in hendrerit
							in vulputate velit esse molestie consequat, vel illum dolore eu
							feugiat nulla facilisis</p>
						<div class="button1">
							<a href=<c:url value="/register"/>><input type="submit" name="Submit"
								value="Create an Account"></a>
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="login-title">
						<h4 class="title">Registered Customers</h4>
						<div id="loginbox" class="loginbox">
							<form action="" method="POST" name="loginForm" id="loginForm" onsubmit="return validateLoginFormjQuery()">
								<fieldset class="input">
									<p >
										<label for="email" id="emailLabel">Email</label> 
										<input id="email" type="text" name="email" class="inputbox" size="18" autocomplete="off" value="${bean.email}">
									</p>
									<p>
										<label for="password" id="passwordLabel">Password</label>
										<input id="password" type="password" name="password" class="inputbox" size="18" autocomplete="off">
									</p>
									<div class="remember">
										<p>
											<input type="submit" name="Submit" class="button" value="Login">
										<div class="clear"></div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
	<%@ include file="/WEB-INF/fragment/scripts.jspf"%>
</body>
</html>