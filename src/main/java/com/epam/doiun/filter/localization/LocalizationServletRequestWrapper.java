package com.epam.doiun.filter.localization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class LocalizationServletRequestWrapper extends HttpServletRequestWrapper {

	private Locale locale;
	
	public LocalizationServletRequestWrapper(ServletRequest request,
			Locale locale) {
		super((HttpServletRequest) request);
		this.locale = locale;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(locale);
		return Collections.enumeration(locales);
	}
}
