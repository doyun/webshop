package com.epam.doiun.constants;

public enum RegExp {

	EMAIL("^(?i)([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$"),
	PASSWORD("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$"),
	NAME("^[a-zA-Z-]+$"),
	CARD_NUMBER("^[0-9]{16}$"),
	CARD_HOLDER_NAME("^[a-zA-Z-]+ [a-zA-Z-]+$"),
	EXPERETION_DATE("^(0[1-9])|(1[012])/[0-9][0-9]$"),
	CVV("^[0-9]{3}$"),
	ADDRESS("^[\\.a-zA-Z0-9-, ]+$"),
	COLUMN_NAME("^([a-z]+((_)?[a-z]+)?\\.)?([a-z]+((_)?[a-z]+)?)$");
	
	private String pattern;
	
	private RegExp(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}
}
