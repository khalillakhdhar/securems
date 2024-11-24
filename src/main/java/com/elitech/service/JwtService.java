package com.elitech.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.elitech.model.User;
import com.elitech.model.mapper.UserMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtService {
	@Value("${jwt.secret}")
	private String SECERET;
@Autowired
UserService userInfoService;
    public String generateToken(int UserId){
        Map<String, Objects> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(UserId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    public UserDetails getUserFromToken(String token) {
        String userId = extractUserName(token); // Now this returns the user ID
        User user = UserMapper.toReadEntity(userInfoService.getUserById(Integer.parseInt(userId)));
        return new UserInfoDetails(user);
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECERET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName= extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
