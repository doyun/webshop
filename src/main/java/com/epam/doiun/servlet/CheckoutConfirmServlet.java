package com.epam.doiun.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.bean.Beans;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.JSPPages;
import com.epam.doiun.constants.OrderStatus;
import com.epam.doiun.constants.URLMapping;
import com.epam.doiun.entity.Order;
import com.epam.doiun.services.OrderService;

/**
 * Servlet implementation class CheckoutConfirmServlet
 */
@WebServlet("/checkout-confirm")
public class CheckoutConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	OrderService orderService;

	@Override
	public void init() throws ServletException {
		orderService = (OrderService) getServletContext().getAttribute(
				ApplicationConstants.ORDER_SERVICE.getValue());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Order order = (Order) request.getSession().getAttribute(
				ApplicationConstants.ORDER.getValue());
		if (order == null || request.getSession().getAttribute(
						Beans.CHECKOUT_FORM_BEAN.toString()) == null) {
			response.sendRedirect(URLMapping.SHOP.getPath());
			return;
		}
		request.setAttribute(
				"bean",	request.getSession().getAttribute(
						Beans.CHECKOUT_FORM_BEAN.toString()));
		request.getRequestDispatcher(JSPPages.CHECKOUT_CONFIRM.getPath())
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		orderService.setOrderStatus(
				(Order) request.getSession().getAttribute(
						ApplicationConstants.ORDER.getValue()),
				OrderStatus.CONFIRMED);
		request.getSession().removeAttribute(
				ApplicationConstants.ORDER.getValue());
		request.getSession().removeAttribute(
				ApplicationConstants.CART.getValue());
		request.getSession().removeAttribute(
				Beans.CHECKOUT_FORM_BEAN.toString());
		response.sendRedirect(URLMapping.SHOP.getPath());
	}

}
