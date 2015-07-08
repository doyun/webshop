package com.epam.doiun.servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.JSPPages;
import com.epam.doiun.entity.ProductFilterDTO;
import com.epam.doiun.entity.ProductsDTO;
import com.epam.doiun.services.ProductService;
import com.epam.doiun.util.extractor.ProductFilterExtractor;

/**
 * Servlet implementation class ShopServlet
 */
@WebServlet("/shop")
public class ShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductService productService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		productService = (ProductService) context
				.getAttribute(ApplicationConstants.PRODUCT_SERVICE.getValue());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ProductFilterDTO filterDTO = ProductFilterExtractor
				.getFilterDTOFromRequest(request);
		int minPrice = productService.getMinPrice();
		int maxPrice = productService.getMaxPrice();
		if (filterDTO.getPriceFrom() == null) {
			filterDTO.setPriceFrom(new BigDecimal(minPrice));
			filterDTO.setPriceTo(new BigDecimal(maxPrice));
		}
		if (filterDTO.getLimit() == null) {
			request.setAttribute("limit", 12);
		}else{
			request.setAttribute("limit", filterDTO.getLimit());
		}
		request.setAttribute("filterDTO", filterDTO	);
		request.setAttribute("brands", productService.getBrands());
		request.setAttribute("flexs", productService.getFlex());
		request.setAttribute("rockerTypes", productService.getRockerTypes());
		request.setAttribute("minPrice", minPrice);
		request.setAttribute("maxPrice", maxPrice);
		request.setAttribute("priceFrom", filterDTO.getPriceFrom());
		request.setAttribute("priceTo", filterDTO.getPriceTo());
		ProductsDTO productsdDTO = productService.getProducts(filterDTO);
		request.setAttribute("products", productsdDTO.getProducts());
		request.setAttribute("productsCount", productsdDTO.getCount());
		request.getRequestDispatcher(JSPPages.SHOP.getPath()).forward(request,
				response);
	}

}
