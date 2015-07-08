package com.epam.doiun.util.validator;

import java.util.regex.Pattern;

import com.epam.doiun.bean.CheckoutFormBean;
import com.epam.doiun.constants.DeliveryType;
import com.epam.doiun.constants.PaymentType;
import com.epam.doiun.constants.RegExp;
import com.epam.doiun.constants.ValidationStatus;

public class CheckoutFormBeanValidator {

	private static Pattern CARD_NUMBER_PATTERN = Pattern
			.compile(RegExp.CARD_NUMBER.getPattern());
	private static Pattern CARD_HOLDER_NAME_PATTERN = Pattern
			.compile(RegExp.CARD_HOLDER_NAME.getPattern());
	private static Pattern CVV_PATTERN = Pattern.compile(RegExp.CVV
			.getPattern());
	private static Pattern ADDRESS_PATTERN = Pattern.compile(RegExp.ADDRESS
			.getPattern());
	private static Pattern EXPERETION_DATE_PATTERN = Pattern
			.compile(RegExp.EXPERETION_DATE.getPattern());

	public static ValidationStatus validate(CheckoutFormBean bean) {

		if (bean.getDeliveryType() == null
				|| !logicalXOR(
						bean.getDeliveryType().equals(
								DeliveryType.ADDRESS.getValue()),
						bean.getDeliveryType().equals(
								DeliveryType.POINT.getValue()))) {
			return ValidationStatus.INCORRECT_DELIVERY_TYPE;
		}

		if (bean.getPaymentType() == null
				|| !logicalXOR(
						bean.getPaymentType().equals(
								PaymentType.CASH.getValue()),
						bean.getPaymentType().equals(
								PaymentType.CARD.getValue()))) {
			return ValidationStatus.INCORRECT_PAYMENT_TYPE;
		}

		if (bean.getPaymentType().equals(PaymentType.CARD.getValue())) {
			if (bean.getCreditCardNumber() == null
					|| !CARD_NUMBER_PATTERN.matcher(bean.getCreditCardNumber())
							.matches()) {
				return ValidationStatus.INCORRECT_CARD_NUMBER;
			}
			if (bean.getCardHolderName() == null
					|| !CARD_HOLDER_NAME_PATTERN.matcher(
							bean.getCardHolderName()).matches()) {
				return ValidationStatus.INCORRECT_CARD_HOLDER_NAME;
			}
			if (bean.getExperetionDate() == null
					|| !EXPERETION_DATE_PATTERN.matcher(
							bean.getExperetionDate()).matches()) {
				return ValidationStatus.INCORRECT_EXPERETION_DATE;
			}
			if (bean.getCvv() == null
					|| !CVV_PATTERN.matcher(bean.getCvv()).matches()) {
				return ValidationStatus.INCORRECT_CVV;
			}
		}
		if (bean.getAddress() == null || bean.getAddress().isEmpty()
				|| !ADDRESS_PATTERN.matcher(bean.getAddress()).matches()) {
			return ValidationStatus.INCORRECT_ADDRESS;
		}

		return ValidationStatus.OK;
	}

	private static boolean logicalXOR(boolean x, boolean y) {
		return ((x || y) && !(x && y));
	}
}
