package com.vti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Cart")
public class Cart {
	@Column(name = "cart_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cart_id;
	@JsonIgnore

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	// int id;
	private int quantity;
	private double total_price;
	private int user_id;
	@Column(updatable = false, insertable = false)
	String created_At;

	@Transient
	private double price;

	@Transient
	private String imageName;

	@Transient
	private String info;

	@Transient
	private String detail;

	@Transient
	private int productId;

	@Transient
	private String productName;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotal_price() {
		return total_price = getQuantity() * getPrice();
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}

	public String getCreated_At() {
		return created_At;
	}

	public void setCreated_At(String created_At) {
		this.created_At = created_At;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductName() {
		return product.getName();
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
//		Locale locale = new Locale("vi", "VN");
//		NumberFormat format = NumberFormat.getCurrencyInstance(locale);
//		format.setRoundingMode(RoundingMode.HALF_UP);
//		double price = product.getPrice();
//		return format.format(price);
		return product.getPrice();

//		DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
//		DecimalFormatSymbols formatSymbol = new DecimalFormatSymbols();
//		formatSymbol.setCurrencySymbol("đ");
//		format.setDecimalFormatSymbols(formatSymbol);
//		return product.getPrice();
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

}
