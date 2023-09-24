package com.pet.commerce.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @since 2023/3/7
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HttpClientResponse implements Serializable {

    // url, 包含?参数
    private String url;
    // 返回状态码
    private int statusCode;
    // 返回头信息
    private Map<String, List<String>> headers;
    // 返回entity内容
    private String entityContent;
}
