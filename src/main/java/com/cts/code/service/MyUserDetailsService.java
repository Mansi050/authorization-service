package com.cts.code.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.code.entity.AuthenticationRequest;
import com.cts.code.repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
		AuthenticationRequest authenticationRequest = userRepository.findById(username).orElseThrow(null);
		UserDetails user = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(),
				new ArrayList<>());
		return user;
	}catch (Exception e) {
		throw new UsernameNotFoundException("User not found");
		
	}

}
}
