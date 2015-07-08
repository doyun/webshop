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
						<c:if test="${bean.status != null}">
							<div class="alert alert-danger alert-dismissible" role="alert">
		 						    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							    ${bean.status}
							</div>
						</c:if>
						Order id: ${order.id}
						<%@ include file="/WEB-INF/fragment/checkoutTable.jspf" %>
						<form id="checkoutForm" method="POST" onsubmit="return validateCheckoutFormjQuery()">
							<div class="col-md-6 col-md-offset-2">
								<label for="paymentType" id="paymentTypeLabel">Payment type:</label><p>
								<select name="paymentType" id="paymentType" onchange="onPaymentTypeChange();">
									<option value="card" ${(bean.paymentType eq 'card') ? 'selected' :  ''}  >Card</option>
									<option value="cash" ${(bean.paymentType eq 'cash') ? 'selected' :  ''}>Cash</option>
								</select>
							</div>
							<div id="card">
								<div class="col-md-6 col-md-offset-2">
									<label for="creditCardNumber" id="creditCardNumberLabel">Credit card number*</label> 
									<input type="text" id="creditCardNumber" class="inputbox" name="creditCardNumber" size="18" autocomplete="off" value="${bean.creditCardNumber }">
								</div>
								<div class="col-md-6 col-md-offset-2">
									<label for="cardHolderName" id="cardHolderNameLabel">Cardholder name*</label> 
									<input type="text" class="inputbox"  id="cardHolderName" name="cardHolderName" autocomplete="off" value="${bean.cardHolderName }">
								</div>
								<div class="col-md-6 col-md-offset-2">
									<label for="experetionDate" id="experetionDateLabel">Experetion date*</label>
									<p> 
									<input type="text" class=""  id="experetionDateMonth" name="experetionDateMonth" size=2>/
									<input type="text" class=""  id="experetionDateYear" name="experetionDateYear" size=2>
								</div>
								<div class="col-md-6 col-md-offset-2">
									<label for="cvv" id="cvvLabel">CVV(Card Verification Value)*</label> 
									<input type="text" class="inputbox"  id="cvv" name="cvv" autocomplete="off" value="${bean.cvv }">
								</div>
							</div>
							<div class="col-md-6 col-md-offset-2">
								<label for="deliveryType" id="deliveryTypeLabel">Delivery type:</label><p>
								<select name="deliveryType" id="deliveryType" >
									<option value="address" ${(bean.paymentType eq 'address') ? 'selected' :  ''}>To address</option>
									<option value="point" ${(bean.paymentType eq 'point') ? 'selected' :  ''}>From delivery point</option>
								</select>
							</div>
							<div class="col-md-6 col-md-offset-2" id="addressDiv">
								<label for="address" id="addressLabel">Address*</label> 
								<input type="text" class="inputbox"  id="address" name="address" autocomplete="off" value="${bean.address}">
							</div>
							<div class="col-md-6 col-md-offset-2" id="addressDiv">
								<input type="submit" name="submit" class="button-black"  value="Next">
								<a class="button-black" onclick="goBack();">Back</a>
							</div>
						</form>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
	<%@ include file="/WEB-INF/fragment/scripts.jspf"%>
</body>
</html>