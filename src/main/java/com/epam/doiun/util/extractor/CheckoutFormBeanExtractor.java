package com.epam.doiun.util.extractor;

import javax.servlet.http.HttpServletRequest;

import com.epam.doiun.bean.CheckoutFormBean;

public class CheckoutFormBeanExtractor {

	public static CheckoutFormBean getCheckoutFormBeanFromRequest(
			HttpServletRequest request) {
		CheckoutFormBean bean = new CheckoutFormBean();
		bean.setPaymentType(request.getParameter("paymentType").trim());
		bean.setDeliveryType(request.getParameter("deliveryType").trim());
		bean.setCreditCardNumber(request.getParameter("creditCardNumber").trim());
		bean.setCardHolderName(request.getParameter("cardHolderName").trim());
		bean.setExperetionDate(request.getParameter("experetionDateMonth") + "/" + request.getParameter("experetionDateYear"));
		bean.setCvv(request.getParameter("cvv").trim());
		bean.setAddress(request.getParameter("address").trim());
		return bean;
	}
}
