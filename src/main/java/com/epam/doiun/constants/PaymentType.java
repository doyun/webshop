package com.epam.doiun.constants;

public enum PaymentType {
	CASH("cash"),
	CARD("card");

	private String value;

	private PaymentType(String value) {
		this.value = value;
	}

	/**
	 * @return the path
	 */
	public String getValue() {
		return value;
	}

}
