package com.epam.doiun.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localizator {

	
	public static String getString(Locale locale, String key){
		ResourceBundle rb = ResourceBundle.getBundle("l10n", locale);
		return rb.getString(key);
	}
}
