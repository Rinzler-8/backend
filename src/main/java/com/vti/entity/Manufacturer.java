//package com.vti.entity;
//
//import java.io.Serializable;
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "Manufacturer", catalog = "Genuine_Dignity")
//public class Manufacturer implements Serializable {
//	private static final long serialVersionUID = 1L;
//	@Column(name = "manufacturer_id")
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private short id;
//
//	@Column(name = "manufacturer_name", nullable = false, unique = true)
//	@Enumerated(EnumType.STRING)
//	private ManufacturerName name;
//
//	public enum ManufacturerName {
//		SAMSUNG, APPLE, XIAOMI, OPPO
//	}
//
//	@OneToMany(mappedBy = "manufacturer")
//	List<Product> products;
//
//	public Manufacturer() {
//		super();
//	}
//
//	/**
//	 * @return the id
//	 */
//	public short getId() {
//		return id;
//	}
//
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(short id) {
//		this.id = id;
//	}
//
//	/**
//	 * @return the name
//	 */
//	public ManufacturerName getName() {
//		return name;
//	}
//
//	/**
//	 * @param name the name to set
//	 */
//	public void setName(ManufacturerName name) {
//		this.name = name;
//	}
//
//	/**
//	 * @return the Products
//	 */
//	public List<Product> getProducts() {
//		return products;
//	}
//
//	/**
//	 * @param Products the Products to set
//	 */
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}
//
//}
