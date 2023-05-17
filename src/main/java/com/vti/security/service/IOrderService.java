package com.vti.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Order;
import com.vti.entity.OrderStatus;
import com.vti.form.OrderFormForCreating;
import com.vti.form.OrderFormForUpdating;

public interface IOrderService {
	public Page<Order> getAllOrders(Pageable pageable, String search);

	public Order getOrderById(int id);

	public Order createOrder(OrderFormForCreating orderNewForm);

	public Order updateOrder(int id, OrderFormForUpdating orderUpdateForm);

	public Order updateOrderStatus(int id, OrderStatus orderStatus);

	public void deleteOrderById(int id);
}
