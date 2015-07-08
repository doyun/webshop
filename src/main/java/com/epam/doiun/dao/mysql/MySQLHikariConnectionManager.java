package com.epam.doiun.dao.mysql;

import static com.epam.doiun.dao.mysql.MySQLDatabaseConfig.getDatabase;
import static com.epam.doiun.dao.mysql.MySQLDatabaseConfig.getPassword;
import static com.epam.doiun.dao.mysql.MySQLDatabaseConfig.getPort;
import static com.epam.doiun.dao.mysql.MySQLDatabaseConfig.getServer;
import static com.epam.doiun.dao.mysql.MySQLDatabaseConfig.getTransactionIsolation;
import static com.epam.doiun.dao.mysql.MySQLDatabaseConfig.getUser;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.epam.doiun.constants.ExceptionMessage;
import com.epam.doiun.dao.ConnectionManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class MySQLHikariConnectionManager implements ConnectionManager {

	private static final Logger LOGGER = Logger
			.getLogger(MySQLHikariConnectionManager.class);
	private static final boolean CACHE_PREPARE_STATEMENTS = true;
	private static final int PREPARE_STATEMENTS_CACHE_SIZE = 250;
	private static final int PREPARE_STATEMENTS_CACHE_SQL_LIMIT = 2048;
	private static final boolean USE_SERVER_PREPARE_STATEMENTS = true;

	private HikariDataSource dataSource;

	/**
	 * Instantiates a new manager.
	 */
	public MySQLHikariConnectionManager() {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.setInitializationFailFast(true);
		config.addDataSourceProperty("serverName", getServer());
		config.addDataSourceProperty("port", getPort());
		config.addDataSourceProperty("databaseName", getDatabase());
		config.addDataSourceProperty("user", getUser());
		config.addDataSourceProperty("password", getPassword());
		config.addDataSourceProperty("cachePrepStmts", CACHE_PREPARE_STATEMENTS);
		config.addDataSourceProperty("prepStmtCacheSize",
				PREPARE_STATEMENTS_CACHE_SIZE);
		config.addDataSourceProperty("prepStmtCacheSqlLimit",
				PREPARE_STATEMENTS_CACHE_SQL_LIMIT);
		config.addDataSourceProperty("useServerPrepStmts",
				USE_SERVER_PREPARE_STATEMENTS);
		config.setTransactionIsolation("TRANSACTION_"
				+ getTransactionIsolation());
		dataSource = new HikariDataSource(config);
	}

	@Override
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			LOGGER.error(ExceptionMessage.CONNECTION_GET_EXCEPTION.getMessage());
			throw new IllegalStateException(
					ExceptionMessage.CONNECTION_GET_EXCEPTION.getMessage(), e);
		}
	}

	@Override
	public void closeDataSource() {
		dataSource.shutdown();
	}
}
