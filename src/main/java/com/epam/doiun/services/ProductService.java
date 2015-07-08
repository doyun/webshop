package com.epam.doiun.services;

import java.util.List;

import com.epam.doiun.entity.Brand;
import com.epam.doiun.entity.Flex;
import com.epam.doiun.entity.Product;
import com.epam.doiun.entity.ProductFilterDTO;
import com.epam.doiun.entity.ProductsDTO;
import com.epam.doiun.entity.RockerType;

public interface ProductService {

	ProductsDTO getProducts(ProductFilterDTO filterDTO);

	List<Product> getProductsById(List<Integer> ids);

	List<Brand> getBrands();

	List<RockerType> getRockerTypes();

	List<Flex> getFlex();

	Integer getMinPrice();

	Integer getMaxPrice();

}
