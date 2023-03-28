package com.vti.form;

public class ProductFormForCreating {
	private String name;
	private double price;
	private String info;
	private String detail;
	private short ratingStar;
	private String imageName;
	private int categoryId;

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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
