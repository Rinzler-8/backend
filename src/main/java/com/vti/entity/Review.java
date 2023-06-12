package com.vti.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "`Reviews`")
public class Review {

	@Column(name = "review_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int review_id;

	@Column(name = "session_id")
	@JoinTable(name = "`Order`", joinColumns = @JoinColumn(name = "session_id"))
	private int sessionId;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "review")
	private String review;

	@Column(name = "rating")
	private short rating;

	@Column(name = "created_At")
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date created_at;

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getReview_id() {
		return review_id;
	}

	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public short getRating() {
		return rating;
	}

	public void setRating(short rating) {
		this.rating = rating;
	}

}
