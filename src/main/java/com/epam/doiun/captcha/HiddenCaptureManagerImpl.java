package com.epam.doiun.captcha;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.bean.CaptchaBean;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.ValidationStatus;

public class HiddenCaptureManagerImpl extends AbstractCaptchaManager {

	public HiddenCaptureManagerImpl(ServletContext context) {
		super(context);
	}

	@Override
	public void remember(CaptchaBean captcha, HttpServletRequest request,
			HttpServletResponse response) {
		String answer = captcha.getAnswer();
		request.setAttribute(ApplicationConstants.USE_HIDDEN.getValue(), true);
		request.setAttribute(ApplicationConstants.ID_CAPTCHA.getValue(),
				captcha.getAnswer().hashCode());
		getCaptchaMap().put(answer.hashCode(), captcha);
	}

	@Override
	public ValidationStatus isValid(HttpServletRequest request) {
		String captcha = (String) request
				.getParameter(ApplicationConstants.CAPTCHA.getValue());
		Integer idCaptcha = Integer.valueOf(request
				.getParameter(ApplicationConstants.ID_CAPTCHA.getValue()));
		CaptchaBean captchaBean = getCaptchaMap().remove(idCaptcha);
		boolean isValid = captcha != null && captchaBean != null
				&& captcha.equals(captchaBean.getAnswer());
		return (isValid) ? ValidationStatus.OK
				: ValidationStatus.INCORRECT_CAPTCHA;
	}
}
