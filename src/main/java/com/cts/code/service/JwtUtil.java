package com.cts.code.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private final String secretkey = "1mICgltqszpJl8zOzMteiVVt2auBiVZqzuRU4dn+13V9fLXJhK1JyE20BXRK5k/T/1Q8mzV4b6DzQnscmAybYhAuE9y4BRPXq4hSrsxlMETYFaCvg0XrSws2+NaddWBRQRfD0+S7qBC7GAOumKJMFms4mkANL7y5oYxoiNgz5e1UF3fdRxRWh/ixqRw6gJ49wJIbO/UnWuM6CAqXXqXscr76rFH0KvhqHO53WtB2eileaYOFFJEBzLK3HsxCcgI59zjITjzZ6Y3XPFL+FgVZmOVxukBRfB+jUfEf8tvhEhP786Xl+PXzrw4lKA9/WV+srKLBU/MPKJ/hG3I8j2J+GHooS/eMiEoRH6geZuZV3WggxTT47+4DLe68tApok49OcxrBFkyWLuqsfVfqdEKbO0Lrt5U1b+ITHI5ACHujp5NEj/OQ4rDstKm2nmlvhG8Kx60KuqDnIxRYVdFX4x98q4df862qcTVcLLTWc7kYOcUpv4Jkbai5aKW8MRea4mEb280kBBKNjvzP+heuwYKxAzsi/Md2D/KPCPy5RUHTOxiQRmsMZEmRZQWzITOjIFsvGzfMpm1waxXeZDn/3bxfR/pbN2llZEZWUcEn+1dy5zl+AM2jvLJt3vdPRLWGBnigb0NajwOcsi2LTy+du1YO0GDop93MOzGTH/JLPu3yMXY=";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = extractAllClaims(token);
			return claimsResolver.apply(claims);
	}

	//retreiving any information from token we willl need the secretkey
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, secretkey).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);

		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public Boolean validateToken(String token) {
		try {
		return !isTokenExpired(token);
	}catch (Exception e) {
		return false;
	}
	}
	public Boolean validate(String token) throws MalformedJwtException  {
		try {
			 Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
			 return true;
			
		}
		catch(Exception e) {
			return false;
		}
		
	}

}
