package com.epam.doiun.filter.localization;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class DefaultLocaleFilter extends Filter {

	public DefaultLocaleFilter() {
	}

	public DefaultLocaleFilter(Filter filter) {
		super(filter);
	}

	@Override
	public Locale getLocale(HttpServletRequest req,
			List<Locale> availibleLocales, Locale defaultLocale,
			List<Locale> languages) {
		return defaultLocale;
	}

}
