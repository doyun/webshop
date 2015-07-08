package com.epam.doiun.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.epam.doiun.anotation.Transactional;
import com.epam.doiun.bean.CheckoutFormBean;
import com.epam.doiun.constants.OrderStatus;
import com.epam.doiun.dao.OrderDAO;
import com.epam.doiun.entity.BillingInfoDTO;
import com.epam.doiun.entity.Order;
import com.epam.doiun.entity.OrderItem;
import com.epam.doiun.entity.Product;
import com.epam.doiun.entity.User;

public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = Logger
			.getLogger(OrderServiceImpl.class);
	private OrderDAO orderDAO;

	public OrderServiceImpl(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Transactional
	@Override
	public Order createOrder(HashMap<Product, Integer> products, User user) {
		Order order = new Order();
		order.setDate(new Timestamp(System.currentTimeMillis()));
		order.setUserId(user.getId());
		order.setStatus(OrderStatus.IS_FORMING.toString());

		Integer orderId = orderDAO.createOrder(order);

		order.setId(orderId);
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		BigDecimal price = BigDecimal.ZERO;
		for (Entry<Product, Integer> entry : products.entrySet()) {
			orderItems.add(new OrderItem(orderId, entry.getKey().getId(), entry
					.getKey().getBrand() + " " + entry.getKey().getModel(),
					entry.getKey().getPrice(), entry.getValue()));
			price = price.add(entry.getKey().getPrice()
					.multiply(new BigDecimal(entry.getValue())));
		}
		orderDAO.createOrderItems(orderItems);
		order.setItems(orderItems);
		order.setPrice(price);
		return order;
	}

	@Transactional
	@Override
	public void updateOrderItems(Order order, HashMap<Product, Integer> products) {
		orderDAO.removeOrderItems(order.getId());
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		BigDecimal price = BigDecimal.ZERO;
		for (Entry<Product, Integer> entry : products.entrySet()) {
			orderItems
					.add(new OrderItem(order.getId(), entry.getKey().getId(),
							entry.getKey().getBrand() + " "
									+ entry.getKey().getModel(), entry.getKey()
									.getPrice(), entry.getValue()));
			price = price.add(entry.getKey().getPrice()
					.multiply(new BigDecimal(entry.getValue())));
		}
		orderDAO.createOrderItems(orderItems);
		order.setItems(orderItems);
		order.setPrice(price);
	}

	@Override
	public void updateWithBillingInfo(Order order, CheckoutFormBean bean) {
		BillingInfoDTO billingObject = new BillingInfoDTO();
		billingObject.setAddress(bean.getAddress());
		billingObject.setDeliveryType(bean.getDeliveryType());
		billingObject.setPaymentType(bean.getPaymentType());
		billingObject.setPrice(order.getPrice());
		orderDAO.updateOrderWithBillingInfo(billingObject, order);
		order.setDeliveryAddress(bean.getAddress());
		order.setDeliveryType(bean.getDeliveryType());
		order.setPaymentType(bean.getPaymentType());
	}

	@Override
	public void setOrderStatus(Order order, OrderStatus orderStatus) {
		orderDAO.updateOrderStatus(order.getId(), orderStatus.toString());
	}
}
