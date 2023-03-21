package com.vti.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vti.entity.Order;
import com.vti.form.OrderFormForCreating;
import com.vti.form.OrderFormForUpdating;
import com.vti.repository.ICheckoutRepo;
import com.vti.specification.OrderSpecification;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private ICheckoutRepo orderRepository;

	@Override
	public Page<Order> getAllOrders(Pageable pageable, String search) {
		Specification<Order> whereOrder = null;
		if (!StringUtils.isEmpty(search)) {
			OrderSpecification idSpecification = new OrderSpecification("order_id", "LIKE", search);
			OrderSpecification sessionSpecification = new OrderSpecification("session_id", "LIKE", search);
			whereOrder = Specification.where(idSpecification).or(sessionSpecification);
		}

		return orderRepository.findAll(whereOrder, pageable); // findAll - phuong thuc co san cua JPA da duoc xay
																// dung san khi extends ben repository
	}

	@Override
	public Order getOrderById(int order_id) {
		return orderRepository.getById(order_id);
	}

	@Override
	public void deleteOrderById(int order_id) {
		orderRepository.deleteById(order_id);
	}

	@Override
	public Order createOrder(OrderFormForCreating orderNewForm) {
		Order order = new Order();
		order.setId(orderNewForm.getOrder_id());
		order.setSession_id(orderNewForm.getSession_id());
		order.setFirst_name(orderNewForm.getFirst_name());
		order.setLast_name(orderNewForm.getLast_name());
		order.setMobile(orderNewForm.getMobile());
		order.setDelivery_address(orderNewForm.getDelivery_address());
		order.setPaymentType(orderNewForm.getPaymentType());
		Order orderNew = orderRepository.save(order);
		return orderNew;

	}

	@Override
	public Order updateOrder(int order_id, OrderFormForUpdating orderUpdateForm) {
		Order order = orderRepository.getById(order_id);

		order.setFirst_name(orderUpdateForm.getFirst_name());
		order.setLast_name(orderUpdateForm.getLast_name());
		order.setMobile(orderUpdateForm.getMobile());
		order.setDelivery_address(orderUpdateForm.getDelivery_address());
		order.setPaymentType(orderUpdateForm.getPaymentType());

		Order orderUpdate = orderRepository.save(order);
		return orderUpdate;
	}

}
