package com.epam.doiun.captcha;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.bean.CaptchaBean;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.ValidationStatus;

public class CookieCaptureManagerImpl extends AbstractCaptchaManager {

	public CookieCaptureManagerImpl(ServletContext context) {
		super(context);
	}

	@Override
	public void remember(CaptchaBean captcha, HttpServletRequest request,
			HttpServletResponse response) {
		String answer = captcha.getAnswer();
		getCaptchaMap().put(answer.hashCode(), captcha);
		response.addCookie(new Cookie(ApplicationConstants.ID_CAPTCHA
				.getValue(), String.valueOf(answer.hashCode())));
	}

	@Override
	public ValidationStatus isValid(HttpServletRequest request) {
		String captcha = (String) request
				.getParameter(ApplicationConstants.CAPTCHA.getValue());
		boolean isValid = false;
		Integer idCaptcha = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(
					ApplicationConstants.ID_CAPTCHA.getValue())) {
				idCaptcha = Integer.valueOf(cookie.getValue());
				CaptchaBean captchaBean = getCaptchaMap().remove(idCaptcha);
				isValid = captcha != null && captchaBean != null
						&& captcha.equals(captchaBean.getAnswer());
			}
		}
		return (isValid) ? ValidationStatus.OK
				: ValidationStatus.INCORRECT_CAPTCHA;
	}
}
