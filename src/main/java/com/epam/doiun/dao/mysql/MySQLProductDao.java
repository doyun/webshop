package com.epam.doiun.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.doiun.constants.ExceptionMessage;
import com.epam.doiun.dao.ConnectionHolder;
import com.epam.doiun.dao.ProductDAO;
import com.epam.doiun.entity.Brand;
import com.epam.doiun.entity.Flex;
import com.epam.doiun.entity.Product;
import com.epam.doiun.entity.ProductFilterDTO;
import com.epam.doiun.entity.RockerType;
import com.epam.doiun.exception.DAOException;
import com.epam.doiun.util.QueryBuilder;
import com.epam.doiun.util.QueryBuilder.ConditionType;

public class MySQLProductDao implements ProductDAO {

	private static final Logger LOGGER = Logger
			.getLogger(MySQLProductDao.class);
	private static final String FIND_PRODUCTS = "SELECT SQL_CALC_FOUND_ROWS product.id, product.model, product.price, product.picture, "
			+ "brand.name, flex.name, rocker_type.name"
			+ " FROM product, brand, rocker_type, flex "
			+ "WHERE product.brand = brand.id AND product.flex = flex.id AND product.rocker_type = rocker_type.id";
	private static final String COUNT_ROWS = "SELECT FOUND_ROWS();";
	private static final String FIND_BRANDS = "SELECT * FROM brand";
	private static final String FIND_FLEX = "SELECT * FROM flex";
	private static final String FIND_ROCKER_TYPES = "SELECT * FROM rocker_type";
	private static final String GET_MAX_PRICE = "SELECT MAX(price) AS price FROM product";
	private static final String GET_MIN_PRICE = "SELECT MIN(price) AS price FROM product";
	private ConnectionHolder connectionHolder;

	public MySQLProductDao(ConnectionHolder connectionHolder) {
		this.connectionHolder = connectionHolder;
	}

