package com.epam.doiun.constants;

public enum ApplicationConstants {
	
	CAPTCHA_MANAGER("captchaManager"),
	CAPTCHA_MAP("captchaMap"),
	CAPTCHA("captcha"),
	ID_CAPTCHA("idCaptcha"),
	USE_HIDDEN("useHidden"),
	CAPTCHA_TIMEOUT("captchaTimeout"),
	USER_SERVICE("user_service"),
	PRODUCT_SERVICE("product_service"),
	ORDER_SERVICE("order_service"),
	AVATAR_DIR("avatar"),
	DEFAULT_AVATAR("images/default.jpg"),
	CONNECTION_MANAGER("connectionManager"),
	FILE_EXTRACTOR("fileExtractor"),
	ROOT_MEDIA_FOLDER("rootMediaFolder"),
	USER("user"),
	CART("cart"),
	STATUS("status"), 
	ORDER("order"),
	LOCALES("locales"),
	DEFAULT_LOCALE("defaultLocale"),
	LANG("lang"),
	LOCALE("locale"), 
	AVAILIBLE_LOCALES("availibleLocales"),
	SAVE_TYPE("saveType"),
	FILE("file"),
	REFERER("referer"),
	COOKIE_LIFE_TIME("cookieLifeTime");

	private String value;

	private ApplicationConstants(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
