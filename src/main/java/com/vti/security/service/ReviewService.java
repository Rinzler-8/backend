package com.vti.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vti.dto.ReviewDto;
import com.vti.entity.Review;
import com.vti.repository.IReviewRepo;
import com.vti.specification.ReviewSpecification;

@Service
public class ReviewService implements IReviewService {
	@Autowired
	public IReviewRepo reviewRepo;

	@Override
	public Page<Review> getAllReviews(Pageable pageable, String search) {
		Specification<Review> whereReview = null;
		if (!StringUtils.isEmpty(search)) {
			ReviewSpecification prodIdSpecification = new ReviewSpecification("productId", "LIKE", search);
			ReviewSpecification sessionSpecification = new ReviewSpecification("sessionId", "=", search);
			whereReview = Specification.where(prodIdSpecification).or(sessionSpecification);
		}

		return reviewRepo.findAll(whereReview, pageable); // findAll - phuong thuc co san cua JPA da duoc xay
															// dung san khi extends ben repository
	}

	@Override
	public Review rateProducts(ReviewDto reviewDto) {
		Review review = new Review();
		review.setProductId(reviewDto.getProductId());
		review.setSessionId(reviewDto.getSessionId());
		review.setRating(reviewDto.getRating());
		review.setReview(reviewDto.getReview());
		Review newReview = reviewRepo.save(review);
		return newReview;
	}
}
