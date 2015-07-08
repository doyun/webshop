package com.epam.doiun.util.extractor;

import javax.servlet.http.HttpServletRequest;

import com.epam.doiun.bean.LoginBean;

public class LoginBeanExtractor {

	public static LoginBean getBeanFromRequest(
			HttpServletRequest request) {
		LoginBean bean = new LoginBean();
		bean.setEmail(request.getParameter("email").trim());
		bean.setPassword(request.getParameter("password").trim());
		return bean;
	}
}
