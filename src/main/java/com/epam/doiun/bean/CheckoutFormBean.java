package com.epam.doiun.bean;

public class CheckoutFormBean {

	private String paymentType;
	private String deliveryType;
	private String creditCardNumber;
	private String cardHolderName;
	private String experetionDate;
	private String cvv;
	private String address;
	private String status;

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType
	 *            the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * @param creditCardNumber
	 *            the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * @return the cardHolderName
	 */
	public String getCardHolderName() {
		return cardHolderName;
	}

	/**
	 * @param cardHolderName
	 *            the cardHolderName to set
	 */
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	/**
	 * @return the experetionDate
	 */
	public String getExperetionDate() {
		return experetionDate;
	}

	/**
	 * @param experetionDate
	 *            the experetionDate to set
	 */
	public void setExperetionDate(String experetionDate) {
		this.experetionDate = experetionDate;
	}

	/**
	 * @return the cvv
	 */
	public String getCvv() {
		return cvv;
	}

	/**
	 * @param cvv
	 *            the cvv to set
	 */
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void cleanCardInfo(){
		creditCardNumber = null;
		cardHolderName = null;
		experetionDate = null;
		cvv = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CheckoutFormBean [paymentType=" + paymentType
				+ ", deliveryType=" + deliveryType + ", creditCardNumber="
				+ creditCardNumber + ", cardHolderName=" + cardHolderName
				+ ", experetionDate=" + experetionDate + ", cvv=" + cvv
				+ ", address=" + address + ", status=" + status + "]";
	}

}
