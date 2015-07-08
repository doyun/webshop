package com.epam.doiun.entity;

import java.util.List;

public class ProductsDTO {

	private List<Product> products;
	private int count;

	/**
	 * @return the prducts
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param prducts
	 *            the prducts to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
