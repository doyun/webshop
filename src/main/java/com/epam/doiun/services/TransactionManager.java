package com.epam.doiun.services;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.doiun.anotation.Transactional;
import com.epam.doiun.constants.ExceptionMessage;
import com.epam.doiun.dao.ConnectionHolder;
import com.epam.doiun.dao.ConnectionManager;
import com.epam.doiun.exception.DAOException;
import com.epam.doiun.exception.ServiceException;
import com.epam.doiun.util.AnnotatedMethodsScanner;

public class TransactionManager implements InvocationHandler {

	private static final Logger LOGGER = Logger
			.getLogger(TransactionManager.class);
	private ConnectionManager connectionManager;
	private ConnectionHolder connectionHolder;
	private Object service;
	private List<Method> transactionalMethods;

	/**
	 * Creates a new transaction manager.
	 *
	 * @param manager
	 *            connection manager
	 * @param holder
	 *            connection holder
	 * @param serviceToInvoke
	 *            service to invoke
	 */
	public TransactionManager(ConnectionManager manager,
			ConnectionHolder holder, Object serviceToInvoke) {
		this.connectionManager = manager;
		this.service = serviceToInvoke;
		this.connectionHolder = holder;
		transactionalMethods = AnnotatedMethodsScanner
				.getMethodsWithDeclaredAnnotation(serviceToInvoke.getClass(),
						Transactional.class);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		if (transactionalMethods.contains(method)) {
			return invokeWithTransaction(method, args);
		}
		return invokeWithoutTransaction(method, args);
	}

	private Object invokeWithoutTransaction(Method method, Object[] args) {
		Connection connection = connectionManager.getConnection();
		connectionHolder.set(connection);
		try {
			connection.setAutoCommit(true);
			return method.invoke(service, args);
		} catch (Exception e) {
			LOGGER.error(ExceptionMessage.INVOKE_EXCEPTION.getMessage()
					+ e.getMessage());
			throw new ServiceException(
					ExceptionMessage.INVOKE_EXCEPTION.getMessage(), e);
		} finally {
			closeConnection(connection);
			connectionHolder.remove();
		}
	}

	private Object invokeWithTransaction(Method method, Object[] args) {
		Connection connection = connectionManager.getConnection();
		connectionHolder.set(connection);
		try {
			connection.setAutoCommit(false);
			Object result;
			try {
				result = method.invoke(service, args);
			} catch (Exception e) {
				throw new ServiceException(
						ExceptionMessage.INVOKE_EXCEPTION.getMessage(), e);
			}
			connection.commit();
			return result;
		} catch (Exception e) {
			LOGGER.error(ExceptionMessage.COMMIT_FAIL.getMessage()
					+ e.getMessage());
			rollback(connection);
			throw new DAOException(ExceptionMessage.COMMIT_FAIL.getMessage(), e);
		} finally {
			closeConnection(connection);
			connectionHolder.remove();
		}
	}

	private void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			LOGGER.error(ExceptionMessage.CONNECTION_CLOSE_EXCEPTION
					.getMessage() + e.getMessage());
			throw new DAOException(
					ExceptionMessage.CONNECTION_CLOSE_EXCEPTION.getMessage(), e);
		}
	}

	private void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			LOGGER.error(ExceptionMessage.ROLLBACK_EXCEPTION.getMessage()
					+ e.getMessage());
			throw new DAOException(
					ExceptionMessage.ROLLBACK_EXCEPTION.getMessage(), e);
		}
	}

}