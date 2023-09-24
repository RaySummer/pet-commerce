package com.pet.commerce.core.utils;

import com.google.common.collect.Lists;
import liquibase.repackaged.org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author: Ray
 * @time: 2021/5/17
 **/
@Slf4j
public class HttpClientUtil {

    private static String proxyScheme = "http";

    private static String proxyHost = "127.0.0.1";

    private static String proxyPort = "1080";

    private static final ContentType STRING_CONTENT_TYPE = ContentType.create("text/plain", StandardCharsets.UTF_8);

//    @Value("${proxy.scheme}")
//    public static void setProxyScheme(String proxyScheme) {
//        HttpClientUtil.proxyScheme = proxyScheme;
//    }
//
//    @Value("${proxy.host}")
//    public static void setProxyHost(String proxyHost) {
//        HttpClientUtil.proxyHost = proxyHost;
//    }
//
//    @Value("${proxy.port}")
//    public static void setProxyPort(String proxyPort) {
//        HttpClientUtil.proxyPort = proxyPort;
//    }

    //增加token访问
    public static String doPost(String url, String jsonStr, String charset, String access_token) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(url);
            //划重点！！！！！
            //不要设置header的content-type！！否则请求失败！！！靠
            if (StringUtils.isNotEmpty(jsonStr)) {
                StringEntity se = new StringEntity(jsonStr);
                se.setContentType("text/json");
                se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
                httpPost.setEntity(se);
            }
            if (StringUtils.isNotEmpty(access_token)) {
                httpPost.addHeader("access_token", access_token);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static String doPost(String url, String jsonStr, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(url);
            //划重点！！！！！
            if (StringUtils.isNotEmpty(jsonStr)) {
                StringEntity se = new StringEntity(jsonStr);
                se.setContentType("text/json");
                se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
                httpPost.setEntity(se);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 传入代理配置的post请求（附带token）
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static String doPost(String url, String jsonStr, String token, boolean isEnableProxy) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpPost = new HttpPost(url);
            //不要设置header的content-type！！否则请求失败！！！靠
            if (StringUtils.isNotEmpty(jsonStr)) {
                StringEntity se = new StringEntity(jsonStr);
                se.setContentType("text/json");
                se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
                httpPost.setEntity(se);
            }
            if (StringUtils.isNotEmpty(token)) {
                httpPost.addHeader("Authorization", "Bearer " + token);
            }

            if (Boolean.parseBoolean(String.valueOf(isEnableProxy))) {
                httpClient = getProxyHttpClient();
            } else {
                httpClient = HttpClientBuilder.create().build();
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 传入代理配置的post请求
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static String doPost(String url, String jsonStr, boolean isEnableProxy) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpPost = new HttpPost(url);
            //不要设置header的content-type！！否则请求失败！！！靠
            if (StringUtils.isNotEmpty(jsonStr)) {
                StringEntity se = new StringEntity(jsonStr);
                se.setContentType("text/json");
                se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
                httpPost.setEntity(se);
            }

            if (Boolean.parseBoolean(String.valueOf(isEnableProxy))) {
                httpClient = getProxyHttpClient();
            } else {
                httpClient = HttpClientBuilder.create().build();
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static String doGet(String url, String charset) {
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    public static HttpClient getProxyHttpClient() {
        HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort), proxyScheme);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                new AuthScope(proxy),
//                new UsernamePasswordCredentials(dto.getUsername(), dto.getPassword())
//        );
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder
                .setProxy(proxy)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setConnectionTimeToLive(1, TimeUnit.MINUTES)
                .disableCookieManagement();
        return clientBuilder.build();
    }

    public static Object multipartPost(String url, Map<String, String> headers, Map<String, Object> paramMap) {
        // 创建 HttpPost 对象
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
        httpPost.setConfig(requestConfig);
        // 设置请求参数
        if (MapUtils.isNotEmpty(paramMap)) {
            // 使用 MultipartEntityBuilder 构造请求体
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            //设置浏览器兼容模式
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //设置请求的编码格式
            builder.setCharset(Consts.UTF_8);
            // 设置 Content-Type
            builder.setContentType(ContentType.MULTIPART_FORM_DATA);
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                String key = entry.getKey();
                Object value = paramMap.get(key);
                // 添加请求参数
                addMultipartBody(builder, key, value);
            }
            HttpEntity entity = builder.build();
            // 将构造好的 entity 设置到 HttpPost 对象中
            httpPost.setEntity(entity);
        }

        return execute(httpPost, null);
    }

    private static HttpClientResponse execute(HttpRequestBase httpRequestBase, HttpContext context) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 使用 try-with-resources 发起请求，保证请求完成后资源关闭
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequestBase, context)) {
            log.warn("httpResponse:{}", httpResponse);
            // 处理响应头
            Map<String, List<String>> headers = headerToMap(httpResponse.getAllHeaders());
            // 处理响应体
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                String entityContent = EntityUtils.toString(httpEntity, Consts.UTF_8);
                log.warn("content is : {}", entityContent);
                return new HttpClientResponse(httpRequestBase.getRequestLine().getUri(), httpResponse.getStatusLine().getStatusCode(), headers, entityContent);
            }
        } catch (Exception ex) {
            log.error("http execute failed.:{}", ex.getMessage());
        }
        return new HttpClientResponse(httpRequestBase.getRequestLine().getUri(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null, "http execute failed.");
    }

    private static void addMultipartBody(MultipartEntityBuilder builder, String key, Object value) {
        if (value == null) {
            return;
        }
        // MultipartFile 是 spring mvc 接收到的文件。
        if (value instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) value;
            try {
                builder.addBinaryBody(key, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
            } catch (IOException e) {
                log.error("read file err.", e);
            }
        } else if (value instanceof File) {
            File file = (File) value;
            builder.addBinaryBody(key, file, ContentType.MULTIPART_FORM_DATA, file.getName());
        } else if (value instanceof List) {
            // 列表形式的参数，要一个一个 add
            List<?> list = (List<?>) value;
            for (Object o : list) {
                addMultipartBody(builder, key, o);
            }
        } else if (value instanceof Date) {
            // 日期格式的参数，使用约定的格式
            builder.addTextBody(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
        } else {
            // 使用 UTF_8 编码的 ContentType，否则可能会有中文乱码问题
            builder.addTextBody(key, value.toString(), STRING_CONTENT_TYPE);
        }
    }

    /**
     * 将headers转map
     *
     * @param headers 头信息
     * @return map
     */
    private static Map<String, List<String>> headerToMap(Header[] headers) {
        if (null == headers || headers.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, List<String>> map = new HashMap<>();
        for (Header header : headers) {
            map.putIfAbsent(header.getName(), Lists.newArrayList(header.getValue()));
        }
        return map;
    }

}
