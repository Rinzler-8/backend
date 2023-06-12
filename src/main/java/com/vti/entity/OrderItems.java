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
@Table(name = "`Order_Items`")
public class OrderItems {

	@Column(name = "item_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int item_id;

	@JoinTable(name = "`Order`", joinColumns = @JoinColumn(name = "id"))
	private int id;

	@Column(name = "session_id")
	@JoinTable(name = "`Order`", joinColumns = @JoinColumn(name = "session_id"))
	private int sessionId;

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
	private int productId;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public String getProductName() {
		return product.getName();
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
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

	public short getRatingStar() {
		return product.getRatingStar();
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getProductId() {
		return product.getProductId();
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
