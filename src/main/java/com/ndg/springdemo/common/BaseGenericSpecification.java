package com.ndg.springdemo.common;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BaseGenericSpecification<T> implements Specification<T> {

   private SpecSearchCriteria criteria;

    BaseGenericSpecification(final SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SpecSearchCriteria getCriteria() {
        return criteria;
    }
    
    @SuppressWarnings("unchecked")
    private <T1, R> Path<R> getPath(Path<T1> root, String path) {
        String[] pathElements = path.split("\\.");
        Path<?> retVal = root;
        for (String pathEl : pathElements) {
            retVal = (Path<R>) retVal.get(pathEl);
        }
        return (Path<R>) retVal;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
    	
        switch (criteria.getOperation()) {
        
            case EQUALITY:
                return builder.equal(getPath(root, criteria.getKey()), criteria.getValue());
            case NEGATION:
                return builder.notEqual(getPath(root, criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(getPath(root, criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(getPath(root, criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return builder.like(getPath(root, criteria.getKey()), criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(getPath(root, criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(getPath(root, criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(getPath(root, criteria.getKey()), "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }

}