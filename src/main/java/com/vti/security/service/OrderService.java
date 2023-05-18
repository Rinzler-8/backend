package com.vti.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vti.entity.Order;
import com.vti.entity.OrderStatus;
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
			OrderSpecification idSpecification = new OrderSpecification("id", "LIKE", search);
			OrderSpecification sessionSpecification = new OrderSpecification("sessionId", "LIKE", search);
			whereOrder = Specification.where(idSpecification).or(sessionSpecification);
		}

		return orderRepository.findAll(whereOrder, pageable); // findAll - phuong thuc co san cua JPA da duoc xay
																// dung san khi extends ben repository
	}

	@Override
	public Order getOrderById(int id) {
		return orderRepository.getById(id);
	}

	@Override
	public void deleteOrderById(int id) {
		orderRepository.deleteById(id);
	}

	@Override
	public Order createOrder(OrderFormForCreating orderNewForm) {
		Order order = new Order();
		order.setid(orderNewForm.getid());
		order.setSessionId(orderNewForm.getSessionId());
		order.setFirstName(orderNewForm.getFirstName());
		order.setLastName(orderNewForm.getLastName());
		order.setMobile(orderNewForm.getMobile());
		order.setDelivery_address(orderNewForm.getDelivery_address());
		order.setPaymentType(orderNewForm.getPaymentType());
		Order orderNew = orderRepository.save(order);
		return orderNew;

	}

	@Override
	public Order updateOrder(int id, OrderFormForUpdating orderUpdateForm) {
		Order order = orderRepository.getById(id);

		order.setStatus(orderUpdateForm.getStatus());

		Order orderUpdate = orderRepository.save(order);
		return orderUpdate;
	}

	@Override
	public Order updateOrderStatus(int id, OrderStatus orderStatus) {
		Order order = orderRepository.getById(id);

		order.setStatus(orderStatus);

		Order orderUpdate = orderRepository.save(order);
		return orderUpdate;
	}

}
