package com.epam.doiun.entity;

import java.math.BigDecimal;
import java.util.List;

public class ProductFilterDTO {

	private List<Integer> brands;
	private BigDecimal priceFrom;
	private BigDecimal priceTo;
	private List<Integer> rockerTypes;
	private List<Integer> flex;
	private Integer limit;
	private Integer page;
	private String orderBy;
	private String orderType;
	private String searchString;

	/**
	 * @return the brands
	 */
	public List<Integer> getBrands() {
		return brands;
	}

	/**
	 * @param brands
	 *            the brands to set
	 */
	public void setBrands(List<Integer> brands) {
		this.brands = brands;
	}

	/**
	 * @return the priceFrom
	 */
	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	/**
	 * @param priceFrom
	 *            the priceFrom to set
	 */
	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	/**
	 * @return the priceTo
	 */
	public BigDecimal getPriceTo() {
		return priceTo;
	}

	/**
	 * @param priceTo
	 *            the priceTo to set
	 */
	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	/**
	 * @return the rockerTypes
	 */
	public List<Integer> getRockerTypes() {
		return rockerTypes;
	}

	/**
	 * @param rockerTypes
	 *            the rockerTypes to set
	 */
	public void setRockerTypes(List<Integer> rockerTypes) {
		this.rockerTypes = rockerTypes;
	}

	/**
	 * @return the flex
	 */
	public List<Integer> getFlex() {
		return flex;
	}

	/**
	 * @param flex
	 *            the flex to set
	 */
	public void setFlex(List<Integer> flex) {
		this.flex = flex;
	}

	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy
	 *            the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return searchString;
	}

	/**
	 * @param searchString
	 *            the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public boolean containsBrand(Integer brand) {
		return brands.contains(String.valueOf(brand));
	}

	public boolean containsFlex(Integer flex) {
		return this.flex.contains(String.valueOf(flex));
	}

	public boolean containsRocker(Integer rocker) {
		return rockerTypes.contains(String.valueOf(rocker));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductFilterDTO [brands=" + brands + ", priceFrom="
				+ priceFrom + ", priceTo=" + priceTo + ", rockerTypes="
				+ rockerTypes + ", flex=" + flex + ", limit=" + limit
				+ ", page=" + page + ", orderBy=" + orderBy + ", orderType="
				+ orderType + ", searchString=" + searchString + "]";
	}

}
