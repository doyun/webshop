package com.epam.doiun.filter.localization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.doiun.constants.ApplicationConstants;

/**
 * Servlet Filter implementation class LocalizationFilter
 */
public class LocalizationFilter implements Filter {

	private static final Logger LOGGER = Logger
			.getLogger(LocalizationFilter.class);

	private List<Locale> languages;
	private List<Locale> availibleLocales;
	private Locale defaultLocale;
	private String saveType;
	private Integer cookieLifeTime;

	public LocalizationFilter() {
		languages = new ArrayList<Locale>();
		availibleLocales = new ArrayList<Locale>();
	}

	public void init(FilterConfig fConfig) throws ServletException {
		for (String s : Arrays.asList(Locale.getISOLanguages())) {
			languages.add(new Locale(s));
		}
		for (String s : extractLocales(fConfig
				.getInitParameter(ApplicationConstants.LOCALES.getValue()))) {
			Locale locale = new Locale(s);
			if (languages.contains(locale)) {
				availibleLocales.add(locale);
			}
		}
		defaultLocale = new Locale(
				fConfig.getInitParameter(ApplicationConstants.DEFAULT_LOCALE
						.getValue()));
		if (!languages.contains(defaultLocale)) {
			defaultLocale = new Locale("en");
		}
		if (availibleLocales.isEmpty()) {
			availibleLocales.add(defaultLocale);
		}
		saveType = fConfig.getInitParameter(ApplicationConstants.SAVE_TYPE
				.getValue());
		try {
			cookieLifeTime = Integer.valueOf(fConfig
					.getInitParameter(ApplicationConstants.COOKIE_LIFE_TIME
							.getValue()));
		} catch (NumberFormatException e) {
			LOGGER.error("Failed to parse cookyLifeType parameter.");
			cookieLifeTime = 30 * 60;
		}
		fConfig.getServletContext().setAttribute(
				ApplicationConstants.AVAILIBLE_LOCALES.getValue(),
				availibleLocales);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Localization filter initialized. Default locale = "
					+ defaultLocale + ". Availible locales " + availibleLocales
					+ ". Save type = " + saveType + ". Cookie life time"
					+ cookieLifeTime);
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		Locale locale = new RequestParamFilter(new SessionFilter(
				new BrowserAcceptedLanguagesFilter(new DefaultLocaleFilter()),
				saveType, cookieLifeTime, httpRes), saveType, cookieLifeTime,
				httpRes).doFilter(httpReq, availibleLocales, defaultLocale,
				languages);
		chain.doFilter(new LocalizationServletRequestWrapper(httpReq, locale),
				response);
	}

	private List<String> extractLocales(String in) {
		List<String> list = new ArrayList<String>();
		Scanner scanner = new Scanner(in);
		while (scanner.hasNext()) {
			String locale = scanner.next();
			if (languages.contains(new Locale(locale))) {
				list.add(locale);
			}
		}
		scanner.close();
		return list;
	}
}
