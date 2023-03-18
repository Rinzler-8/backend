package com.vti.controllers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.OrderDto;
import com.vti.entity.Order;
import com.vti.form.OrderFormForCreating;
import com.vti.form.OrderFormForUpdating;
import com.vti.security.service.IOrderService;

@RestController
@RequestMapping(value = "api/v1/orders")
@CrossOrigin("*")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	// Lấy danh sách tất cả order
	@GetMapping()
	public ResponseEntity<?> getAllorder(Pageable pageable, @RequestParam(required = false) String search) {
		Page<Order> orderPage_DB = orderService.getAllOrders(pageable, search);
		// Dữ liệu lấy ở DB, đã được thực hiện phân trang và sort dữ liệu
		// Chuyển đổi dữ liệu
		Page<OrderDto> orderPage_Dtos = orderPage_DB.map(new Function<Order, OrderDto>() {
			@Override
			public OrderDto apply(Order order) {
				OrderDto orderDto = new OrderDto();
				orderDto.setOrder_id(order.getOrder_id());
				orderDto.setSession_id(order.getSession_id());
				orderDto.setFirst_name(order.getFirst_name());
				orderDto.setLast_name(order.getLast_name());
				orderDto.setMobile(order.getMobile());
				orderDto.setDelivery_address(order.getDelivery_address());
				orderDto.setPaymentType(order.getPaymentType());
				return orderDto;
			}

		});

		return new ResponseEntity<>(orderPage_Dtos, HttpStatus.OK);
	}

// Tìm sản phẩm theo id
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getOrderByID(@PathVariable(name = "id") int id) {
		try {
			Order orderDB = orderService.getOrderById(id);

			OrderDto orderDto = new OrderDto();
			orderDto.setOrder_id(orderDB.getOrder_id());
			orderDto.setSession_id(orderDB.getSession_id());
			orderDto.setFirst_name(orderDB.getFirst_name());
			orderDto.setLast_name(orderDB.getLast_name());
			orderDto.setMobile(orderDB.getMobile());
			orderDto.setDelivery_address(orderDB.getDelivery_address());
			orderDto.setPaymentType(orderDB.getPaymentType());

			return new ResponseEntity<>(orderDto, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Can not get order by ID", HttpStatus.NOT_FOUND);
		}

	}

//	Thêm mới sản phẩm
	@PostMapping()
	public ResponseEntity<?> createNeworder(@RequestBody OrderFormForCreating orderNewForm) {
		try {
//			Thêm mới Order
//			Sau khi thêm mới, trả về thông tin Order vừa thêm
			Order orderNew = orderService.createOrder(orderNewForm);

//			Convert
			OrderDto orderNewDto = new OrderDto();
			orderNewDto.setOrder_id(orderNew.getOrder_id());
			orderNewDto.setSession_id(orderNew.getSession_id());
			orderNewDto.setFirst_name(orderNew.getFirst_name());
			orderNewDto.setLast_name(orderNew.getLast_name());
			orderNewDto.setMobile(orderNew.getMobile());
			orderNewDto.setDelivery_address(orderNew.getDelivery_address());
			orderNewDto.setPaymentType(orderNew.getPaymentType());

			return new ResponseEntity<>(orderNewDto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not create new order", HttpStatus.BAD_REQUEST);
		}

	}

// Update sản phẩm

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateOrder(@PathVariable(name = "id") int id,
			@RequestBody OrderFormForUpdating orderUpdateForm) {
		try {
			orderService.updateOrder(id, orderUpdateForm);
			return new ResponseEntity<>("Update order ok", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not update order", HttpStatus.NOT_FOUND);
		}
	}

//	Xóa sản phẩm theo id
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteOrderById(@PathVariable(name = "id") int id) {
		try {

			orderService.deleteOrderById(id);

			return new ResponseEntity<>("Delete order ok", HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Can not delete order", HttpStatus.NOT_FOUND);
		}
	}

}
