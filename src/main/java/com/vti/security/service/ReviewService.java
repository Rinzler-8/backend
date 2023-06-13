package com.vti.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.ReviewDto;
import com.vti.entity.Review;
import com.vti.repository.IReviewRepo;

@Service
public class ReviewService implements IReviewService {
	@Autowired
	public IReviewRepo reviewRepo;

	@Override
	public List<Review> getAllReviews() {
		return reviewRepo.findAll(); // findAll - phuong thuc co san cua JPA da duoc xay
										// dung san khi extends ben repository
	}

	@Override
	public List<Review> rateProducts(List<ReviewDto> reviewDtos) {
		List<Review> reviewList = reviewDtos.stream().map(rv -> {
			Review review = new Review(); // Create a new Review object for each ReviewDto
			review.setProductId(rv.getProductId());
			review.setSessionId(rv.getSessionId());
			review.setRating(rv.getRating());
			review.setReview(rv.getReview());
			return review;
		}).collect(Collectors.toList());
		reviewRepo.saveAll(reviewList);
		return reviewList;
	}
}
