package com.vti.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.entity.User;

public class AccountSpecification implements Specification<User> {
	private String field;
	private String operator;
	private Object value;

//	@Override
//	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//		if (operator.equalsIgnoreCase("LIKE")) {
//			if (field.equalsIgnoreCase("department")) {
//				return criteriaBuilder.like(root.get("department").get("name"), "%" + value.toString() + "%");
//			} else if (field.equalsIgnoreCase("position")) {
//				return criteriaBuilder.like(root.get("position").get("name").as(String.class),
//						"%" + value.toString() + "%");
//			} else {
//				return criteriaBuilder.like(root.get(field), "%" + value.toString() + "%");
//			}
//		}
//
//		return null;
//	}
	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (operator.equalsIgnoreCase("LIKE")) {
			if (field.equalsIgnoreCase("department")) {
				return criteriaBuilder.like(root.get("department").get("name"), "%" + value.toString() + "%");
			} else if (field.equalsIgnoreCase("position")) {
				return criteriaBuilder.like(root.get("position").get("name").as(String.class),
						"%" + value.toString() + "%");
			} else {
//				System.out.println("df " + field);
				return criteriaBuilder.like(root.get(field), "%" + value.toString() + "%");
			}
		}

		return null;
	}

	public AccountSpecification(String field, String operator, Object value) {
		super();
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

}
