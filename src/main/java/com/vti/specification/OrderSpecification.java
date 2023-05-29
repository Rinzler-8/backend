package com.vti.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.entity.Order;

public class OrderSpecification implements Specification<Order> {
	private String field;
	private String operator;
	private Object value;

	@Override
	public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (operator.equalsIgnoreCase("LIKE")) {
			return criteriaBuilder.like(root.get(field).as(String.class), "%" + value + "%");
		} else if (operator.equalsIgnoreCase("=")) {
			return criteriaBuilder.equal(root.get(field), value);
		}

		return null;
	}

	public OrderSpecification(String field, String operator, Object value) {
		super();
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

}
