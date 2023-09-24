package com.pet.commerce.portal.filter;

import com.alibaba.fastjson.JSON;
import com.pet.commerce.core.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ray
 * @since 2023/3/24
 */
public interface FilterException {

    Logger log = LoggerFactory.getLogger(FilterException.class);


    default void errorResponse(ServletResponse response, HttpStatus httpStatus, String errorMsg) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(httpStatus.value());
            httpServletResponse.setContentType("application/json");
            Response errorResponse = Response.ofError(errorMsg);
            httpServletResponse.getWriter().write(JSON.toJSONString(errorResponse));
            httpServletResponse.getWriter().flush();
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
