package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.entity.Checkout;

@Repository
public interface ICheckoutRepo extends JpaRepository<Checkout, Integer> {
	@Query("Select checkCart FROM Checkout checkCart WHERE checkCart.order_id=:order_id")
	Checkout getOrderInfo(@Param("order_id") int order_id);
}

//@Query("DELETE  FROM Cart item WHERE item.cart_id =:cart_id   and item.user_id=:user_id")
//void deleteCartByIdAndUserId(@Param("cart_id") int cart_id, @Param("user_id") int user_id);