	@Override
	public List<Product> getProducts(ProductFilterDTO filterDTO) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> res = new ArrayList<Product>();
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(filterDTO);
			}
			String query = new QueryBuilder(FIND_PRODUCTS)
					.like("product.model", ConditionType.AND)
					.in("product.brand", filterDTO.getBrands().size(), ConditionType.AND)
					.in("product.flex", filterDTO.getFlex().size(), ConditionType.AND)
					.in("product.rocker_type",
							filterDTO.getRockerTypes().size(), ConditionType.AND)
					.greaterOrEqual("price", ConditionType.AND)
					.underOrEqual("price", ConditionType.AND)
					.orderBy(filterDTO.getOrderBy(), "brand.name",
							filterDTO.getOrderType()).limit().offset().build();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Method getProducts(), query = " + query);
			}
			pstmt = con.prepareStatement(query);
			fillPreparedStatementForProducts(pstmt, filterDTO);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				res.add(extractProduct(rs));
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to get products. " + e.getMessage());
			throw new DAOException(e);
		} finally {
			closeStatement(pstmt);
		}
		return res;
	}

	@Override
	public List<Product> getProductsById(List<Integer> ids) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> res = new ArrayList<Product>();
		try {
			String query = new QueryBuilder(FIND_PRODUCTS).in("product.id",
					ids.size(), ConditionType.AND).build();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Method getProductsById(), query = " + query);
			}
			pstmt = con.prepareStatement(query);
			setIntInPreparedStatementFromList(ids, pstmt, 1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				res.add(extractProduct(rs));
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to get products. " + e.getMessage());
			throw new DAOException(e);
		} finally {
			closeStatement(pstmt);
		}
		return res;
	}

	@Override
	public int getProductsCount() {
		Connection con = connectionHolder.get();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(COUNT_ROWS);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to get products count. " + e.getMessage());
			throw new DAOException(e);
		} finally {
			closeStatement(stmt);
		}
		return 0;
	}

	@Override
	public List<Brand> getBrands() {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Brand> res = new ArrayList<Brand>();
		try {
			pstmt = con.prepareStatement(FIND_BRANDS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Brand brand = new Brand();
				brand.setId(rs.getInt("id"));
				brand.setName(rs.getString("name"));
				res.add(brand);
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to get brands. " + e.getMessage());
			throw new DAOException(e);
		} finally {
			closeStatement(pstmt);
		}
		return res;
	}

	@Override
	public List<Flex> getFlex() {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Flex> res = new ArrayList<Flex>();
		try {
			pstmt = con.prepareStatement(FIND_FLEX);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Flex flex = new Flex();
				flex.setId(rs.getInt("id"));
				flex.setName(rs.getString("name"));
				res.add(flex);
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to get flex. " + e.getMessage());
			throw new DAOException(e);
		} finally {
			closeStatement(pstmt);
		}
		return res;
	}

	@Override
	public List<RockerType> getRockerTypes() {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RockerType> res = new ArrayList<RockerType>();
		try {
			pstmt = con.prepareStatement(FIND_ROCKER_TYPES);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RockerType rockerType = new RockerType();
				rockerType.setId(rs.getInt("id"));
				rockerType.setName(rs.getString("name"));
				res.add(rockerType);
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to get rockerTypes. " + e.getMessage());
			throw new DAOException(e);
		} finally {
			closeStatement(pstmt);
		}
		return res;
	}

	@Override
	public Integer getMinPrice() {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(GET_MIN_PRICE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getBigDecimal("price").intValue();
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to get brands. " + e.getMessage());
			throw new DAOException(e);
		} finally {
			closeStatement(pstmt);
		}
		return 0;
	}

	@Override
	public Integer getMaxPrice() {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(GET_MAX_PRICE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return rs.getBigDecimal("price").intValue();
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to get brands. " + e.getMessage());
			throw new DAOException(e);
		} finally {
			closeStatement(pstmt);
		}
		return Integer.MAX_VALUE;
	}


	private void closeStatement(Statement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			LOGGER.error("Fail to close Statement.");
			throw new DAOException(
					ExceptionMessage.CLOSE_STATEMENT_EXCEPTION_MESSAGE
							.getMessage(),
					e);
		}
	}

	private Product extractProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("product.id"));
		product.setBrand(rs.getString("brand.name"));
		product.setFlex(rs.getString("flex.name"));
		product.setModel(rs.getString("product.model"));
		product.setPicturePath(rs.getString("product.picture"));
		product.setPrice(rs.getBigDecimal("product.price"));
		product.setRockerType(rs.getString("rocker_type.name"));
		return product;
	}

	private void fillPreparedStatementForProducts(PreparedStatement pstmt,
			ProductFilterDTO filterDTO) throws NumberFormatException,
			SQLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(filterDTO);
		}
		int i = 1;
		pstmt.setString(i++, filterDTO.getSearchString() + "%");
		i = setIntInPreparedStatementFromList(filterDTO.getBrands(), pstmt, i);
		i = setIntInPreparedStatementFromList(filterDTO.getFlex(), pstmt, i);
		i = setIntInPreparedStatementFromList(filterDTO.getRockerTypes(),
				pstmt, i);
		pstmt.setBigDecimal(i++, filterDTO.getPriceFrom());
		pstmt.setBigDecimal(i++, filterDTO.getPriceTo());
		pstmt.setInt(i++, filterDTO.getLimit());
		pstmt.setInt(i++, (filterDTO.getPage() - 1) * filterDTO.getLimit());
	}

	private int setIntInPreparedStatementFromList(List<Integer> list,
			PreparedStatement pstmt, int fromIndex)
			throws NumberFormatException, SQLException {
		if (list != null) {
			for (Integer value : list) {
				pstmt.setInt(fromIndex++, value);
			}
		}
		return fromIndex;
	}

}
