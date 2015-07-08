package com.epam.doiun.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public final class OrderItem implements Serializable{

	private static final long serialVersionUID = 4756499072814828690L;
	private final Integer orderId;
	private final Integer productId;
	private final String name;
	private final BigDecimal price;
	private final Integer amount;

	public OrderItem(Integer orderId, Integer productId, String name,
			BigDecimal price, Integer amount) {
		this.orderId = orderId;
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.amount = amount;
	}

	/**
	 * @return the orderId
	 */
	public Integer getOrderId() {
		return new Integer(orderId);
	}

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return new Integer(productId);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return new BigDecimal(price.unscaledValue(), price.scale());
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return new Integer(amount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderItem [orderId=" + orderId + ", productId=" + productId
				+ ", name=" + name + ", price=" + price + ", amount=" + amount
				+ "]";
	}

}
