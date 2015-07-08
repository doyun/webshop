package com.epam.doiun.filter.localization;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.constants.ApplicationConstants;

public class RequestParamFilter extends Filter {

	private String saveType;
	private Integer cookyLifeTime;
	private HttpServletResponse response;

	public RequestParamFilter(Filter filter, String saveType,
			Integer cookyLifeTime, HttpServletResponse response) {
		super(filter);
		this.saveType = saveType;
		this.cookyLifeTime = cookyLifeTime;
		this.response = response;
	}

	@Override
	public Locale getLocale(HttpServletRequest req,
			List<Locale> availibleLocales, Locale defaultLocale,
			List<Locale> languages) {
		Locale locale = null;
		String localeName = req.getParameter(ApplicationConstants.LANG
				.getValue());
		if (localeName != null) {
			locale = new Locale(localeName);
			if (availibleLocales.contains(locale)) {
				if (saveType.equals("cookie")) {
					Cookie cookie = new Cookie(
							ApplicationConstants.LOCALE.getValue(),
							locale.getLanguage());
					cookie.setMaxAge(cookyLifeTime);
					response.addCookie(cookie);
				} else {
					req.getSession().setAttribute(
							ApplicationConstants.LOCALE.getValue(), locale);
				}

			} else {
				locale = null;
			}
		}
		return locale;
	}

}
