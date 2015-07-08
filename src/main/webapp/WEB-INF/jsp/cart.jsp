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
				<c:choose>
					<c:when test="${cart ne null && cart.amount ne 0 }">
						<div id="order-detail-content" class="col-md-12">
							<table id="cart_summary"
								class="table table-bordered">
								<thead>
									<tr>
										<th>Product</th>
										<th>Description</th>
										<th>Unit price</th>
										<th>Qty</th>
										<th>Total</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${cart.products }" var="entry">
									<tr id="row${entry.key.id }">
										<td>
											<div class="col-md-6 shop_box">
												<img src='<c:url value="/media?path=${entry.key.picturePath }"></c:url>' class="img-responsive" alt="" />
											</div>
										</td>
										<td>
											<div class="">
												<h3>
													 ${entry.key.brand} ${entry.key.model}
												</h3>
												<p>Rocker type: ${entry.key.rockerType}</p>
												<p>Flex: ${entry.key.flex}</p>
											</div>
										</td>
										<td data-title="Unit price"> 
											<span >$${entry.key.price}</span>
											<input type="hidden" id="price${entry.key.id }" value="${entry.key.price}">
										</td>
										<td>
											<input type="number" min="0"  value="${entry.value}" name="quantity" id="amount${entry.key.id }">
											<span><i class="fa fa-refresh fa-2x pointer" onclick="updateProductInCart(${entry.key.id });"></i></span>
										</td>
										<td data-title="Total">
											<span id="productPrice${entry.key.id }" > $${entry.value * entry.key.price}</span>
										</td>
										<td class="text-center" data-title="Delete">
											<div>
												<i class="fa fa-trash-o fa-3x pointer" onclick="removeFromCart(${entry.key.id});"></i>
											</div>
										</td>
									</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr >
		
										<td colspan="5" class="text-right">Total</td>
										<td colspan="2" id="cartPrice">$${cart.price}</td>
									</tr>
								</tfoot>
							</table>
						</div>
						<div class="row">
							<div class="col-md-1 col-md-offset-10" ><a class="button-black" href='<c:url value="/clear-cart"></c:url>'>Clear</a></div>
							<div class="col-md-1"><a class="button-black" href='<c:url value="/checkout"></c:url>'>Checkout</a></div>
						</div>
					</c:when>
					<c:otherwise><div class="row"><h1>Shopping cart is empty.</h1></div></c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
	<%@ include file="/WEB-INF/fragment/scripts.jspf"%>
</body>
</html>