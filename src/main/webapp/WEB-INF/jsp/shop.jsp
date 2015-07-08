<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/tags.jspf"%>
<!DOCTYPE HTML>
<html>
<webshop:head title="Free Snow Bootstrap Website Template | Register ::
	w3layouts"></webshop:head>
<body>
	<%@ include file="/WEB-INF/fragment/navbar.jspf"%>

	<div class="main">
		<div class="shop_top">
			<div class="container-fluide">
				<form method="get" id="filterForm">
					<div class="row">
						<div class="form-group col-lg-2 col-lg-offset-5">
						  <label for="orderBy"><fmt:message key="shop.sortBy"/>:</label>
						  <select class="form-control" id="orderBy" name="orderBy" onchange="submitForm();">
						    <option value="brand" <webshoptags:selected value="brand" parameter="orderBy"/> ><fmt:message key="product.brand"/></option>
						    <option value="model" <webshoptags:selected value="model" parameter="orderBy"/> ><fmt:message key="product.model"/></option>
						    <option value="price" <webshoptags:selected value="price" parameter="orderBy"/> ><fmt:message key="product.price"/></option>
						  </select>
						</div>
						<div class="form-group col-lg-2">
						  <label for="orderBy"><fmt:message key="shop.sortType"/>:</label>
						  <select class="form-control" id="orderType" name="orderType" onchange="submitForm();">
						    <option value="ASC" <webshoptags:selected value="ASC" parameter="orderType"/>><fmt:message key="shop.sortAscending"/></option>
						    <option value="DESC" <webshoptags:selected value="DESC" parameter="orderType"/>><fmt:message key="shop.sortDescending"/></option>
						  </select>
						</div>
						<div class="form-group col-lg-2">
						  <label for="limit"><fmt:message key="product.products"/>:</label>
						  <select class="form-control" id="limit" name="limit" onchange="submitForm();">
						    <option value="12" <webshoptags:selected value="12" parameter="limit"/>>12</option>
						    <option value="24" <webshoptags:selected value="24" parameter="limit"/>>24</option>
						  </select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-1"></div>
						<div class="col-md-2">
							<fieldset class="input">
								<p >
									<label for="search" id="searchLabel"><fmt:message key="shop.search"/></label> 
									<input id="search" type="text" name="search" class="inputbox" size="18" autocomplete="off" value="${filterDTO.searchString }">
								</p>
							</fieldset>
							<div><fmt:message key="product.brand"/></div>
							<c:forEach items="${brands }" var="brand">
								<label class="checkbox">
									<input type="checkbox" id="brand" name="brand" value="${brand.id }" <webshoptags:checked list="${ filterDTO.brands}" entityId="${brand.id}"/> ><i> </i>${brand.name }
								</label>
							</c:forEach>
							<div><fmt:message key="shop.priceRange"/>:</div>
	 							<input type="text" id="amount" readonly class="price-range">
	 							<input type="hidden" id="minPrice" value="${minPrice}" readonly>
	 							<input type="hidden" id="maxPrice" value="${maxPrice }" readonly>
	 							<input type="hidden" id="priceFrom" name="priceFrom" value="${priceFrom }" readonly>
	 							<input type="hidden" id="priceTo" name="priceTo" value="${priceTo }" readonly>
							<div id="slider-range"></div>
							<div><fmt:message key="product.flex"/></div>
							<c:forEach items="${flexs}" var="flex">
								<label class="checkbox">
									<input type="checkbox" id="flex" name="flex" value="${flex.id }" <webshoptags:checked list="${ filterDTO.flex}" entityId="${flex.id}"/>><i> </i>${flex.name }
								</label>
							</c:forEach>
							<div><fmt:message key="product.rockerType"/></div>
							<c:forEach items="${rockerTypes }" var="rockerType">
								<label class="checkbox">
									<input type="checkbox" id="rocker" name="rocker" value="${rockerType.id }" <webshoptags:checked list="${ filterDTO.rockerTypes}" entityId="${rockerType.id}"/>>
									<i> </i>${rockerType.name }
								</label>
							</c:forEach>
							<div class="button1">
								<input type="submit" id="submitFilter"  value='<fmt:message key="shop.filter"/>'>
						 	</div>
						</div>
						<div class="col-md-8 shop_box-top ">
							<div class="row">
							<c:if test="${ productsCount == 0}">
							 	<div class="col-md-offset-3"><h1><fmt:message key="shop.noProduct"/></h1></div>
							</c:if>
							<c:forEach items="${ products}" var="product">
								<div class="col-md-3 shop_box">
									<div class="shop_desc">
										<a href="single.html"> 
											<img src='<c:url value="/media?path=${product.picturePath }"></c:url>' class="img-responsive" alt="" style="margin: auto;"/> 
										</a>
									</div>
									<div class="shop_desc">
										<div class="brandModel">
											<h3>
												<a href="#" > ${product.brand} ${product.model}</a>
											</h3>
										</div>
										<span class="actual">${product.price} $</span><br>
										<ul class="buttons">
											<li class="cart"><a href="#" onclick="addToCart(${product.id}, 1);"><fmt:message key="shop.addToCart"/></a></li>
											<li class="shop_btn"><a href="#"><fmt:message key="shop.readMore"/></a></li>
										</ul>
											<div class="clear"></div>
									</div>
								</div>
							</c:forEach>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-offset-3">
							<webshop:paginator pages="${(productsCount + limit) / limit }" url="shop" currentPage="${(param.page eq null)?1:param.page }"></webshop:paginator>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
	<%@ include file="/WEB-INF/fragment/scripts.jspf"%>
</body>
</html>