//package com.vti.controllers;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.vti.dto.CheckoutDTO;
//import com.vti.entity.Cart;
//import com.vti.entity.Checkout;
//import com.vti.entity.OrderItems;
//import com.vti.security.service.ICartService;
//
//@CrossOrigin(origins = { "http://localhost:3000" })
//@RestController
//@RequestMapping("api/v1/order")
//public class CheckoutController2 {
//	@Autowired
//	private ICartService cartService;
//
//	@PostMapping("/checkout/{id}")
//	public ResponseEntity<?> checkout_order(@PathVariable(name = "id") int id, @RequestBody CheckoutDTO checkoutDto) {
//		try {
//			List<Cart> cartItems = cartService.getCartByUserId(id);
//			List<Checkout> tmp = new ArrayList<Checkout>();
////			for (Cart addCart : cartItems) {
//			Checkout cart = new Checkout();
//			cart.setSession_id(this.getOrderId());
//			cart.setFirst_name(checkoutDto.getFirst_name());
//			cart.setLast_name(checkoutDto.getLast_name());
//			cart.setMobile(checkoutDto.getMobile());
//			cart.setUser_id(id);
//			cart.setDelivery_address(checkoutDto.getDelivery_address());
//			cart.setPaymentType(checkoutDto.getPaymentType());
//			tmp.add(cart);
////			}
//			cartService.saveProductsForCheckout(tmp);
//			return ResponseEntity.ok("Order placed successfully");
//		} catch (
//
//		Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("Can not checkout", HttpStatus.NOT_FOUND);
//		}
//	}
//
//	@PostMapping("/checkout1/{id}")
//	public ResponseEntity<?> checkout1_order(@PathVariable(name = "id") int id, @RequestBody CheckoutDTO checkoutDto) {
//		int index, index1;
//		try {
//			this.checkout_order(id, checkoutDto);
//			List<Cart> cartItems = cartService.getCartByUserId(id);
//			Checkout orderItems = cartService.getOrderByUserId(id);
//			List<OrderItems> tmp2 = new ArrayList<OrderItems>();
////			if (orderItems.size() == 1) {
////				index = orderItems.get(0).getOrder_id();
////				index1 = orderItems.get(0).getSession_id();
////			} else {
////				index = orderItems.get(orderItems.size() - 1).getOrder_id();
////				index1 = orderItems.get(orderItems.size() - 1).getSession_id();
////			}
//			index = orderItems.getOrder_id();
//			index1 = orderItems.getSession_id();
//			for (int i = 0; i < cartItems.size(); i++) {
//				OrderItems order = new OrderItems();
//				order.setOrder_id(index);
//				order.setSession_id(index1);
//				order.setProduct(cartItems.get(i).getProduct());
//				order.setQuantity(cartItems.get(i).getQuantity());
//				tmp2.add(order);
//			}
//			cartService.saveOrderItems(tmp2);
//			return ResponseEntity.ok("Order placed successfully");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("Can not checkout", HttpStatus.NOT_FOUND);
//		}
//	}
//
//	public int getOrderId() {
//		Random r = new Random(System.currentTimeMillis());
//		return 10000 + r.nextInt(20000);
//	}
//
//	@GetMapping(value = "/getOrderInfo/{id}")
//	public ResponseEntity<?> getOrderDetailsByUserId(@PathVariable(name = "id") int id) {
//		try {
//			Checkout obj = cartService.getOrderByUserId(id);
//			return ResponseEntity.ok(obj);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Can not get checkout by user id", HttpStatus.NOT_FOUND);
//		}
//	}
//
//	@GetMapping(value = "/getOrderItems/{id}")
//	public ResponseEntity<?> getFullOrderByOrderId(@PathVariable(name = "id") int id) {
//		try {
//			List<OrderItems> obj = cartService.getOrderItemsBySessionId(id);
//			return ResponseEntity.ok(obj);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Can not get checkout by user id", HttpStatus.NOT_FOUND);
//		}
//	}
//}
