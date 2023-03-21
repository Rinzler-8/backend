package com.vti.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Cart;
import com.vti.entity.Order;
import com.vti.entity.OrderItems;
import com.vti.entity.Product;
import com.vti.repository.IAddToCartRepo;
import com.vti.repository.ICheckoutRepo;
import com.vti.repository.IOrderItemsRepo;

@Service
public class CartSerivceImpl implements ICartService {

	@Autowired
	IAddToCartRepo addCartRepo;
	@Autowired
	ICheckoutRepo checkOutRepo;
	@Autowired
	IOrderItemsRepo orderItemsRepo;
	@Autowired
	ProductService proServices;
	private static final Logger logger = LoggerFactory.getLogger(CartSerivceImpl.class);

	@Override
	public List<Cart> addCartbyUserIdAndProductId(int productId, int userId, int quantity, double price)
			throws Exception {
		try {
			if (addCartRepo.getCartByProductIdAnduserId(userId, productId).isPresent()) {
				throw new Exception("Product is already exist.");
			}
			Cart obj = new Cart();
			Product pro = proServices.getProductById(productId);
			obj.setProduct(pro);
			obj.setUser_id(userId);
			obj.setQuantity(quantity);
			// TODO price has to check with quantity
			obj.setPrice(price);
			addCartRepo.save(obj);
			return this.getCartByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("" + e.getMessage());
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public List<Cart> getCartByUserId(int userId) {
		return addCartRepo.getCartByuserId(userId);
	}

	@Override
	public List<Cart> removeCartByUserId(int cartId, int userId) {
		addCartRepo.deleteCartByIdAndUserId(cartId, userId);
		return this.getCartByUserId(userId);
	}

	@Override
	public void updateQtyByCartId(int cartId, int quantity, double price) throws Exception {
		addCartRepo.updateQtyByCartId(cartId, quantity, price);
	}

	@Override
	public Boolean checkTotalAmountAgainstCart(double totalAmount, int userId) {
		double total_amount = addCartRepo.getTotalAmountByUserId(userId);
		if (total_amount == totalAmount) {
			return true;
		}
		System.out.print("Error from request " + total_amount + " --db-- " + totalAmount);
		return false;
	}

	@Override
	public Order getOrderInfo(int orderId) {
		return checkOutRepo.getOrderInfo(orderId);
	}

	@Override
	public List<OrderItems> getOrderItemsByOrderId(int orderId) {
		return orderItemsRepo.getOrderItemsByOrderId(orderId);
	}

	@Override
	public List<OrderItems> getOrderItemsBySessionId(int sessionId) {
		return orderItemsRepo.getOrderItemsBySessionId(sessionId);
	}

	@Override
	public Order saveProductsForCheckout(Order tmp) throws Exception {
		try {
			int user_id = tmp.getUser_id();
			int order_id = tmp.getId();
			checkOutRepo.save(tmp);
			return this.getOrderInfo(order_id);

		} catch (Exception e) {
			throw new Exception("Error while checkout " + e.getMessage());
		}

	}

	@Override
	public List<OrderItems> saveOrderItems(List<OrderItems> tmp2) throws Exception {
		try {
			int order_id = tmp2.get(0).getOrder_id();
			if (tmp2.size() > 0) {
				orderItemsRepo.saveAll(tmp2);
				return this.getOrderItemsByOrderId(order_id);
			} else {
				throw new Exception("Should not be empty");
			}
		} catch (Exception e) {
			throw new Exception("Error while checkout " + e.getMessage());
		}

	}

	@Override
	public List<Cart> removeAllCartByUserId(int userId) {
		addCartRepo.deleteAllCartByUserId(userId);
		return null;
	}

}
