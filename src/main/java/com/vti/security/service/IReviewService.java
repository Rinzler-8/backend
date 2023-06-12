package com.vti.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.dto.ReviewDto;
import com.vti.entity.Review;
//import com.vti.form.CategoryFormForCreating;

public interface IReviewService {
	public Page<Review> getAllReviews(Pageable pageable, String search);

	public Review rateProducts(ReviewDto reviewDto);
}
