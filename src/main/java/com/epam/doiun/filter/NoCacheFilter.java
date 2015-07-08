package com.epam.doiun.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class NoCacheFilter
 */
public class NoCacheFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(NoCacheFilter.class);

	public void init(FilterConfig fConfig) throws ServletException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("NoCacheFilter initialized.");
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Request URL " + httpRequest.getRequestURI());
		}
		httpResponse.setHeader("Pragma",
				"private, no-store, no-cache, must-revalidate");
		httpResponse.setHeader("Cache-Control", "no-cache");
		chain.doFilter(request, response);

	}

}
