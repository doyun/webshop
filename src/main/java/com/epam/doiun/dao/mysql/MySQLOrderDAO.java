package com.epam.doiun.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.doiun.constants.ExceptionMessage;
import com.epam.doiun.dao.ConnectionHolder;
import com.epam.doiun.dao.OrderDAO;
import com.epam.doiun.entity.BillingInfoDTO;
import com.epam.doiun.entity.Order;
import com.epam.doiun.entity.OrderItem;
import com.epam.doiun.exception.DAOException;

public class MySQLOrderDAO implements OrderDAO {

	private static final String CREATE_ORDER = "INSERT INTO `order` (user_id, status, date) VALUES (?, ?, ?)";
	private static final String CREATE_ORDER_ITEM = "INSERT INTO `order_item` (order_id, product_id, price, amount) VALUES (?, ?, ?, ?)";
	private static final String DELETE_ORDER_ITEMS = "DELETE FROM `order_item` WHERE  order_id=?";
	private static final String UPDATE_ORDER_WITH_BILING_INFO = "UPDATE `order` SET address=?, payment_type=?, delivery_type=?, price=? WHERE id=?";
	private static final String UPDATE_ORDER_STATUS = "UPDATE `order` SET status=? WHERE id=?";
	private static final Logger LOGGER = Logger.getLogger(MySQLOrderDAO.class);

	private ConnectionHolder connectionHolder;

	public MySQLOrderDAO(ConnectionHolder connectionHolder) {
		this.connectionHolder = connectionHolder;
	}

	@Override
	public Integer createOrder(Order order) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(CREATE_ORDER,
					Statement.RETURN_GENERATED_KEYS);

			int i = 1;
			pstmt.setInt(i++, order.getUserId());
			pstmt.setString(i++, order.getStatus());
			pstmt.setTimestamp(i++, order.getDate());

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to create order. " + order + e.getMessage());
			throw new DAOException("Fail to create order. " + order
					+ e.getMessage(), e);
		} finally {
			closePreparedStatement(pstmt);
		}
		throw new DAOException("Fail to obtain order id.");
	}

	@Override
	public void createOrderItems(List<OrderItem> orderItems) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(CREATE_ORDER_ITEM);
			for (OrderItem orderItem : orderItems) {
				int i = 1;
				pstmt.setInt(i++, orderItem.getOrderId());
				pstmt.setInt(i++, orderItem.getProductId());
				pstmt.setBigDecimal(i++, orderItem.getPrice());
				pstmt.setInt(i++, orderItem.getAmount());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			LOGGER.error("Fail to create order items. " + e.getMessage());
			throw new DAOException(e);
		} catch (Exception e) {
			LOGGER.error("Fail to create order items. " + e.getMessage());

		} finally {
			closePreparedStatement(pstmt);
		}
	}

	@Override
	public void removeOrderItems(Integer id) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(DELETE_ORDER_ITEMS);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Fail to remove order items. " + e.getMessage());
			throw new DAOException(e);
		} catch (Exception e) {
			LOGGER.error("Fail to remove order items. " + e.getMessage());

		} finally {
			closePreparedStatement(pstmt);
		}
	}

	@Override
	public void updateOrderWithBillingInfo(BillingInfoDTO billingObject,
			Order order) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(UPDATE_ORDER_WITH_BILING_INFO);

			int i = 1;
			pstmt.setString(i++, billingObject.getAddress());
			pstmt.setString(i++, billingObject.getPaymentType());
			pstmt.setString(i++, billingObject.getDeliveryType());
			pstmt.setBigDecimal(i++, billingObject.getPrice());
			pstmt.setInt(i++, order.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Fail to update order with billing info. "
					+ billingObject + e.getMessage());
			throw new DAOException("Fail to update order with billing info. "
					+ billingObject + e.getMessage(), e);
		} finally {
			closePreparedStatement(pstmt);
		}
	}

	@Override
	public void updateOrderStatus(Integer id, String status) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(UPDATE_ORDER_STATUS);

			int i = 1;
			pstmt.setString(i++, status);
			pstmt.setInt(i++, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Fail to update order status. " + e.getMessage());
			throw new DAOException("Fail to update order status. "
					+ e.getMessage(), e);
		} finally {
			closePreparedStatement(pstmt);
		}
	}

	private void closePreparedStatement(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			LOGGER.error("Fail to close PreparedStatement.");
			throw new DAOException(
					ExceptionMessage.CLOSE_STATEMENT_EXCEPTION_MESSAGE
							.getMessage(),
					e);
		}
	}

}
