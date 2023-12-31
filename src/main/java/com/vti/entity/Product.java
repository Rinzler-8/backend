package com.vti.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Product", catalog = "GenuineDignity")
public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = (int) 1L;

	@Column(name = "product_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;
	@Column(name = "price", length = 50, nullable = false)
	private double price;
	@Column(name = "product_info", length = 200, nullable = false)
	private String info;
	@Column(name = "product_detail", length = 500)
	private String detail;
	@Column(name = "rating_star")
	private short ratingStar;

	@Column(name = "product_image_name", nullable = false)
	private String imageName;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "stock_qty", nullable = false)
	private int stockQty;

	public Product() {
		super();
	}

	public Product(int id, String name, double price, String info, String detail, short ratingStar, String imageName,
			Category category, int stockQty) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.info = info;
		this.detail = detail;
		this.ratingStar = ratingStar;
		this.imageName = imageName;
		this.category = category;
		this.stockQty = stockQty;
	}

	public String getName() {
		return name;
	}

	public int getProductId() {
		return id;
	}

	public void setProductId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public short getRatingStar() {
		return ratingStar;
	}

	public void setRatingStar(short ratingStar) {
		this.ratingStar = ratingStar;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

}
