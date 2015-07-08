package com.epam.doiun.filter.localization;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.epam.doiun.constants.ApplicationConstants;

public class LocalizationFilterTest {

	private LocalizationFilter filter;

	private HttpSession session;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private FilterConfig fConfig;

	private ServletContext context;

	private FilterChain filterChain;

	private ArgumentCaptor<LocalizationServletRequestWrapper> localizationServletRequestWrapperCaptor;

	private ArgumentCaptor<Object> localeCaptor;

	public LocalizationFilterTest() throws IOException, ServletException {
		session = mock(HttpSession.class);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		fConfig = mock(FilterConfig.class);
		context = mock(ServletContext.class);
		filterChain = mock(FilterChain.class);

		filter = new LocalizationFilter();

		when(fConfig.getInitParameter(ApplicationConstants.LOCALES.getValue()))
				.thenReturn("en ru ch");
		when(fConfig.getInitParameter(ApplicationConstants.DEFAULT_LOCALE
						.getValue())).thenReturn("af");
		when(fConfig.getInitParameter(ApplicationConstants.SAVE_TYPE
						.getValue())).thenReturn("session");
		when(fConfig.getInitParameter(ApplicationConstants.COOKIE_LIFE_TIME
						.getValue())).thenReturn("3600");
		when(fConfig.getServletContext()).thenReturn(context);

		doNothing().when(context).setAttribute(anyString(), anyObject());

		doNothing().when(filterChain).doFilter((ServletRequest) anyObject(),
			(ServletResponse) anyObject());

		when(request.getSession()).thenReturn(session);

		List<Locale> locales = new ArrayList<Locale>();
		locales.add(new Locale("en"));
		locales.add(new Locale("ru"));

		when(request.getLocales()).thenReturn(Collections.enumeration(locales));
		doNothing().when(response).sendRedirect(anyString());

		filter.init(fConfig);
		localizationServletRequestWrapperCaptor = ArgumentCaptor
				.forClass(LocalizationServletRequestWrapper.class);
		localeCaptor = ArgumentCaptor.forClass(Object.class);
	}
	
	@Test
	public void testBrowserLanguageEnRu() throws IOException, ServletException {
		filter.doFilter(request, response, filterChain);
		verify(filterChain).doFilter((ServletRequest) 
				localizationServletRequestWrapperCaptor.capture(), eq(response));
		assertTrue("testBrowserLanguageEnRu",localizationServletRequestWrapperCaptor.
				getValue().getLocales().nextElement().getLanguage().equals("en"));
	}
	
	@Test
	public void testDefaultWithBrowserLanguageSpUk() throws IOException, ServletException {
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(new Locale("sp"));
		locales.add(new Locale("uk"));
		when(request.getLocales()).thenReturn(Collections.enumeration(locales));
		filter.doFilter(request, response, filterChain);
		verify(filterChain).doFilter((ServletRequest) 
				localizationServletRequestWrapperCaptor.capture(), eq(response));
		assertTrue("testBrowserLanguageEnRu",localizationServletRequestWrapperCaptor.
				getValue().getLocales().nextElement().getLanguage().equals("af"));
		assertTrue("testBrowserLanguageEnRu",localizationServletRequestWrapperCaptor.
				getValue().getLocales().nextElement().getLanguage().equals("af"));
	}
	
	@Test
	public void testSessionCh() throws IOException, ServletException {
		when(session.getAttribute(ApplicationConstants.LOCALE.getValue())).thenReturn(new Locale("ch"));
		filter.doFilter(request, response, filterChain);
		verify(filterChain).doFilter((ServletRequest) 
				localizationServletRequestWrapperCaptor.capture(), eq(response));
		assertTrue("testBrowserLanguageEnRu",localizationServletRequestWrapperCaptor.
				getValue().getLocales().nextElement().getLanguage().equals("ch"));
	}
	
	@Test
	public void testCookieCh() throws IOException, ServletException {
		when(fConfig.getInitParameter(ApplicationConstants.SAVE_TYPE
				.getValue())).thenReturn("cookie");
		filter.init(fConfig);
		when(request.getCookies()).thenReturn(new Cookie[]{new Cookie(ApplicationConstants.LOCALE.getValue(), "ch")});
		filter.doFilter(request, response, filterChain);
		verify(filterChain).doFilter((ServletRequest) 
				localizationServletRequestWrapperCaptor.capture(), eq(response));
		assertTrue("testBrowserLanguageEnRu",localizationServletRequestWrapperCaptor.
				getValue().getLocales().nextElement().getLanguage().equals("ch"));
	}
	
	@Test
	public void testRequestCh() throws IOException, ServletException {
		when(request.getParameter(ApplicationConstants.LANG.getValue())).thenReturn("ch");
		filter.doFilter(request, response, filterChain);
		verify(session).setAttribute(eq(ApplicationConstants.LOCALE.getValue()), localeCaptor.capture());
		verify(filterChain).doFilter((ServletRequest) 
				localizationServletRequestWrapperCaptor.capture(), eq(response));
		assertTrue("testBrowserLanguageEnRu",localizationServletRequestWrapperCaptor.
				getValue().getLocales().nextElement().getLanguage().equals("ch"));
		assertTrue("testBrowserLanguageEnRu",((Locale) localeCaptor.
				getValue()).getLanguage().equals("ch"));
	}

	@Test
	public void testIncorrectDefaultWithIncorrectBrowserLanguages() throws IOException, ServletException {
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(new Locale("iop"));
		locales.add(new Locale("pjk"));
		when(request.getLocales()).thenReturn(Collections.enumeration(locales));
		when(fConfig.getInitParameter(ApplicationConstants.DEFAULT_LOCALE
				.getValue())).thenReturn("piu");
		filter.init(fConfig);
		filter.doFilter(request, response, filterChain);
		verify(filterChain).doFilter((ServletRequest) 
				localizationServletRequestWrapperCaptor.capture(), eq(response));
		assertTrue("testBrowserLanguageEnRu",localizationServletRequestWrapperCaptor.
				getValue().getLocales().nextElement().getLanguage().equals("en"));
	}
}
