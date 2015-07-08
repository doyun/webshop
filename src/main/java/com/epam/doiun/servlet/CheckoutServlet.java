package com.epam.doiun.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.bean.Beans;
import com.epam.doiun.bean.CheckoutFormBean;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.JSPPages;
import com.epam.doiun.constants.PaymentType;
import com.epam.doiun.constants.URLMapping;
import com.epam.doiun.constants.ValidationStatus;
import com.epam.doiun.entity.Cart;
import com.epam.doiun.entity.Order;
import com.epam.doiun.entity.User;
import com.epam.doiun.services.OrderService;
import com.epam.doiun.services.ProductService;
import com.epam.doiun.util.extractor.CheckoutFormBeanExtractor;
import com.epam.doiun.util.validator.CheckoutFormBeanValidator;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductService productService;
	private OrderService orderService;

	@Override
	public void init() throws ServletException {
		productService = (ProductService) getServletContext().getAttribute(
				ApplicationConstants.PRODUCT_SERVICE.getValue());
		orderService = (OrderService) getServletContext().getAttribute(
				ApplicationConstants.ORDER_SERVICE.getValue());
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute(
				ApplicationConstants.CART.getValue());
		if (cart == null || cart.getAmount() == 0) {
			cart = new Cart(productService);
			request.getSession().setAttribute(
					ApplicationConstants.CART.getValue(), cart);
			response.sendRedirect(URLMapping.SHOP.getPath());
			return;
		}
		Order order = (Order) request.getSession().getAttribute(
				ApplicationConstants.ORDER.getValue());
		if (order == null) {
			order = orderService.createOrder(
					cart.getProducts(),
					(User) request.getSession().getAttribute(
							ApplicationConstants.USER.getValue()));
		} else {
			orderService.updateOrderItems(order, cart.getProducts());
		}

		request.getSession().setAttribute("order", order);
		request.setAttribute(
				"bean",
				request.getSession().getAttribute(
						Beans.CHECKOUT_FORM_BEAN.toString()));
		request.getRequestDispatcher(JSPPages.CHECKOUT.getPath()).forward(
				request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CheckoutFormBean bean = CheckoutFormBeanExtractor
				.getCheckoutFormBeanFromRequest(request);
		if (!bean.getPaymentType().equals(PaymentType.CARD.getValue())) {
			bean.cleanCardInfo();
		}
		ValidationStatus vs = CheckoutFormBeanValidator.validate(bean);
		request.getSession().setAttribute(Beans.CHECKOUT_FORM_BEAN.toString(),
				bean);
		if (vs == ValidationStatus.OK) {
			orderService.updateWithBillingInfo((Order) request.getSession()
					.getAttribute(ApplicationConstants.ORDER.getValue()), bean);
			response.sendRedirect(URLMapping.CHECKOUT_CONFIRMATION.getPath());
		} else {
			bean.setStatus(vs.getMessage());
			response.sendRedirect(URLMapping.CHECKOUT.getPath());
		}
	}
}
