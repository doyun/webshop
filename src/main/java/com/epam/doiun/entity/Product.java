package com.epam.doiun.entity;

import java.math.BigDecimal;

public class Product {

	private Integer id;
	private String brand;
	private String model;
	private String flex;
	private BigDecimal price;
	private String rockerType;
	private String picturePath;

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
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the flex
	 */
	public String getFlex() {
		return flex;
	}

	/**
	 * @param flex
	 *            the flex to set
	 */
	public void setFlex(String flex) {
		this.flex = flex;
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

	/**
	 * @return the rockerType
	 */
	public String getRockerType() {
		return rockerType;
	}

	/**
	 * @param rockerType
	 *            the rockerType to set
	 */
	public void setRockerType(String rockerType) {
		this.rockerType = rockerType;
	}

	/**
	 * @return the picturePath
	 */
	public String getPicturePath() {
		return picturePath;
	}

	/**
	 * @param picturePath
	 *            the picturePath to set
	 */
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [id=" + id + ", brand=" + brand + ", model=" + model
				+ ", flex=" + flex + ", price=" + price + ", rockerType="
				+ rockerType + ", picturePath=" + picturePath + "]";
	}

}
