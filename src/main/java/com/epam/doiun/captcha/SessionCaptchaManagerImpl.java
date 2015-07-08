package com.epam.doiun.captcha;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.bean.CaptchaBean;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.ValidationStatus;

public class SessionCaptchaManagerImpl extends AbstractCaptchaManager {

	public SessionCaptchaManagerImpl(ServletContext context) {
		super(context);
	}

	@Override
	public void remember(CaptchaBean captcha, HttpServletRequest request,
			HttpServletResponse response) {
		String answer = captcha.getAnswer();
		getCaptchaMap().put(answer.hashCode(), captcha);
		request.getSession().setAttribute(
				ApplicationConstants.ID_CAPTCHA.getValue(),
				captcha.getAnswer().hashCode());
	}

	@Override
	public ValidationStatus isValid(HttpServletRequest request) {
		String captcha = (String) request
				.getParameter(ApplicationConstants.CAPTCHA.getValue());
		Integer idCaptcha = (Integer) request.getSession().getAttribute(
				ApplicationConstants.ID_CAPTCHA.getValue());
		CaptchaBean captchaBean = getCaptchaMap().remove(idCaptcha);
		boolean isValid = captcha != null && captchaBean != null
				&& captcha.equals(captchaBean.getAnswer());
		request.getSession().removeAttribute(
				ApplicationConstants.ID_CAPTCHA.getValue());
		return (isValid) ? ValidationStatus.OK
				: ValidationStatus.INCORRECT_CAPTCHA;
	}

}
