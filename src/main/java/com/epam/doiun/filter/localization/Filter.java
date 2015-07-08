package com.epam.doiun.filter.localization;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public abstract class Filter {

	private Filter next;

	protected Filter() {
	}

	public Filter(Filter filter) {
		next = filter;
	}

	protected Filter getNext() {
		return next;
	}

	public Locale doFilter(HttpServletRequest req,
			List<Locale> availibleLocales, Locale defaultLocale,
			List<Locale> languages) {
		Locale locale = getLocale(req, availibleLocales, defaultLocale, languages);
		if (locale != null) {
			return locale;
		}
		return next.doFilter(req, availibleLocales, defaultLocale, languages);
	}

	abstract public Locale getLocale(HttpServletRequest req,
			List<Locale> availibleLocales, Locale defaultLocale,
			List<Locale> languages);
}
