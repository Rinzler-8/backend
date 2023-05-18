package com.vti.dto;

public class ProductDto {
	private int id;

	private String name;

	private double price;

	private String info;

	private String detail;

	private short ratingStar;

	private String imageName;

	private String categoryName;

	private int stockQty;

	public int getProductId() {
		return id;
	}

	public void setProductId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
//		Locale locale = new Locale("vi", "VN");
//		NumberFormat format = NumberFormat.getCurrencyInstance(locale);
//		format.setRoundingMode(RoundingMode.HALF_UP);
//		return format.format(price);
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

}
