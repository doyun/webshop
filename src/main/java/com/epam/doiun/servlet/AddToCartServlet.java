package com.epam.doiun.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.entity.Cart;
import com.epam.doiun.services.ProductService;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute(
				ApplicationConstants.CART.getValue());
		if (cart == null) {
			cart = new Cart((ProductService) getServletContext().getAttribute(
					ApplicationConstants.PRODUCT_SERVICE.getValue()));
			request.getSession().setAttribute(
					ApplicationConstants.CART.getValue(), cart);
		}
		int id = Integer.valueOf(request.getParameter("id"));
		String amount = request.getParameter("amount");
		if (amount == null) {
			amount = "1";
		}
		cart.addProduct(id, Integer.valueOf(amount));
		
		response.setContentType("application/json");
		JSONObject amountJSON = new JSONObject();
		amountJSON.put("amount", cart.getAmount());
		amountJSON.put("price", cart.getPrice());
		response.getWriter().write(amountJSON.toString());
	}

}
