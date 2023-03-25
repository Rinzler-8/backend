package com.vti.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Order;
import com.vti.entity.OrderStatus;
import com.vti.form.OrderFormForCreating;

public interface IOrderService {
	public Page<Order> getAllOrders(Pageable pageable, String search);

	public Order getOrderById(int order_id);

	public Order createOrder(OrderFormForCreating orderNewForm);

	public Order updateOrderStatus(int order_id, OrderStatus orderStatus);

	public void deleteOrderById(int order_id);
}
