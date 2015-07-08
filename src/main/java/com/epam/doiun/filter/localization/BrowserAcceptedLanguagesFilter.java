package com.epam.doiun.filter.localization;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class BrowserAcceptedLanguagesFilter extends Filter {

	public BrowserAcceptedLanguagesFilter(Filter filter) {
		super(filter);
	}

	@Override
	public Locale getLocale(HttpServletRequest req,
			List<Locale> availibleLocales, Locale defaultLocale,
			List<Locale> languages) {
		Enumeration<Locale> locales = req.getLocales();
		while (locales.hasMoreElements()) {
			Locale locale = locales.nextElement();
			if (availibleLocales.contains(locale)) {
				return locale;
			}
		}
		return null;
	}

}
