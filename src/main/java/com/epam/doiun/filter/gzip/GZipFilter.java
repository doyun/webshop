package com.epam.doiun.filter.gzip;

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
 * Servlet Filter implementation class GZipFilter
 */
public class GZipFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(GZipFilter.class);

	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (acceptsGZipEncoding(httpRequest)) {
			GZipServletResponseWrapper gzipResponse = new GZipServletResponseWrapper(
					httpResponse);
			chain.doFilter(request, gzipResponse);
			gzipResponse.close();
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean acceptsGZipEncoding(HttpServletRequest httpRequest) {
		String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
		return acceptEncoding != null && acceptEncoding.indexOf("gzip") != -1;
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
