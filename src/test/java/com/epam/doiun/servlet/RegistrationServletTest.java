package com.epam.doiun.servlet;

import com.epam.doiun.bean.Beans;
import com.epam.doiun.bean.CaptchaBean;
import com.epam.doiun.bean.RegistrationFormBean;
import com.epam.doiun.captcha.AbstractCaptchaManager;
import com.epam.doiun.captcha.CookieCaptureManagerImpl;
import com.epam.doiun.captcha.HiddenCaptureManagerImpl;
import com.epam.doiun.captcha.SessionCaptchaManagerImpl;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.ValidationStatus;
import com.epam.doiun.services.UserService;
import com.epam.doiun.util.extractor.FileExtractor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class RegistrationServletTest {

    private ServletContext context;

    private HttpSession session;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private HashMap<Integer, CaptchaBean> captchaMap;

    private UserService service;

    private RequestDispatcher requestDispatcher;

    private RegistrationServlet servlet;

    private Part part;

    private AbstractCaptchaManager captchaManager;

    private ArgumentCaptor<Object> attrValueCaptor;

    private ArgumentCaptor<String> attrNameCaptor;

    public RegistrationServletTest() throws ServletException, IOException {
        session = mock(HttpSession.class);
        context = mock(ServletContext.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        ServletConfig servletConfig = mock(ServletConfig.class);
        requestDispatcher = mock(RequestDispatcher.class);
        FileExtractor fileExtractor = mock(FileExtractor.class);

        captchaMap = new HashMap<>();
        service = mock(UserService.class);
        servlet = new RegistrationServlet();

        when(fileExtractor.downloadFile((HttpServletRequest) anyObject(), (Part) anyObject())).thenReturn("asdf");
        when(context.getAttribute(ApplicationConstants.USER_SERVICE.getValue()))
                .thenReturn(service);
        when(context.getAttribute(ApplicationConstants.CAPTCHA_MAP.getValue()))
                .thenReturn(captchaMap);
        when(
                context.getAttribute(ApplicationConstants.FILE_EXTRACTOR
                        .getValue())).thenReturn(fileExtractor);

        when(
                context.getInitParameter(ApplicationConstants.CAPTCHA_TIMEOUT
                        .getValue())).thenReturn("60");
        when(servletConfig.getServletContext()).thenReturn(context);

        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
        when(request.getParameter("firstName")).thenReturn("Ivan");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("password")).thenReturn("IvaN1Doe");
        when(request.getParameter("passwordConfirmation")).thenReturn(
                "IvaN1Doe");
        when(request.getParameter("email")).thenReturn("email@email.com");
        when(request.getPart("avatar")).thenReturn(part);
        doReturn(requestDispatcher).when(request).getRequestDispatcher(
                anyString());
        doNothing().when(response).sendRedirect(anyString());
        servlet.init(servletConfig);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUpBeforeMethod() throws ServletException, IOException {
        attrValueCaptor = ArgumentCaptor.forClass(Object.class);
        attrNameCaptor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    public void testSessionCaptchaManagerImplPositive()
            throws ServletException, IOException {
        captchaManager = new SessionCaptchaManagerImpl(context);
        when(
                context.getAttribute(ApplicationConstants.CAPTCHA_MANAGER
                        .getValue())).thenReturn(captchaManager);
        servlet.init();

        servlet.doGet(request, response);

        verify(session).setAttribute(attrNameCaptor.capture(),
                attrValueCaptor.capture());
        when(request.getParameter("captcha")).thenReturn(
                captchaMap.get(attrValueCaptor.getValue()).getAnswer());
        when(session.getAttribute(ApplicationConstants.ID_CAPTCHA.getValue()))
                .thenReturn(attrValueCaptor.getValue());

        servlet.doPost(request, response);
        verify(session).setAttribute(attrNameCaptor.capture(),
                attrValueCaptor.capture());
        verify(service).addUser((RegistrationFormBean) anyObject());
        assertTrue(!(attrValueCaptor.getValue() instanceof ValidationStatus));
    }

    @Test
    public void testSessionCaptchaManagerImplNegative()
            throws ServletException, IOException {
        captchaManager = new SessionCaptchaManagerImpl(context);
        when(
                context.getAttribute(ApplicationConstants.CAPTCHA_MANAGER
                        .getValue())).thenReturn(captchaManager);
        servlet.init();
        servlet.doGet(request, response);

        verify(session).setAttribute(attrNameCaptor.capture(),
                attrValueCaptor.capture());
        when(request.getParameter(ApplicationConstants.CAPTCHA.getValue()))
                .thenReturn("asdfasd");
        when(session.getAttribute(ApplicationConstants.ID_CAPTCHA.getValue()))
                .thenReturn(attrValueCaptor.getValue());

        servlet.doPost(request, response);
        verify(session).setAttribute(
                eq(Beans.REGISTRATION_FORM_BEAN.toString()),
                attrValueCaptor.capture());
        verify(service, never()).addUser((RegistrationFormBean) anyObject());
    }

    @Test
    public void testHiddenCaptchaManagerImplPositive() throws ServletException,
            IOException {
        captchaManager = new HiddenCaptureManagerImpl(context);
        when(
                context.getAttribute(ApplicationConstants.CAPTCHA_MANAGER
                        .getValue())).thenReturn(captchaManager);
        servlet.init();

        servlet.doGet(request, response);

        verify(request).setAttribute(
                eq(ApplicationConstants.ID_CAPTCHA.getValue()),
                attrValueCaptor.capture());

        when(request.getParameter(ApplicationConstants.CAPTCHA.getValue()))
                .thenReturn(
                        captchaMap.get(attrValueCaptor.getValue()).getAnswer());
        when(request.getParameter(ApplicationConstants.ID_CAPTCHA.getValue()))
                .thenReturn(String.valueOf(attrValueCaptor.getValue()));
        // is made to do assertTrue
        session.setAttribute("name", "value");
        servlet.doPost(request, response);
        verify(session).setAttribute(attrNameCaptor.capture(),
                attrValueCaptor.capture());
        verify(service).addUser((RegistrationFormBean) anyObject());
        assertTrue(!(attrValueCaptor.getValue() instanceof ValidationStatus));
    }

    @Test
    public void testHiddenCaptchaManagerImplNegative() throws ServletException,
            IOException {
        captchaManager = new HiddenCaptureManagerImpl(context);
        when(
                context.getAttribute(ApplicationConstants.CAPTCHA_MANAGER
                        .getValue())).thenReturn(captchaManager);
        servlet.init();

        servlet.doGet(request, response);

        verify(request).setAttribute(
                eq(ApplicationConstants.ID_CAPTCHA.getValue()),
                attrValueCaptor.capture());

        when(request.getParameter(ApplicationConstants.CAPTCHA.getValue()))
                .thenReturn("sadfasdf");
        when(request.getParameter(ApplicationConstants.ID_CAPTCHA.getValue()))
                .thenReturn(String.valueOf(attrValueCaptor.getValue()));

        servlet.doPost(request, response);

        verify(session).setAttribute(
                eq(Beans.REGISTRATION_FORM_BEAN.toString()),
                attrValueCaptor.capture());
        verify(service, never()).addUser((RegistrationFormBean) anyObject());
        assertEquals(((RegistrationFormBean) attrValueCaptor.getValue()).getStatus(), ValidationStatus.INCORRECT_CAPTCHA.getMessage());
    }

    @Test
    public void testCookieCaptchaManagerImplPositive() throws ServletException,
            IOException {
        captchaManager = new CookieCaptureManagerImpl(context);
        when(
                context.getAttribute(ApplicationConstants.CAPTCHA_MANAGER
                        .getValue())).thenReturn(captchaManager);
        servlet.init();
        servlet.doGet(request, response);

        verify(response).addCookie((Cookie) attrValueCaptor.capture());
        when(request.getCookies()).thenReturn(
                new Cookie[]{((Cookie) attrValueCaptor.getValue())});
        String answer = captchaMap.get(
                (Integer.valueOf(((Cookie) attrValueCaptor.getValue())
                        .getValue()))).getAnswer();
        when(request.getParameter(ApplicationConstants.CAPTCHA.getValue()))
                .thenReturn(answer);

        // is made to do assertTrue
        session.setAttribute("name", "value");
        servlet.doPost(request, response);
        verify(session).setAttribute(attrNameCaptor.capture(),
                attrValueCaptor.capture());
        verify(service).addUser((RegistrationFormBean) anyObject());
        assertTrue(!(attrValueCaptor.getValue() instanceof ValidationStatus));
    }

    @Test
    public void testCookieCaptchaManagerImplNegative() throws ServletException,
            IOException {
        captchaManager = new CookieCaptureManagerImpl(context);
        when(
                context.getAttribute(ApplicationConstants.CAPTCHA_MANAGER
                        .getValue())).thenReturn(captchaManager);
        servlet.init();
        servlet.doGet(request, response);

        verify(response).addCookie((Cookie) attrValueCaptor.capture());
        when(request.getCookies()).thenReturn(
                new Cookie[]{((Cookie) attrValueCaptor.getValue())});
        when(request.getParameter(ApplicationConstants.CAPTCHA.getValue()))
                .thenReturn("sadfasdfs");

        servlet.doPost(request, response);

        verify(session).setAttribute(
                eq(Beans.REGISTRATION_FORM_BEAN.toString()),
                attrValueCaptor.capture());
        verify(service, never()).addUser((RegistrationFormBean) anyObject());
        assertTrue(Objects.equals(((RegistrationFormBean) attrValueCaptor.getValue()).getStatus(), ValidationStatus.INCORRECT_CAPTCHA.getMessage()));
    }
}
