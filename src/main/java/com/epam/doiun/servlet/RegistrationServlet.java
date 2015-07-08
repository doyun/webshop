package com.epam.doiun.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.doiun.bean.Beans;
import com.epam.doiun.bean.RegistrationFormBean;
import com.epam.doiun.captcha.AbstractCaptchaManager;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.JSPPages;
import com.epam.doiun.constants.URLMapping;
import com.epam.doiun.constants.ValidationStatus;
import com.epam.doiun.services.UserService;
import com.epam.doiun.util.extractor.RegistrationFormBeanExtractor;
import com.epam.doiun.util.validator.RegistrationFormBeanValidator;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 15)
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AbstractCaptchaManager captchaManager;
	private UserService service;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		captchaManager = (AbstractCaptchaManager) context
				.getAttribute(ApplicationConstants.CAPTCHA_MANAGER.getValue());
		service = (UserService) context
				.getAttribute(ApplicationConstants.USER_SERVICE.getValue());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(isLoggedIn(session)){
			response.sendRedirect(URLMapping.INDEX.getPath());
			return;
		}
		if(isLoggedIn(session)){
			response.sendRedirect(URLMapping.INDEX.getPath());
			return;
		}
		request.setAttribute("bean",
				session.getAttribute(Beans.REGISTRATION_FORM_BEAN.toString()));
		session.removeAttribute(Beans.REGISTRATION_FORM_BEAN.toString());
		request.setAttribute("image",
				captchaManager.generate(request, response));
		request.setAttribute("disableLoginInfo", true);
		request.getRequestDispatcher(JSPPages.REGISTER.getPath()).forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(isLoggedIn(request.getSession())){
			response.sendRedirect(URLMapping.INDEX.getPath());
			return;
		}
		RegistrationFormBean bean = RegistrationFormBeanExtractor
				.getBeanFromRequest(request);
		ValidationStatus vs = RegistrationFormBeanValidator.validate(bean,
				service);
		ValidationStatus captchaVS = captchaManager.isValid(request);

		if (vs == ValidationStatus.OK && captchaVS == ValidationStatus.OK) {
			service.addUser(bean);
			response.sendRedirect(URLMapping.INDEX.getPath());
		} else {
			bean.setPassword("");
			bean.setPasswordConfirmation("");
			bean.setStatus(((vs != ValidationStatus.OK) ? vs : captchaVS)
					.getMessage());
			request.getSession().setAttribute(
					Beans.REGISTRATION_FORM_BEAN.toString(), bean);
			response.sendRedirect(URLMapping.REGISTER.getPath());
		}
	}

	private boolean isLoggedIn(HttpSession session) {
		return session.getAttribute(ApplicationConstants.USER.getValue()) != null;
	}
}
