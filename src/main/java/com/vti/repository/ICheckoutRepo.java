package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.entity.Checkout;

@Repository
public interface ICheckoutRepo extends JpaRepository<Checkout, Integer> {
	@Query("Select checkCart  FROM Checkout checkCart WHERE checkCart.user_id=:user_id")
	List<Checkout> getByuserId(@Param("user_id") int user_id);
}
