package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.vti.entity.Review;

@Repository
public interface IReviewRepo extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {

//	@Query("Select item FROM Review item WHERE item.product.id=:productId")
//	List<Review> getReviewByProductId(@Param("productId") int productId);

}
