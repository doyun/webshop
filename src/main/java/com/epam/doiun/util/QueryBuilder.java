package com.epam.doiun.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.doiun.exception.DAOException;

public class QueryBuilder {

	private static final Logger LOGGER = Logger.getLogger(QueryBuilder.class);
	private StringBuilder sb;
	private static List<String> orderTypes;
	private static HashMap<String, String> columnsToOrderBy;


	static {
		orderTypes = new ArrayList<String>();
		orderTypes.add("ASC");
		orderTypes.add("DESC");
		columnsToOrderBy = new HashMap<String, String>();
		columnsToOrderBy.put("brand", "brand.name");
		columnsToOrderBy.put("model", "product.model");
		columnsToOrderBy.put("price", "product.price");
	}

	public QueryBuilder(String sqlQuery) {
		sb = new StringBuilder(sqlQuery);
	}

	public QueryBuilder like(String column, ConditionType type) {
		sb.append(type.getValue()).append(column).append(" LIKE ?");
		return this;
	}

	public QueryBuilder in(String column, int number, ConditionType type) {
		if (number != 0) {
			sb.append(type.getValue()).append(column).append(" IN (");
			for (int i = 0; i < number - 1; i++) {
				sb.append("?, ");
			}
			sb.append("?)");
		}
		return this;
	}

	public QueryBuilder greaterOrEqual(String column, ConditionType type) {
		sb.append(type.getValue()).append(column).append(" >= ?");
		return this;
	}

	public QueryBuilder underOrEqual(String column, ConditionType type) {
		sb.append(type.getValue()).append(column).append(" <= ?");
		return this;
	}

	public QueryBuilder orderBy(String column, String defaultColumn, String type)
			throws DAOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("orderBy " + column + " " + defaultColumn + " " + type);
		}
		if (columnsToOrderBy.containsKey(column)) {
			column = columnsToOrderBy.get(column);
		} else {
			column = defaultColumn;
		}
		if (!orderTypes.contains(type)) {
			type = "ASC";
		}
		sb.append(" ORDER BY ").append(column).append(" ").append(type);
		return this;
	}

	public QueryBuilder limit() {
		sb.append(" LIMIT ?");
		return this;
	}

	public QueryBuilder offset() {
		sb.append(" OFFSET ?");
		return this;
	}

	public String build() {
		return sb.toString();
	}

	public enum ConditionType {
		AND(" AND "), OR(" OR "), NO_CONDITION(" ");
		
		private String value;
		
		private ConditionType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
}
