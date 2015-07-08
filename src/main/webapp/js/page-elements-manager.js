addEventListener("load", function() {
	setTimeout(hideURLbar, 0);
}, false);
function hideURLbar() {
	window.scrollTo(0, 1);
}

$(document).ready(function() {
	$(".dropdown img.flag").addClass("flagvisibility");

	$(".dropdown dt a").click(function() {
		$(".dropdown dd ul").toggle();
	});

	$(".dropdown dd ul li a").click(function() {
		var text = $(this).html();
		$(".dropdown dt a span").html(text);
		$(".dropdown dd ul").hide();
		$("#result").html("Selected value is: " + getSelectedValue("sample"));
	});

	function getSelectedValue(id) {
		return $("#" + id).find("dt a span.value").html();
	}

	$(document).bind('click', function(e) {
		var $clicked = $(e.target);
		if (!$clicked.parents().hasClass("dropdown"))
			$(".dropdown dd ul").hide();
	});

	$("#flagSwitcher").click(function() {
		$(".dropdown img.flag").toggleClass("flagvisibility");
	});
});

$(window).load(function() {
	$("#flexiselDemo3").flexisel({
		visibleItems : 5,
		animationSpeed : 1000,
		autoPlay : true,
		autoPlaySpeed : 3000,
		pauseOnHover : true,
		enableResponsiveBreakpoints : true,
		responsiveBreakpoints : {
			portrait : {
				changePoint : 480,
				visibleItems : 1
			},
			landscape : {
				changePoint : 640,
				visibleItems : 2
			},
			tablet : {
				changePoint : 768,
				visibleItems : 3
			}
		}
	});

});

$(document).ready(
		function() {
			var min = +($("#minPrice").val());
			var max = +($("#maxPrice").val());
			$("#slider-range")
					.slider(
							{
								range : true,
								min : min,
								max : max,
								values : [ $("#priceFrom").val(),
										$("#priceTo").val() ],
								slide : function(event, ui) {
									$("#amount").val(
											"$" + ui.values[0] + " - $"
													+ ui.values[1]);
									$("#priceFrom").val(ui.values[0]);
									$("#priceTo").val(ui.values[1]);
								}
							});
			$("#amount").val(
					"$" + $("#slider-range").slider("values", 0) + " - $"
							+ $("#slider-range").slider("values", 1));
			$("#priceFrom").val($("#slider-range").slider("values", 0));
			$("#priceTo").val($("#slider-range").slider("values", 1));
		});

$.fn.equalizeHeights = function() {
	var maxHeight = this.map(function(i, e) {
		return $(e).height();
	}).get();
	return this.height(Math.max.apply(this, maxHeight));
};
$(document).ready(function() {
	$(".brandModel").equalizeHeights();
});

function submitForm() {
	document.getElementById("filterForm").submit();
}

function addToCart(id, amount) {
	event.preventDefault();
	$.ajax({
		method : "POST",
		url : "/WebShop/add-to-cart",
		data : {
			"id" : id,
			"amount" : amount
		},
		statusCode : {
			200 : function(data) {
				$("#cartBadge").text(data.amount);
			},
		}

	});
}

function removeFromCart(id) {
	$
			.ajax({
				method : "POST",
				url : "/WebShop/remove-from-cart",
				data : {
					"id" : id,
				},
				statusCode : {
					200 : function(data) {
						$("#cartBadge").text(data.amount);
						if (data.price == 0) {
							$("#cartContainer")
									.html(
											"<div class='row'><h1>Shopping cart is empty.</h1></div>");
						} else {
							$("#cartPrice").text("$" + data.price);
							$("#row" + id).hide("slow");
						}
					}
				}

			});
}

function updateProductInCart(id) {
	event.preventDefault();
	var amount = $("#amount" + id).val();
	if (amount <= 0) {
		removeFromCart(id);
	} else {
		$
				.ajax({
					method : "POST",
					url : "/WebShop/add-to-cart",
					data : {
						"id" : id,
						"amount" : amount
					},
					statusCode : {
						200 : function(data) {
							$("#cartBadge").text(data.amount);
							$("#productPrice" + id).text(
									"$"
											+ (amount * $("#price" + id).val())
													.toFixed(2));
							$("#cartPrice").text("$" + data.price);
						}
					}

				});
	}
}

function onPaymentTypeChange() {
	if ($("#paymentType").val() == 'cash') {
		$("#card").hide();
	}else{
		$("#card").show();
	}
}

$(window).load(function(){
	if ($("#paymentType").val() == 'cash') {
		$("#card").hide();
	}else{
		$("#card").show();
	}
});

function goBack(){
	window.location.href = document.referrer;
}

function changeLocale(language) {
	event.preventDefault();
	var url = location.href;
	if(url.indexOf("lang=") >= 0){
		var regEx = /([?&]lang)=([^#&]*)/g;
		var newurl = url.replace(regEx, '$1=' + language);
		location.href = newurl;
	}else{
		location.search += "&lang=" + language;
	}
}

// new UISearch(document.getElementById('sb-search'));
