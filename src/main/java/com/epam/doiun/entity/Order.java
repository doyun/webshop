package com.epam.doiun.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order implements Serializable{

	private static final long serialVersionUID = -6363003804227485474L;
	private Integer id;
	private String status;
	private String statusInfo;
	private Timestamp date;
	private Integer userId;
	private String paymentType;
	private String deliveryType;
	private String cardNumber;
	private String cardCv;
	private String cardExpDate;
	private String deliveryAddress;
	private BigDecimal price;
	private List<OrderItem> items;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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

	/**
	 * @return the statusInfo
	 */
	public String getStatusInfo() {
		return statusInfo;
	}

	/**
	 * @param statusInfo
	 *            the statusInfo to set
	 */
	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber
	 *            the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the cardCv
	 */
	public String getCardCv() {
		return cardCv;
	}

	/**
	 * @param cardCv
	 *            the cardCv to set
	 */
	public void setCardCv(String cardCv) {
		this.cardCv = cardCv;
	}

	/**
	 * @return the cardExpDate
	 */
	public String getCardExpDate() {
		return cardExpDate;
	}

	/**
	 * @param cardExpDate
	 *            the cardExpDate to set
	 */
	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}

	/**
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * @param deliveryAddress
	 *            the deliveryAddress to set
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * @return the items
	 */
	public List<OrderItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
