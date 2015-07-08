package com.epam.doiun.dao;

import java.util.List;

import com.epam.doiun.entity.BillingInfoDTO;
import com.epam.doiun.entity.Order;
import com.epam.doiun.entity.OrderItem;

public interface OrderDAO {

	Integer createOrder(Order order);

	void createOrderItems(List<OrderItem> orderItems);

	void removeOrderItems(Integer id);

	void updateOrderWithBillingInfo(BillingInfoDTO billingObject, Order order);

	void updateOrderStatus(Integer id, String status);
}
