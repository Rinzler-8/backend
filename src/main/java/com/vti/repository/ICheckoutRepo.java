package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.entity.Order;

@Repository
public interface ICheckoutRepo extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
	@Query("Select checkCart FROM Order checkCart WHERE checkCart.id=:id")
	Order getOrderInfo(@Param("id") int id);

	public Order findById(int id);
}

//@Query("DELETE  FROM Cart item WHERE item.cart_id =:cart_id   and item.user_id=:user_id")
//void deleteCartByIdAndUserId(@Param("cart_id") int cart_id, @Param("user_id") int user_id);