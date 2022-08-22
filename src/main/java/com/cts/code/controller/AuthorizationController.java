package com.cts.code.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.code.dto.AuthorizationResponse;
import com.cts.code.entity.AuthenticationRequest;
import com.cts.code.entity.AuthenticationResponse;
import com.cts.code.service.JwtUtil;
import com.cts.code.service.MyUserDetailsService;

import io.jsonwebtoken.MalformedJwtException;

@RestController
@CrossOrigin(origins = "*")
public class AuthorizationController {
	
	Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	private AuthorizationResponse response = new AuthorizationResponse();

	@GetMapping("/get-health")
	public ResponseEntity<?> healthCheckMethod() {
		logger.debug("Health check method");
		return ResponseEntity.ok("Health check successful");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
		
		logger.info("START");

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception e) {
			logger.info("User is not validated hence not Authorized");
			return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		if(userDetails.getPassword().equals(authRequest.getPassword())) {
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		logger.info("User is logged in and authorized");
		logger.info("END");
		return ResponseEntity.ok(new AuthenticationResponse(authRequest.getUsername(), jwt));
		}else {
			return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
		}

	}
	

	@GetMapping(path = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorizationResponse> validatingAuthorizationToken(
			@RequestHeader(name = "Authorization") String tokenDup){
		String token = tokenDup.substring(7);
		try {
			UserDetails user = userDetailsService.loadUserByUsername(jwtTokenUtil.extractUsername(token));
			if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(token,user))) {
				response.setValidStatus(true);
				logger.info("Valid User");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setValidStatus(false);
				logger.info("Invalid User");
				return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			response.setValidStatus(false);
			logger.info("Invalid User");
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}
	}

}
