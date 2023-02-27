package com.vti.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vti.entity.Cart;
import com.vti.entity.Checkout;
import com.vti.entity.OrderItems;

@Service
public interface ICartService2 {
//	List<Cart> addCartbyUserIdAndProductId(int productId,int userId,int quantity,double price) throws Exception;
	List<Cart> addCartbyUserIdAndProductId(int productId, int userId, int quantity, double price) throws Exception;

	List<Checkout> saveProductsForCheckout(List<Checkout> tmp) throws Exception;

	List<OrderItems> saveOrderItems(List<OrderItems> tmp2) throws Exception;

	void updateQtyByCartId(int cartId, int quantity, double price) throws Exception;

	List<Cart> getCartByUserId(int userId);

	List<Checkout> getOrderByUserId(int userId);

	List<OrderItems> getOrderItemsByOrderId(int orderId);

	List<OrderItems> getOrderItemsBySessionId(int sessionId);

	List<Cart> removeCartByUserId(int cartId, int userId);

	List<Cart> removeAllCartByUserId(int userId);

	Boolean checkTotalAmountAgainstCart(double totalAmount, int userId);

}
