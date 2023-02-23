package com.vti.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.CheckoutDTO;
import com.vti.entity.Cart;
import com.vti.entity.Checkout;
import com.vti.security.service.ICartService;
import com.vti.security.service.IProductService;

@RestController
@RequestMapping("api/v1/order")
public class CheckoutController {
	@Autowired
	private ICartService cartService;
	private IProductService productService;

	@RequestMapping("checkout/{id}")
	public ResponseEntity<?> checkout_order(@PathVariable(name = "id") int id, @RequestBody CheckoutDTO checkoutDto) {
		try {
			int userId = id;
			List<Cart> cartItems = cartService.getCartByUserId(id);
			List<Checkout> tmp = new ArrayList<Checkout>();
			for (Cart addCart : cartItems) {
				Checkout cart = new Checkout();
				cart.setFirst_name(checkoutDto.getFirst_name());
				cart.setLast_name(checkoutDto.getLast_name());
				cart.setMobile(checkoutDto.getMobile());
				cart.setUser_id(id);
				cart.setProduct(addCart.getProduct());
				cart.setQuantity(addCart.getQuantity());
				cart.setDelivery_address(checkoutDto.getDelivery_address());
				cart.setPaymentType(checkoutDto.getPaymentType());
				tmp.add(cart);
			}
			cartService.saveProductsForCheckout(tmp);
			return ResponseEntity.ok("Order placed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Can not checkout", HttpStatus.NOT_FOUND);
		}
	}

	public int getOrderId() {
		Random r = new Random(System.currentTimeMillis());
		return 10000 + r.nextInt(20000);
	}

//	@RequestMapping("getOrdersByUserId")
//	public ResponseEntity<?> getOrdersByUserId(@RequestBody HashMap<String, String> ordersRequest) {
//		try {
//			String keys[] = { "userId" };
//			return ResponseEntity.ok(new ApiResponse("Order placed successfully", ""));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
//		}
//
//	}
}
