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
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.URLMapping;
import com.epam.doiun.entity.User;
import com.epam.doiun.security.SecurityManager;

/**
 * Servlet Filter implementation class AccessFilter
 */
public class AccessFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(AccessFilter.class);
	private SecurityManager securityManager;

	public AccessFilter() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
		String path = fConfig.getInitParameter(ApplicationConstants.FILE
				.getValue());
		try {
			securityManager = new SecurityManager(path);
		} catch (JAXBException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			System.exit(1);
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String url = httpRequest.getServletPath();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(url);
		}
		if (securityManager.isConstraintExisting(url)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Exist constraint for " + url);
			}
			User user = (User) httpRequest.getSession().getAttribute(
					ApplicationConstants.USER.getValue());
			if (user == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Referer" + httpRequest.getHeader("Referer"));
				}
				httpRequest.getSession().setAttribute(
						ApplicationConstants.REFERER.getValue(),
						url.substring(1));
				httpResponse.sendRedirect(URLMapping.LOGIN.getPath());
				return;
			}
			if (securityManager.isAccessAllowed(url, user.getRole())) {
				chain.doFilter(httpRequest, httpResponse);
			} else {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

		} else {
			chain.doFilter(request, response);
		}
	}
}
