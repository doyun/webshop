package com.epam.doiun.dao.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.epam.doiun.exception.FileException;

public final class MySQLDatabaseConfig {

	private static final Logger LOGGER = Logger
			.getLogger(MySQLDatabaseConfig.class);
	private static final String DATABASE_CONFIG_FILE = "/db.properties";
	private static final String SERVER;
	private static final String PORT;
	private static final String DATABASE;
	private static final String RDBMS;
	private static final String USER;
	private static final String PASSWORD;
	private static final String TRANSACTION_ISOLATION;

	static {
		try (InputStream resource = MySQLDatabaseConfig.class
				.getResourceAsStream(DATABASE_CONFIG_FILE)) {
			Properties properties = new Properties();
			properties.load(resource);
			SERVER = properties.getProperty("server");
			PORT = properties.getProperty("port");
			DATABASE = properties.getProperty("database");
			RDBMS = properties.getProperty("rdbms");
			USER = properties.getProperty("user");
			PASSWORD = properties.getProperty("password");
			TRANSACTION_ISOLATION = properties
					.getProperty("transactionIsolation");
		} catch (IOException e) {
			String message = "Fail to load config file: '"
					+ DATABASE_CONFIG_FILE + "'";
			LOGGER.error(message);
			throw new FileException(message, e);
		}
	}

	public static String getServer() {
		return SERVER;
	}

	public static String getPort() {
		return PORT;
	}

	public static String getDatabase() {
		return DATABASE;
	}

	public static String getRdbms() {
		return RDBMS;
	}

	public static String getUser() {
		return USER;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public static String getTransactionIsolation() {
		return TRANSACTION_ISOLATION;
	}
}
