package com.epam.doiun.constants;

public enum ValidationStatus {
	
	OK("OK"),
	INCORRECT_NAME("incorrectName"),
	INCORRECT_EMAIL("incorrectEmail"),
	INCORRECT_PASSWORD("incorrectPassword"),
	INCORRECT_PASSWORD_CONFIRMATION("incorrectPasswordConfirmation"),
	INCORRECT_LOGIN_DATA("incorrectLoginInfo"),
	USER_ALREADY_EXISTS("userAlreadyExists"),
	INCORRECT_CAPTCHA("incorrectCaptcha"),
	INCORRECT_CARD_NUMBER("incorrectCardNumber"),
	INCORRECT_CARD_HOLDER_NAME("incorrectCardHolderName"),
	INCORRECT_EXPERETION_DATE("incorrectExperetionDate"),
	INCORRECT_CVV("incorrectCvv"),
	INCORRECT_ADDRESS("incorrectAddress"),
	INCORRECT_DELIVERY_TYPE("incorrectDeliveryType"), 
	INCORRECT_PAYMENT_TYPE("incorrectPaymentType");
	
	private String message;
	
	private ValidationStatus(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return "validationStatus." + message;
	}
}	
