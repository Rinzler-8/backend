package com.vti.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

//	@GetMapping()
//	public ResponseEntity<?> getAllReviews() {
//		List<Review> reviewPage_DB = reviewService.getAllReviews();
//		// Dữ liệu lấy ở DB, đã được thực hiện phân trang và sort dữ liệu
//		// Chuyển đổi dữ liệu
//		List<ReviewDto> reviewPage_Dtos = new ArrayList<>() {
//			for (Review review: reviewPage_DB) {
//				ReviewDto reviewDto = new ReviewDto();
//				reviewDto.setReview_id(review.getReview_id());
//				reviewDto.setSessionId(review.getSessionId());
//				reviewDto.setReview(review.getReview());
//				reviewDto.setCreated_At(review.getCreated_at());
//				return reviewDto;
//			}
//
//		});
//
//	return new ResponseEntity<>(reviewPage_Dtos,HttpStatus.OK);
//
//	}

	@PostMapping()
	public ResponseEntity<?> rateProduct(@RequestBody List<ReviewDto> reviewDto) {
		try {
			List<Review> newReview = reviewService.rateProducts(reviewDto);
			List<ReviewDto> newReviewDtoList = newReview.stream().map(review -> {
				ReviewDto newReviewDto = new ReviewDto();
				newReviewDto.setProductId(review.getProductId());
				newReviewDto.setSessionId(review.getSessionId());
				newReviewDto.setRating(review.getRating());
				newReviewDto.setReview(review.getReview());
				return newReviewDto; // Return the newReviewDto object
			}).collect(Collectors.toList()); // Collect the mapped objects into a List

			return new ResponseEntity<>(newReviewDtoList, HttpStatus.OK); // Return the newReviewDtoList
		} catch (Exception e) {
			return new ResponseEntity<>("Can not rate product", HttpStatus.NOT_FOUND);
		}
	}

}
