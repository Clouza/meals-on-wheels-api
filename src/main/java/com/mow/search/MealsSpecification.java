package com.mow.search;

import org.springframework.data.jpa.domain.Specification;

import com.mow.entity.Meals;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MealsSpecification implements Specification<Meals> {

	private static final long serialVersionUID = 1L;
	private SearchCriteria searchCriteria;
	
	public MealsSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Meals> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if(searchCriteria.getOperation().equalsIgnoreCase(">=")) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
		}
		
		if (searchCriteria.getOperation().equalsIgnoreCase("<=")) {
            return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
		
		if(searchCriteria.getOperation().equalsIgnoreCase(">")) {
			return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
		}
		
		if (searchCriteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
		
        if (searchCriteria.getOperation().equalsIgnoreCase("=")) {
            return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
        }
        
        if (searchCriteria.getOperation().equalsIgnoreCase("%")) {
            return criteriaBuilder.like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
        }
		
		return null;
	}

}
