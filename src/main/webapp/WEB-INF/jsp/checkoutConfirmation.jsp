<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/tags.jspf"%>
<%@ include file="/WEB-INF/fragment/l10n.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
<webshop:head title="Free Snow Bootstrap Website Template | Register ::
	w3layouts"></webshop:head>
<body>
<div id="wrap">
	<%@ include file="/WEB-INF/fragment/navbar.jspf"%>
	
	<div class="main" >
		<div class="shop_top">
			<div class="container" id="cartContainer">
				<%@ include file="/WEB-INF/fragment/checkoutTable.jspf" %>
				Order id: ${order.id }
				<p>Payment type: ${order.paymentType }
				<c:if test="${bean.paymentType eq 'card' }">
					<p>Credit card number:  ${bean.creditCardNumber }
					<p>Cardholder name: ${bean.cardHolderName }
					<p>Experetion date: ${bean.experetionDate }
					<p>CVV(Card Verification Value): ${bean.cvv }
				</c:if>
				<p>Delivery type: ${order.deliveryType }
				<p>Address: ${order.deliveryAddress}
				<form method="POST">
					<input type="submit" name="submit" class="button-black"  value="Confirm">
					<a class="button-black" onclick="goBack();">Back</a>
				</form>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
	<%@ include file="/WEB-INF/fragment/scripts.jspf"%>
</body>
</html>