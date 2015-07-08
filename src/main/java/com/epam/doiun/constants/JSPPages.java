package com.epam.doiun.constants;

public enum JSPPages {
	INDEX("/WEB-INF/jsp/index.jsp"),
	REGISTER("/WEB-INF/jsp/register.jsp"),
	LOGIN("/WEB-INF/jsp/login.jsp"),
	SHOP("/WEB-INF/jsp/shop.jsp"),
	CART("/WEB-INF/jsp/cart.jsp"),
	CHECKOUT("/WEB-INF/jsp/checkout.jsp"), 
	CHECKOUT_CONFIRM("/WEB-INF/jsp/checkoutConfirmation.jsp");

	private String path;

	private JSPPages(String path) {
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

}
