package com.vti.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.CheckoutDTO;
import com.vti.entity.Cart;
import com.vti.entity.Checkout;
import com.vti.entity.OrderItems;
import com.vti.security.service.ICartService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("api/v1/order")
public class CheckoutController {
	@Autowired
	private ICartService cartService;

	@PostMapping("/checkout/{id}")
	public ResponseEntity<?> createOrder(@PathVariable(name = "id") int id, @RequestBody CheckoutDTO checkoutDto) {
		try {
			List<Cart> cartItems = cartService.getCartByUserId(id);
			Checkout cart = new Checkout();
			cart.setSession_id(this.getOrderId());
			cart.setFirst_name(checkoutDto.getFirst_name());
			cart.setLast_name(checkoutDto.getLast_name());
			cart.setMobile(checkoutDto.getMobile());
			cart.setUser_id(id);
			cart.setDelivery_address(checkoutDto.getDelivery_address());
			cart.setPaymentType(checkoutDto.getPaymentType());
			cartService.saveProductsForCheckout(cart);
			List<OrderItems> orderItemList = new ArrayList<OrderItems>();
			for (int i = 0; i < cartItems.size(); i++) {
				OrderItems item = new OrderItems();
				item.setOrder_id(cart.getOrder_id());
				item.setSession_id(cart.getSession_id());
				item.setProduct(cartItems.get(i).getProduct());
				item.setQuantity(cartItems.get(i).getQuantity());
				orderItemList.add(item);
			}
			cartService.saveOrderItems(orderItemList);
			return ResponseEntity.ok(cart);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Can not checkout", HttpStatus.NOT_FOUND);
		}
	}

//	@PostMapping("/checkout/{user_id}/{order_id}")
//	public ResponseEntity<?> checkout(@PathVariable(name = "user_id") int user_id,
//			@PathVariable(name = "order_id") int order_id, @RequestBody CheckoutDTO checkoutDto) {
//		try {
//			this.createOrder(user_id, checkoutDto);
//			List<Cart> cartItems = cartService.getCartByUserId(user_id);
//			Checkout orderItems = cartService.getOrderInfo(order_id);
//			List<OrderItems> tmp2 = new ArrayList<OrderItems>();
//			for (int i = 0; i < cartItems.size(); i++) {
//				OrderItems item = new OrderItems();
//				item.setOrder_id(orderItems.getOrder_id());
//				item.setSession_id(orderItems.getSession_id());
//				item.setProduct(cartItems.get(i).getProduct());
//				item.setQuantity(cartItems.get(i).getQuantity());
//				tmp2.add(item);
//			}
//			cartService.saveOrderItems(tmp2);
//			return ResponseEntity.ok("Order placed successfully");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("Can not checkout", HttpStatus.NOT_FOUND);
//		}
//	}

	public int getOrderId() {
		Random r = new Random(System.currentTimeMillis());
		return 10000 + r.nextInt(20000);
	}

	@GetMapping(value = "/getOrderInfo/{order_id}")
	public ResponseEntity<?> getOrderDetailsByUserId(@PathVariable(name = "order_id") int order_id) {
		try {
			Checkout obj = cartService.getOrderInfo(order_id);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not get checkout by user id", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/getOrderItems/{session_id}")
	public ResponseEntity<?> getFullOrderByOrderId(@PathVariable(name = "session_id") int session_id) {
		try {
			List<OrderItems> obj = cartService.getOrderItemsBySessionId(session_id);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not get checkout by session id", HttpStatus.NOT_FOUND);
		}
	}
}
