package com.vti.controllers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.ReviewDto;
import com.vti.entity.Review;
import com.vti.security.service.IReviewService;

@RestController
@RequestMapping(value = "api/v1/reviews")
@CrossOrigin("*")
public class ReviewController {
	@Autowired
	private IReviewService reviewService;

	@GetMapping()
	public ResponseEntity<?> getAllReviews(Pageable pageable, @RequestParam(required = false) String search) {
		Page<Review> reviewPage_DB = reviewService.getAllReviews(pageable, search);
		// Dữ liệu lấy ở DB, đã được thực hiện phân trang và sort dữ liệu
		// Chuyển đổi dữ liệu
		Page<ReviewDto> reviewPage_Dtos = reviewPage_DB.map(new Function<Review, ReviewDto>() {
			@Override
			public ReviewDto apply(Review review) {
				ReviewDto reviewDto = new ReviewDto();
				reviewDto.setReview_id(review.getReview_id());
				reviewDto.setSessionId(review.getSessionId());
				reviewDto.setReview(review.getReview());
				reviewDto.setCreated_At(review.getCreated_at());
				return reviewDto;
			}

		});

		return new ResponseEntity<>(reviewPage_Dtos, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> rateProduct(@RequestBody ReviewDto reviewDto) {
		try {
			Review newReview = reviewService.rateProducts(reviewDto);
			ReviewDto newReviewDto = new ReviewDto();
			newReviewDto.setProductId(newReview.getProductId());
			newReviewDto.setSessionId(newReview.getSessionId());
			newReviewDto.setRating(newReview.getRating());
			newReviewDto.setReview(newReview.getReview());
			return new ResponseEntity<>("Rate product ok", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not rate product", HttpStatus.NOT_FOUND);
		}
	}

}
