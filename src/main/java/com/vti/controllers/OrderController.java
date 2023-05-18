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
import com.vti.entity.OrderStatus;
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
	public ResponseEntity<?> getAllOrders(Pageable pageable, @RequestParam(required = false) String search) {
		Page<Order> orderPage_DB = orderService.getAllOrders(pageable, search);
		// Dữ liệu lấy ở DB, đã được thực hiện phân trang và sort dữ liệu
		// Chuyển đổi dữ liệu
		Page<OrderDto> orderPage_Dtos = orderPage_DB.map(new Function<Order, OrderDto>() {
			@Override
			public OrderDto apply(Order order) {
				OrderDto orderDto = new OrderDto();
				orderDto.setid(order.getid());
				orderDto.setSessionId(order.getSessionId());
				orderDto.setFirstName(order.getFirstName());
				orderDto.setLastName(order.getLastName());
				orderDto.setUser_id(order.getUser_id());
				orderDto.setMobile(order.getMobile());
				orderDto.setDelivery_address(order.getDelivery_address());
				orderDto.setNote(order.getNote());
				orderDto.setPaymentType(order.getPaymentType());
				orderDto.setOrderStatus(order.getStatus());
				orderDto.setCreated_At(order.getCreated_at());
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
			orderDto.setid(orderDB.getid());
			orderDto.setSessionId(orderDB.getSessionId());
			orderDto.setFirstName(orderDB.getFirstName());
			orderDto.setLastName(orderDB.getLastName());
			orderDto.setUser_id(orderDB.getUser_id());
			orderDto.setMobile(orderDB.getMobile());
			orderDto.setDelivery_address(orderDB.getDelivery_address());
			orderDto.setPaymentType(orderDB.getPaymentType());
			orderDto.setOrderStatus(orderDB.getStatus());
			orderDto.setCreated_At(orderDB.getCreated_at());

			return new ResponseEntity<>(orderDto, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Can not get order by ID", HttpStatus.NOT_FOUND);
		}

	}

//	Thêm mới sản phẩm
	@PostMapping()
	public ResponseEntity<?> createNewOrder(@RequestBody OrderFormForCreating orderNewForm) {
		try {
//			Thêm mới Order
//			Sau khi thêm mới, trả về thông tin Order vừa thêm
			Order orderNew = orderService.createOrder(orderNewForm);

//			Convert
			OrderDto orderNewDto = new OrderDto();
			orderNewDto.setid(orderNew.getid());
			orderNewDto.setSessionId(orderNew.getSessionId());
			orderNewDto.setFirstName(orderNew.getFirstName());
			orderNewDto.setLastName(orderNew.getLastName());
			orderNewDto.setMobile(orderNew.getMobile());
			orderNewDto.setDelivery_address(orderNew.getDelivery_address());
			orderNewDto.setPaymentType(orderNew.getPaymentType());

			return new ResponseEntity<>(orderNewDto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not create new order", HttpStatus.BAD_REQUEST);
		}

	}

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

	@PutMapping()
	public ResponseEntity<?> updateOrderStatus(@RequestParam int id, @RequestParam OrderStatus orderStatus) {
		try {
			orderService.updateOrderStatus(id, orderStatus);
			return new ResponseEntity<>("Update status successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not update order", HttpStatus.NOT_FOUND);
		}
	}

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
