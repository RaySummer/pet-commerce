package com.pet.commerce.portal.filter.jwt;

import com.pet.commerce.core.module.member.dto.MemberDto;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.member.dto.vo.MemberVO;
import com.pet.commerce.portal.module.member.service.JwtUserDetailsService;
import com.pet.commerce.portal.module.member.service.MemberService;
import com.pet.commerce.portal.utils.JwtUtil;
import com.pet.commerce.core.constants.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Lazy
    @Autowired
    private MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        try {
            // OPTIONS 的请求都通过
            if (request.getMethod().equalsIgnoreCase(RequestMethod.OPTIONS.name())) {
                chain.doFilter(request, response);
            }
            WebThreadLocal.init();

            // 设置浏览器指纹
            String browserFingerprint = request.getHeader(Constants.COOKIE_NAME_BROWSER_FINGERPRINT);
            if (browserFingerprint != null) {
                WebThreadLocal.setBrowserFingerprint(browserFingerprint);
            }

            final String requestTokenHeader = request.getHeader(Constants.HEADER_TOKEN_NAME);

            String username = null;
            String jwtToken = null;
            // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
            if (requestTokenHeader != null && requestTokenHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    logger.warn("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    logger.warn("JWT Token has expired");

                    DefaultClaims claims = (DefaultClaims) e.getClaims();
                    Date expiration = claims.getExpiration();

                    String isRefreshToken = request.getHeader("isRefreshToken");
                    String requestURL = request.getRequestURL().toString();
                    // allow for Refresh Token creation if following conditions are true.
                    if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshToken")) {
                        if (!jwtUtil.isTokenIdle(expiration)) {
                            allowForRefreshToken(e, request);
                        } else {
                            logger.warn("Exceed idle time. Can never be used to generate refresh token");
                            request.setAttribute("exception", "----Exceed idle time. Can never be used to generate refresh token.");
                        }
                    } else {
                        request.setAttribute("exception", e);
                    }
                }
            } else {
                logger.warn("JWT Token does not begin with Bearer String");
            }

            //Once we get the token validate it.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

                // if token is valid configure Spring Security to manually set authentication
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    MemberVO memberVO = memberService.getMemberVOByAccount(userDetails.getUsername());
                    MemberDto memberDto = new MemberDto();
                    BeanUtils.copyProperties(memberVO, memberDto);
                    memberDto.setUid(UUID.fromString(memberVO.getUid()));
                    WebThreadLocal.setMember(memberDto);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }


    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());

    }

}
