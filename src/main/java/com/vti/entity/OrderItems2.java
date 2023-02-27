package com.vti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Order_Items")
public class OrderItems2 {

	@Column(name = "item_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int item_id;

	@JoinTable(name = "Checkout", joinColumns = @JoinColumn(name = "order_id"))
	private int order_id;

	@JoinTable(name = "Checkout", joinColumns = @JoinColumn(name = "session_id"))
	private int session_id;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	private int quantity;

	@Transient
	private double price;

	@Transient
	private String imageName, info, detail, productName;

	@Transient
	private int product_id;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getProductName() {
		return product.getName();
	}

	public int getSession_id() {
		return session_id;
	}

	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}

	public String getInfo() {
		return product.getInfo();
	}

	public String getDetail() {
		return product.getDetail();
	}

	public String getImageName() {
		return product.getImageName();
	}

	public double getPrice() {
		return product.getPrice();
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getProduct_id() {
		return product.getId();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
