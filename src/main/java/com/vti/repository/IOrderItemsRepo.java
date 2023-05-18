package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.entity.OrderItems;

@Repository
public interface IOrderItemsRepo extends JpaRepository<OrderItems, Integer> {

	@Query("Select item FROM OrderItems item WHERE item.id=id")
	List<OrderItems> getOrderItemsByOrderId(@Param("id") int id);

	@Query("Select item FROM OrderItems item WHERE item.sessionId=:sessionId")
	List<OrderItems> getOrderItemsBySessionId(@Param("sessionId") int sessionId);
}
