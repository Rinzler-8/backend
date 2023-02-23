package com.vti.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.CartDTO;
import com.vti.entity.Cart;
import com.vti.security.service.ICartService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("api/v1/cart")
public class CartController {

	@Autowired
	private ICartService cartService;

	@PostMapping("addToCart/{id}")
//	public ResponseEntity<?> addCartwithProduct(@RequestBody CartDTO cartDto) {
//		try {
//			int productId = cartDto.getProduct_id();
//			int userId = cartDto.getUser_id();
//			int quantity = cartDto.getQuantity();
//			double price = cartDto.getPrice();
////			List<Cart> obj = cartService.addCartbyUserIdAndProductId(productId, userId, quantity, price);
//			List<Cart> obj = cartService.addCartbyUserIdAndProductId(productId, userId, price);
//			return ResponseEntity.ok(obj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("Can not add to cart", HttpStatus.NOT_FOUND);
//		}
//
//	}

	public ResponseEntity<?> addCartwithProduct(@PathVariable(name = "id") int id, @RequestBody CartDTO cartDto) {
		try {
			int productId = cartDto.getProduct_id();
			int userId = id;
			int quantity = cartDto.getQuantity();
			double price = cartDto.getPrice();
			List<Cart> obj = cartService.addCartbyUserIdAndProductId(productId, userId, quantity, price);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Can not add to cart", HttpStatus.NOT_FOUND);
		}

	}

//	@PutMapping("updateQtyForCart")
//	public ResponseEntity<?> updateQtyForCart(@RequestBody HashMap<String, String> addCartRequest) {
//		try {
//			String keys[] = { "cartId", "userId", "quantity", "price" };
//			int cartId = Integer.parseInt(addCartRequest.get("cartId"));
//			int userId = Integer.parseInt(addCartRequest.get("userId"));
//			int quantity = Integer.parseInt(addCartRequest.get("quantity"));
//			double price = Double.parseDouble(addCartRequest.get("price"));
//			cartService.updateQtyByCartId(cartId, quantity, price);
//			List<Cart> obj = cartService.getCartByUserId(userId);
//			return ResponseEntity.ok(obj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
//		}
//
//	}

	@PutMapping("updateQtyForCart/{id}")
	public ResponseEntity<?> updateQtyForCart(@PathVariable(name = "id") int id, @RequestBody CartDTO cartDto) {
		try {
			int cartId = cartDto.getCart_id();
			int userId = id;
			int quantity = cartDto.getQuantity();
//			double prodPrice = product.getPrice();
			double prodPrice = cartDto.getPrice();
			double total_price = prodPrice * quantity;
			cartService.updateQtyByCartId(cartId, quantity, total_price);
			List<Cart> obj = cartService.getCartByUserId(userId);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = "removeProductFromCart/{cartId}/{userId}")
	public ResponseEntity<?> removeCartwithProductId(@PathVariable(name = "cartId") int cartId,
			@PathVariable(name = "userId") int userId) {
		try {
			List<Cart> obj = cartService.removeCartByUserId(cartId, userId);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/getCartsByUserId/{id}")
	public ResponseEntity<?> getCartsByUserId(@PathVariable(name = "id") int id) {
		try {
			List<Cart> obj = cartService.getCartByUserId(id);
			return ResponseEntity.ok(obj);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not get cart by user id", HttpStatus.NOT_FOUND);
		}
	}
}
