package com.epam.doiun.dao;

import java.sql.Connection;

public interface ConnectionManager {

	Connection getConnection();

	void closeDataSource();
}
