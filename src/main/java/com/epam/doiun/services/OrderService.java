package com.epam.doiun.services;

import java.util.HashMap;

import com.epam.doiun.bean.CheckoutFormBean;
import com.epam.doiun.constants.OrderStatus;
import com.epam.doiun.entity.Order;
import com.epam.doiun.entity.Product;
import com.epam.doiun.entity.User;

public interface OrderService {

	Order createOrder(HashMap<Product, Integer> products, User user);

	void updateOrderItems(Order order, HashMap<Product, Integer> products);

	void updateWithBillingInfo(Order order, CheckoutFormBean bean);

	void setOrderStatus(Order order, OrderStatus orderStatus);
}
