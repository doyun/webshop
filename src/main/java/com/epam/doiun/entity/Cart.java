package com.epam.doiun.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.epam.doiun.services.ProductService;

public class Cart {

	private HashMap<Integer, Integer> products;
	private ProductService productService;

	public Cart(ProductService productService) {
		products = new HashMap<Integer, Integer>();
		this.productService = productService;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		int amount = 0;
		for (Entry<Integer, Integer> entry : products.entrySet()) {
			amount += entry.getValue();
		}
		return amount;
	}

	public void addProduct(Integer id, Integer amount) {
		if (amount > 0) {
			products.put(id, amount);
		} else if (amount <= 0) {
			products.remove(id);
		}
	}

	public HashMap<Product, Integer> getProducts() {
		HashMap<Product, Integer> result = new HashMap<Product, Integer>();
		List<Integer> ids = new ArrayList<Integer>();
		ids.addAll(products.keySet());
		if (!ids.isEmpty()) {
			List<Product> productsList = productService.getProductsById(ids);
			for (int i = 0; i < productsList.size(); i++) {
				result.put(productsList.get(i),
						products.get(productsList.get(i).getId()));
			}
		}
		return result;
	}

	public void removeProduct(Integer id) {
		products.remove(id);
	}

	public void clearCart() {
		products = new HashMap<Integer, Integer>();
	}

	public BigDecimal getPrice() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.addAll(products.keySet());
		BigDecimal sum = BigDecimal.ZERO;
		if (!ids.isEmpty()) {
			List<Product> productsList = productService.getProductsById(ids);
			for (Product product : productsList) {
				BigDecimal amount = new BigDecimal(
						products.get(product.getId()));
				sum = sum.add(product.getPrice().multiply(amount));
			}
		}
		return sum;
	}
}
