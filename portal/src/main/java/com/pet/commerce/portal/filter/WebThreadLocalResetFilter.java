//package com.pet.commerce.portal.filter;
//
//import com.pet.commerce.core.module.member.dto.MemberDto;
//import com.pet.commerce.core.utils.WebThreadLocal;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author Ray
// * @since 2023/3/24
// */
//@Slf4j
//@Component
//@Order(FilterOrderConstants.WEB_THREAD_LOCAL_RESET_ORDER)
//public class WebThreadLocalResetFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        WebThreadLocal.init();
//        try {
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            log.error("", e);
//        } finally {
//            // 清除有的thread local的值，防止内存泄露
//            WebThreadLocal.remove();
//        }
//    }
//
//}
