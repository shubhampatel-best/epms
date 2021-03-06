package com.epms.authentication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.epms.dto.UserDetailsDTO;
import com.epms.service.IUserDetailsService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private IUserDetailsService iUserDetailsService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("email", username);
		paramSource.addValue("isActive", true);
		List<UserDetailsDTO> userDetailsDTO =  iUserDetailsService.findByNamedParameters(paramSource);
        if (CollectionUtils.isEmpty(userDetailsDTO)) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetailsDTO(DataAccessUtils.singleResult(userDetailsDTO));
    }
}
