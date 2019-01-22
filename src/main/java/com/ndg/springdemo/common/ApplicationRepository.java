package com.ndg.springdemo.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.CrossOrigin;


@NoRepositoryBean
public interface ApplicationRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor {

}
