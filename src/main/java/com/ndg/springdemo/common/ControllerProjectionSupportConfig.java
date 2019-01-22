package com.ndg.springdemo.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

@Configuration
class ControllerProjectionSupportConfig {

  @Bean
  public SpelAwareProxyProjectionFactory projectionFactory() {
	  
    return new SpelAwareProxyProjectionFactory();
    
  }
}
