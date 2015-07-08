package com.epam.doiun.util.validator;

import java.util.regex.Pattern;

import com.epam.doiun.bean.LoginBean;
import com.epam.doiun.constants.RegExp;
import com.epam.doiun.constants.ValidationStatus;
import com.epam.doiun.services.UserService;

public class LoginBeanValidator {

	private static Pattern EMAIL_PATTERN = Pattern.compile(RegExp.EMAIL
			.getPattern());
	private static Pattern PASSWORD_PATTERN = Pattern.compile(RegExp.PASSWORD
			.getPattern());

	public static ValidationStatus validate(LoginBean bean, UserService service) {

		if (bean.getEmail() == null
				|| !EMAIL_PATTERN.matcher(bean.getEmail()).matches()) {
			return ValidationStatus.INCORRECT_EMAIL;
		}
		if (bean.getPassword() == null
				|| !PASSWORD_PATTERN.matcher(bean.getPassword()).matches()) {
			return ValidationStatus.INCORRECT_PASSWORD;
		}
		if (!service.isRightLoginInfo(bean)) {
			return ValidationStatus.INCORRECT_LOGIN_DATA;
		}
		return ValidationStatus.OK;
	}
}
