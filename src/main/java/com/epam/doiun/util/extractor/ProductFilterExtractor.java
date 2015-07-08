package com.epam.doiun.util.extractor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epam.doiun.entity.ProductFilterDTO;

public class ProductFilterExtractor {

	public static ProductFilterDTO getFilterDTOFromRequest(
			HttpServletRequest request) {
		ProductFilterDTO filterDTO = new ProductFilterDTO();
		filterDTO.setBrands(getIntegerListFromStringArray(request
				.getParameterValues("brand")));
		filterDTO.setFlex(getIntegerListFromStringArray(request.getParameterValues("flex")));
		filterDTO
				.setPriceFrom(getBigDecimal(request.getParameter("priceFrom")));
		filterDTO.setPriceTo(getBigDecimal(request.getParameter("priceTo")));
		filterDTO.setRockerTypes(getIntegerListFromStringArray(request
				.getParameterValues("rocker")));
		filterDTO.setLimit(getInteger(request.getParameter("limit"), 12));
		filterDTO.setPage(getInteger(request.getParameter("page"), 1));
		filterDTO.setOrderBy(request.getParameter("orderBy"));
		filterDTO.setOrderType(request.getParameter("orderType"));
		String search = (request.getParameter("search") == null) ? "" : request
				.getParameter("search");
		filterDTO.setSearchString(search);
		
		

		return filterDTO;
	}

	private static List<Integer> getIntegerListFromStringArray(String[] array) {
		if (array == null) {
			return Collections.emptyList();
		}
		List<Integer> list = new ArrayList<Integer>();
		for(String s : array){
			list.add(Integer.valueOf(s));
		}
		return list;
	}

	private static Integer getInteger(String value, Integer dafaultValue) {
		if (value == null || Integer.valueOf(value) <= 0) {
			return dafaultValue;
		}
		return Integer.valueOf(value);
	}

	private static BigDecimal getBigDecimal(String value) {
		if (value == null ) {
			return null;
		}
		return new BigDecimal(value);
	}
}
