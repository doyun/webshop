package com.epam.doiun.dao;

import java.sql.Connection;

/**
 * Provides thread-safe implementation of {@link ConnectionHolder} interface.
 * 
 * @see java.lang.ThreadLocal
 */
public class ThreadLocalConnectionHolder implements ConnectionHolder {

	private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

	@Override
	public Connection get() {
		return connectionHolder.get();
	}

	@Override
	public void set(Connection connection) {
		connectionHolder.set(connection);
	}

	@Override
	public void remove() {
		connectionHolder.remove();
	}
}
