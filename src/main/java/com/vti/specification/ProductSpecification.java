package com.vti.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.entity.Product;

public class ProductSpecification implements Specification<Product> {
	private String field;
	private String operator;
	private Object value;

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (operator.equalsIgnoreCase("LIKE")) {
			if (field.equalsIgnoreCase("category")) {
				return criteriaBuilder.like(root.get("category").get("name"), "%" + value.toString() + "%");
			} else if (field.equalsIgnoreCase("manufacturer")) {
				return criteriaBuilder.like(root.get("manufacturer").get("name").as(String.class),
						"%" + value.toString() + "%");
			} else {
				return criteriaBuilder.like(root.get(field), "%" + value.toString() + "%");
			}
		}

		return null;
	}

	public ProductSpecification(String field, String operator, Object value) {
		super();
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

}
