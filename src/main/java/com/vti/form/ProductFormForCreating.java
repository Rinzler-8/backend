package com.vti.form;

public class ProductFormForCreating {
	private String name;
	private double price;
	private String info;
	private String detail;
	private short ratingStar;
	private String imageName;
	private short manufacturerId;
	private short categoryId;

	public ProductFormForCreating() {
		super();
	}

	public String getName() {
		return name;
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

	public short getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(short manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public short getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(short categoryId) {
		this.categoryId = categoryId;
	}

}
