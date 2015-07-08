package com.epam.doiun.util.extractor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.epam.doiun.bean.RegistrationFormBean;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.ExceptionMessage;

public class RegistrationFormBeanExtractor {

	private static final Logger LOGGER = Logger
			.getLogger(RegistrationFormBeanExtractor.class);

	public static RegistrationFormBean getBeanFromRequest(HttpServletRequest request) {
		RegistrationFormBean bean = new RegistrationFormBean();
		bean.setEmail(request.getParameter("email").trim());
		bean.setFirstName(request.getParameter("firstName").trim());
		bean.setLastName(request.getParameter("lastName").trim());
		bean.setPassword(request.getParameter("password").trim());
		bean.setPasswordConfirmation(request.getParameter(
				"passwordConfirmation").trim());
		try {
			bean.setAvatarPath(((FileExtractor) request.getServletContext()
					.getAttribute(
							ApplicationConstants.FILE_EXTRACTOR.getValue()))
					.downloadFile(request, request.getPart("avatar")));
		} catch (IOException | ServletException e) {
			LOGGER.error(ExceptionMessage.AVATAR_LOAD_EXCEPTION.getMessage()
					+ e.getMessage());
			bean.setAvatarPath(ApplicationConstants.DEFAULT_AVATAR.getValue());
		}
		return bean;
	}
}
