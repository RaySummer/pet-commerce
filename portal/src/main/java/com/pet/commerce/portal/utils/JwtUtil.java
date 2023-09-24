package com.pet.commerce.portal.utils;

import com.pet.commerce.core.utils.SpringUtil;
import com.pet.commerce.portal.module.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Slf4j
@Component
public class JwtUtil implements Serializable {
    private static final long serialVersionUID = 183413845384035416L;


    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.jwtExpirationInS}")
    private int jwtExpirationInS;
    @Value("${jwt.refreshExpirationDateInS}")
    private int refreshExpirationDateInS;
    @Value("${jwt.jwtIdleInS}")
    private int jwtIdleInS;

    //token前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String AUTH_HEADER_KEY = "Authorization";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean isTokenIdle(Date expiration) {
        Long maxIdleTimeLong = expiration.getTime() + jwtIdleInS * 1000;
        final Date maxIdleTime = new Date(maxIdleTimeLong);
        log.warn("maxIdleTime: " + maxIdleTime);
        return maxIdleTime.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, subject);
    }


    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInS * 1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInS * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        //当使用第三方登录时，判断token存的email和第三方绑定的email是否一致
        if (!username.equals(userDetails.getUsername())) {
            return SpringUtil.getBean(MemberService.class).checkMemberExists(userDetails.getUsername(), username) && !isTokenExpired(token);
        }

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

}
