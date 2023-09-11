package com.tcc.Hive.security.utils;

import com.tcc.Hive.dto.UserAuthenticationDTO;
import com.tcc.Hive.user.UserHive;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.util.Date;

@Component
public class JWTUtils {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;
    private String SECRET_KEY = "golfinho";

    public String generateAcessToken(UserHive user){

        return Jwts.builder()
                .claim("username",user.getEmail())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims getClaims(String token){
        Claims claims;
        try{
            claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch(Exception e){
            System.out.println(e.getMessage());
            claims = null;
        }
        return claims;
    }
    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    public Boolean tokenExpired(String token){
        Date expired = getClaims(token).getExpiration();
        return expired.before(new Date());
    }

    public boolean validateToken(String token){
        if((token.isEmpty() || token.isBlank() || token == null)){
           return false;
        }
        if(getClaims(token) == null){
            return false;
        }
        if(tokenExpired(token) || getSubject(token).isBlank()){
            return false;
        }
        return true;
    }
}
