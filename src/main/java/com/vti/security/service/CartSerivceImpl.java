package com.vti.security.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Cart;
import com.vti.entity.Checkout;
import com.vti.entity.Product;
import com.vti.repository.IAddToCartRepo;
import com.vti.repository.ICheckoutRepo;

@Service
public class CartSerivceImpl implements ICartService {

	@Autowired
	IAddToCartRepo addCartRepo;
	@Autowired
	ICheckoutRepo checkOutRepo;
	@Autowired
	ProductService proServices;
	private static final Logger logger = LoggerFactory.getLogger(CartSerivceImpl.class);

//	@Override
//	public List<Cart> addCartbyUserIdAndProductId(int productId, int userId, int quantity, double price)
//			throws Exception {
//		try {
//			if (addCartRepo.getCartByProductIdAnduserId(userId, productId).isPresent()) {
//				throw new Exception("Product is already exist.");
//			}
//			Cart obj = new Cart();
//			obj.setQuantity(quantity);
//			obj.setUser_id(userId);
//			Product pro = proServices.getProductById(productId);
//			obj.setProduct(pro);
//			// TODO price has to check with quantity
//			obj.settotal_price(price);
//			addCartRepo.save(obj);
//			return this.getCartByUserId(userId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("" + e.getMessage());
//			throw new Exception(e.getMessage());
//		}
//
//	}
//
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
	public List<Checkout> getCheckoutByUserId(int userId) {
		return checkOutRepo.getByuserId(userId);
	}

	@Override
	public List<Checkout> saveProductsForCheckout(List<Checkout> tmp) throws Exception {
		try {
			int user_id = tmp.get(0).getUser_id();
			if (tmp.size() > 0) {
				checkOutRepo.saveAll(tmp);
				return this.getCheckoutByUserId(user_id);
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
