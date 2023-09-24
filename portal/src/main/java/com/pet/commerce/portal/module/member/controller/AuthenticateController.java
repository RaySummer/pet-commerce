package com.pet.commerce.portal.module.member.controller;

import com.pet.commerce.core.module.member.model.Member;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.core.utils.Response;
import com.pet.commerce.portal.module.member.dto.ro.JwtRequestRO;
import com.pet.commerce.portal.module.member.dto.ro.MobileNumberJwtRequestRO;
import com.pet.commerce.portal.module.member.dto.vo.JwtResponseVO;
import com.pet.commerce.portal.module.member.service.JwtUserDetailsService;
import com.pet.commerce.portal.module.member.service.MemberService;
import com.pet.commerce.portal.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Slf4j
@CrossOrigin
@RestController
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private MemberService memberService;


    @PostMapping("/authenticate")
    public Response authenticateJWT(@RequestBody JwtRequestRO jwtRequestRO) {
        String userName = jwtRequestRO.getUsername();
        boolean isInvalidCredential = false;
        try {
            Authentication auth = authenticate(jwtRequestRO.getUsername(), jwtRequestRO.getPassword());
        } catch (CustomizeException customizeException) {
            isInvalidCredential = true;
        }
        if (isInvalidCredential) {
            String jwtToken = jwtRequestRO.getPassword();
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);
            try {
                if (!isValidToken(jwtToken, userDetails)) {
                    return Response.ofError("INVALID_CREDENTIALS");
                }
            } catch (Exception exception) {
                log.error(exception.getMessage());
                return Response.ofError(exception.getMessage());
            }
        }

        final String token = jwtUtil.generateToken(userName);

        final String refreshToken = jwtUtil.doGenerateRefreshToken(new HashMap<>(), userName);

        return Response.of(new JwtResponseVO(token, refreshToken, new Date()));
    }

    @PostMapping("/authenticate-mobile")
    public Response authenticateByMobileNumber(@RequestBody MobileNumberJwtRequestRO jwtRequestRO) {
        String userName = jwtRequestRO.getUsername();

        Member member = memberService.findMemberByAccount(userName);

        if (member == null) {
            member = memberService.register(userName);
        }

        boolean isInvalidCredential = false;
        try {
            Authentication auth = authenticate(member.getAccount(), userName.substring(userName.length() - 5));
        } catch (CustomizeException customizeException) {
            isInvalidCredential = true;
        }
        if (isInvalidCredential) {
            String jwtToken = member.getPassword();
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);
            try {
                if (!isValidToken(jwtToken, userDetails)) {
                    return Response.ofError("INVALID_CREDENTIALS");
                }
            } catch (Exception exception) {
                log.error(exception.getMessage());
                return Response.ofError(exception.getMessage());
            }
        }

        final String token = jwtUtil.generateToken(userName);

        final String refreshToken = jwtUtil.doGenerateRefreshToken(new HashMap<>(), userName);

        return Response.of(new JwtResponseVO(token, refreshToken, new Date()));
    }

    private Authentication authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.warn("USER_DISABLED");
            throw new CustomizeException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.warn("INVALID_CREDENTIALS");
            throw new CustomizeException(e.getMessage());
        }
        return auth;
    }

    private boolean isValidToken(String jwtToken, UserDetails userDetails) {
        if (jwtUtil.validateToken(jwtToken, userDetails)) {
            return true;
        }
        return false;
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        String token = jwtUtil.generateToken(name);

        String refreshToken = jwtUtil.doGenerateRefreshToken(new HashMap<>(), name);

        return ResponseEntity.ok(new JwtResponseVO(token, refreshToken, new Date()));
    }


}
