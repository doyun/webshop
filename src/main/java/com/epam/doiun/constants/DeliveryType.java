package com.epam.doiun.constants;

public enum DeliveryType {
	ADDRESS("address"),
	POINT("point");

	private String value;

	private DeliveryType(String value) {
		this.value = value;
	}

	/**
	 * @return the path
	 */
	public String getValue() {
		return value;
	}

}
