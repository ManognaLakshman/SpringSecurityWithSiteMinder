package com.ndg.springdemo.common;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ndg.springdemo.model.*;
class GenericSpecificationBuilder<U>{

    private final List<SpecSearchCriteria> params;

    GenericSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    private  GenericSpecificationBuilder<U> with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    private  GenericSpecificationBuilder<U> with(final String precedenceIndicator, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) // the operation may be complex operation
            {
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(precedenceIndicator, key, op, value));
        }
        return this;
    }

    private Specification<U> build(Function<SpecSearchCriteria, Specification<U>> converter) {

        if (params.size() == 0) {
            return null;
        }

        final List<Specification<U>> specs = params.stream()
            .map(converter)
            .collect(Collectors.toCollection(ArrayList::new));

        Specification<U> result = specs.get(0);

        for (int idx = 1; idx < specs.size(); idx++) {
            result = params.get(idx)
                .isOrPredicate()
                    ? Specification.where(result)
                        .or(specs.get(idx))
                    : Specification.where(result)
                        .and(specs.get(idx));
        }
        
        return result;
    }

    
	Specification<U> build(Deque<?> postFixedExprStack, Function<SpecSearchCriteria, Specification<U>> converter) throws BadRequestException {

        Deque<Specification<U>> specStack = new LinkedList<>();

        Collections.reverse((List<?>) postFixedExprStack);

        while (!postFixedExprStack.isEmpty()) {
        	
            Object mayBeOperand = postFixedExprStack.pop();

            if (!(mayBeOperand instanceof String)) {
                specStack.push(converter.apply((SpecSearchCriteria) mayBeOperand));
            } else {
                Specification<U> operand1 = specStack.pop();
                Specification<U> operand2 = specStack.pop();
                if (mayBeOperand.equals(SearchOperation.AND_OPERATOR))
                    specStack.push(Specification.where(operand1)
                        .and(operand2));
                else if (mayBeOperand.equals(SearchOperation.OR_OPERATOR))
                    specStack.push(Specification.where(operand1)
                        .or(operand2));
            }

        }
        
//        	System.out.println("ERROR bad request");
//        	return specStack.p);

        		if(specStack.isEmpty()) 
				throw new BadRequestException();

				// TODO Auto-generated catch block
    	        return specStack.pop();

			}
//        	cannotCreateEntityWithKey();
        	
//            response.setStatus( HttpServletResponse.SC_BAD_REQUEST  );

//        	 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
//        return specStack.pop();

    
//	protected ResponseEntity<Object> cannotCreateEntityWithKey() {
//		System.out.print("Bad request");
//       new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//       request.abort();
//         ResponseEntity.badRequest().header("Failure", "A new entity cannot already have an ID").body(null);
       
    }


