package com.vti.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Order;
import com.vti.form.OrderFormForCreating;
import com.vti.form.OrderFormForUpdating;

public interface IOrderService {
	public Page<Order> getAllOrders(Pageable pageable, String search);

	public Order getOrderById(int order_id);

	public Order createOrder(OrderFormForCreating orderNewForm);

	public Order updateOrder(int order_id, OrderFormForUpdating orderUpdateForm);

	public void deleteOrderById(int order_id);
}
