package com.epam.doiun.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.doiun.bean.Beans;
import com.epam.doiun.bean.LoginBean;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.JSPPages;
import com.epam.doiun.constants.URLMapping;
import com.epam.doiun.constants.ValidationStatus;
import com.epam.doiun.services.UserService;
import com.epam.doiun.util.LoginManager;
import com.epam.doiun.util.extractor.LoginBeanExtractor;
import com.epam.doiun.util.validator.LoginBeanValidator;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService service;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		service = (UserService) context
				.getAttribute(ApplicationConstants.USER_SERVICE.getValue());
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setAttribute("bean",
				session.getAttribute(Beans.LOGIN_BEAN.toString()));
		request.setAttribute("disableLoginInfo", true);
		request.setAttribute(ApplicationConstants.STATUS.getValue(),
				session.getAttribute(ApplicationConstants.STATUS.getValue()));
		session.removeAttribute(Beans.LOGIN_BEAN.toString());
		request.getRequestDispatcher(JSPPages.LOGIN.getPath()).forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LoginBean bean = LoginBeanExtractor.getBeanFromRequest(request);
		ValidationStatus vs = LoginBeanValidator.validate(bean, service);

		if (vs == ValidationStatus.OK) {
			LoginManager.login(request.getSession(), bean.getEmail(), service);
			String referer = (String) request.getSession().getAttribute(
					ApplicationConstants.REFERER.getValue());
			if (referer != null) {
				request.getSession().removeAttribute(ApplicationConstants.REFERER.getValue());
				response.sendRedirect(referer);
			} else {
				response.sendRedirect(URLMapping.INDEX.getPath());
			}
		} else {
			bean.setPassword("");
			bean.setStatus(vs.getMessage());
			request.getSession()
					.setAttribute(Beans.LOGIN_BEAN.toString(), bean);
			response.sendRedirect(URLMapping.LOGIN.getPath());
		}
	}

}
