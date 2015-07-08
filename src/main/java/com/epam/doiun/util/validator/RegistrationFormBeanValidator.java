package com.epam.doiun.util.validator;

import java.util.regex.Pattern;

import com.epam.doiun.bean.RegistrationFormBean;
import com.epam.doiun.constants.RegExp;
import com.epam.doiun.constants.ValidationStatus;
import com.epam.doiun.services.UserService;

public class RegistrationFormBeanValidator {

	private static Pattern EMAIL_PATTERN = Pattern.compile(RegExp.EMAIL
			.getPattern());
	private static Pattern NAME_PATTERN = Pattern.compile(RegExp.NAME
			.getPattern());
	private static Pattern PASSWORD_PATTERN = Pattern.compile(RegExp.PASSWORD
			.getPattern());

	public static ValidationStatus validate(RegistrationFormBean bean, UserService service) {

		if(service.existsUser(bean.getEmail())){
			return ValidationStatus.USER_ALREADY_EXISTS;
		}
		
		if (bean.getFirstName() == null || bean.getLastName() == null
				|| !NAME_PATTERN.matcher(bean.getFirstName()).matches()
				|| !NAME_PATTERN.matcher(bean.getLastName()).matches()) {
			return ValidationStatus.INCORRECT_NAME;
		}
		if (bean.getEmail() == null
				|| !EMAIL_PATTERN.matcher(bean.getEmail()).matches()) {
			return ValidationStatus.INCORRECT_EMAIL;
		}
		if (bean.getPassword() == null
				|| !PASSWORD_PATTERN.matcher(bean.getPassword()).matches()) {
			return ValidationStatus.INCORRECT_PASSWORD;
		}
		if (!bean.getPassword().equals(bean.getPasswordConfirmation())) {
			return ValidationStatus.INCORRECT_PASSWORD_CONFIRMATION;
		}
		return ValidationStatus.OK;
	}
}
