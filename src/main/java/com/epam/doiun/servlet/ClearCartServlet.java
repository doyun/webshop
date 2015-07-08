package com.epam.doiun.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.URLMapping;
import com.epam.doiun.entity.Cart;
import com.epam.doiun.services.ProductService;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/clear-cart")
public class ClearCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute(
				ApplicationConstants.CART.getValue());
		if (cart == null) {
			cart = new Cart((ProductService) getServletContext().getAttribute(
					ApplicationConstants.PRODUCT_SERVICE.getValue()));
			request.getSession().setAttribute(
					ApplicationConstants.CART.getValue(), cart);
		}
		cart.clearCart();
		response.sendRedirect(URLMapping.SHOP.getPath());
	}

}
