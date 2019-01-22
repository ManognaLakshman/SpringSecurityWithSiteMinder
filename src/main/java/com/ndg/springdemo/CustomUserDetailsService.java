package com.ndg.springdemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
 
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomUserDetailsService implements UserDetailsService {
	
	Map<String, String> usernames= new HashMap<String,String>() {{
		put("manogna","Manogna Lakshman");
		
	}};
	
	@Override
	 public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException
	    {
      String userFullName = usernames.get(username);
		//Get this user details from database and set its roles also here
      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
      //Collection<SimpleGrantedAuthority> authority =  new ArrayList<SimpleGrantedAuthority>(){{ add(new SimpleGrantedAuthority("LEADERSHIP"));}};
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority("LEADERSHIP");
      authorities.add(authority);
      
        UserDetails user = new User(userFullName, "", true, true, true, true,authorities);
        
       
        return user;
    }
	
}