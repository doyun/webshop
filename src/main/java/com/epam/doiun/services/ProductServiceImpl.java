package com.epam.doiun.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.epam.doiun.anotation.Transactional;
import com.epam.doiun.dao.ProductDAO;
import com.epam.doiun.entity.Brand;
import com.epam.doiun.entity.Flex;
import com.epam.doiun.entity.Product;
import com.epam.doiun.entity.ProductFilterDTO;
import com.epam.doiun.entity.ProductsDTO;
import com.epam.doiun.entity.RockerType;

public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = Logger
			.getLogger(ProductServiceImpl.class);
	private ProductDAO productDAO;

	public ProductServiceImpl(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	@Transactional
	public ProductsDTO getProducts(ProductFilterDTO filterDTO) {
		ProductsDTO productsDTO = new ProductsDTO();
		productsDTO.setProducts(productDAO.getProducts(filterDTO));
		productsDTO.setCount(productDAO.getProductsCount());
		return productsDTO;
	}

	@Override
	public List<Product> getProductsById(List<Integer> ids) {
		return productDAO.getProductsById(ids);
	}

	@Override
	public List<Brand> getBrands() {
		return productDAO.getBrands();
	}

	@Override
	public List<RockerType> getRockerTypes() {
		return productDAO.getRockerTypes();
	}

	@Override
	public List<Flex> getFlex() {
		return productDAO.getFlex();
	}

	@Override
	public Integer getMinPrice() {
		return productDAO.getMinPrice();
	}

	@Override
	public Integer getMaxPrice() {
		return productDAO.getMaxPrice();
	}
}
