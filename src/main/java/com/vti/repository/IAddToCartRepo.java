package com.vti.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.entity.Cart;

@Repository
public interface IAddToCartRepo extends JpaRepository<Cart, Integer> {

	// remove cart by userid and cartId,
	// getCartByuserId

	@Query("Select sum(item.total_price) FROM Cart item WHERE item.user_id=:user_id")
	double getTotalAmountByUserId(@Param("user_id") int user_id);

	@Query("Select item FROM Cart item WHERE item.user_id=:user_id")
	List<Cart> getCartByuserId(@Param("user_id") int user_id);

	@Query("Select item FROM Cart item ")
	Optional<Cart> getCartByuserIdtest();

	@Query("Select item FROM Cart item WHERE item.product.id= :product_id and item.user_id=:user_id")
	Optional<Cart> getCartByProductIdAnduserId(@Param("user_id") int user_id, @Param("product_id") int product_id);

	@Modifying
	@Transactional
	@Query("DELETE FROM Cart item WHERE item.cart_id =:cart_id  and item.user_id=:user_id")
	void deleteCartByIdAndUserId(@Param("cart_id") int cart_id, @Param("user_id") int user_id);

	@Modifying
	@Transactional
	@Query("DELETE FROM Cart item WHERE item.user_id=:user_id")
	void deleteAllCartByUserId(@Param("user_id") int user_id);

	@Modifying
	@Transactional
	@Query("update Cart item set item.quantity=:quantity,item.total_price=:total_price WHERE item.id=:cart_id")
	void updateQtyByCartId(@Param("cart_id") int cart_id, @Param("quantity") Integer quantity,
			@Param("total_price") double total_price);

}
