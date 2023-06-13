package com.vti.security.service;

import java.util.List;

import com.vti.dto.ReviewDto;
import com.vti.entity.Review;
//import com.vti.form.CategoryFormForCreating;

public interface IReviewService {
	public List<Review> getAllReviews();

	public List<Review> rateProducts(List<ReviewDto> reviewDto);
}
