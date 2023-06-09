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
import com.vti.entity.Order;
import com.vti.entity.OrderItems;
import com.vti.security.service.ICartService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("api/v1/order")
public class CheckoutController {
	@Autowired
	private ICartService cartService;

	@PostMapping("/checkout")
	public ResponseEntity<?> createOrder(@RequestBody CheckoutDTO checkoutDto) {
		try {
			List<Cart> cartItems = cartService.getCartByUserId(checkoutDto.getUser_id());
			Order cart = new Order();
			cart.setFirstName(checkoutDto.getFirstName());
			cart.setLastName(checkoutDto.getLastName());
			cart.setMobile(checkoutDto.getMobile());
			cart.setUser_id(checkoutDto.getUser_id());
			cart.setDelivery_address(checkoutDto.getDelivery_address());
			cart.setNote(checkoutDto.getNote());
			cart.setPaymentType(checkoutDto.getPaymentType());
			cart.setSessionId(getOrderId());
			cartService.saveProductsForCheckout(cart);
			List<OrderItems> orderItemList = new ArrayList<OrderItems>();

			for (int i = 0; i < cartItems.size(); i++) {
				System.out.println("size: " + cart.getSessionId());
				OrderItems item = new OrderItems();
				item.setid(cart.getid());
				item.setSessionId(cart.getSessionId());
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

//	@PostMapping("/checkout/{user_id}/{id}")
//	public ResponseEntity<?> checkout(@PathVariable(name = "user_id") int user_id,
//			@PathVariable(name = "id") int id, @RequestBody CheckoutDTO checkoutDto) {
//		try {
//			this.createOrder(user_id, checkoutDto);
//			List<Cart> cartItems = cartService.getCartByUserId(user_id);
//			Checkout orderItems = cartService.getOrderInfo(id);
//			List<OrderItems> tmp2 = new ArrayList<OrderItems>();
//			for (int i = 0; i < cartItems.size(); i++) {
//				OrderItems item = new OrderItems();
//				item.setid(orderItems.getid());
//				item.setSessionId(orderItems.getSessionId());
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

	@GetMapping(value = "/getOrderInfo/{id}")
	public ResponseEntity<?> getOrderDetailsByUserId(@PathVariable(name = "id") int id) {
		try {
			Order obj = cartService.getOrderInfo(id);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not get checkout by user id", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/getOrderItems/{sessionId}")
	public ResponseEntity<?> getFullOrderByOrderId(@PathVariable(name = "sessionId") int sessionId) {
		try {
			List<OrderItems> obj = cartService.getOrderItemsBySessionId(sessionId);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not get checkout by session id", HttpStatus.NOT_FOUND);
		}
	}
}
