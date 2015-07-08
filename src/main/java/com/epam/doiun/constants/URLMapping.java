package com.epam.doiun.constants;

public enum URLMapping {

	REGISTER("register"),
	LOGIN("login"),
	CART("cart"),
	SHOP("shop"),
	CHECKOUT("checkout"),
	INDEX("index"), 
	CHECKOUT_CONFIRMATION("checkout-confirm");

	private String path;

	private URLMapping(String path) {
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

}
