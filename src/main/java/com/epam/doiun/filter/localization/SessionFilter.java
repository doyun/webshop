package com.epam.doiun.filter.localization;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.doiun.constants.ApplicationConstants;

public class SessionFilter extends Filter {

	private String saveType;
	private HttpServletResponse response;
	private Integer cookieLifeTime;

	public SessionFilter(Filter filter, String saveType,
			Integer cookieLifeTime, HttpServletResponse response) {
		super(filter);
		this.saveType = saveType;
		this.response = response;
		this.cookieLifeTime = cookieLifeTime;
	}

	@Override
	public Locale getLocale(HttpServletRequest req,
			List<Locale> availibleLocales, Locale defaultLocale,
			List<Locale> languages) {
		if (saveType.equals("cookie")) {
			for (Cookie cookie : req.getCookies()) {
				if (cookie.getName().equals(
						ApplicationConstants.LOCALE.getValue())) {
					Cookie newCookie = new Cookie(
							ApplicationConstants.LOCALE.getValue(),
							cookie.getValue());
					cookie.setMaxAge(cookieLifeTime);
					response.addCookie(newCookie);
					return new Locale(cookie.getValue());
				}
			}
		} else {
			HttpSession session = req.getSession();
			if (session.getAttribute(ApplicationConstants.LOCALE.getValue()) != null) {
				return (Locale) session
						.getAttribute(ApplicationConstants.LOCALE.getValue());
			}
		}
		return null;
	}

}
