package com.pet.commerce.core.utils;

import com.pet.commerce.core.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.pet.commerce.core.constants.Constants.RUNTIME_ENV_LOCAL;
import static com.pet.commerce.core.constants.Constants.URL_SEPARATOR;


/**
 * @author hary
 * @since 2018-10-6
 */
public class StringUtil {

    private static final Pattern HUMP_PATTEN = Pattern.compile("(?<=[a-z])[A-Z]");

    /**
     * 拼接字符串
     *
     * @param delimiter 连接符
     * @param elements  字符串
     */
    public static String join(CharSequence delimiter, String... elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        return Arrays.stream(elements).filter(s -> s != null && !s.trim().isEmpty()).map(String::trim)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * 拼接字符串
     *
     * @param delimiter  连接符
     * @param collection 字符串
     */
    public static String join(CharSequence delimiter, Collection<String> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }
        Objects.requireNonNull(delimiter);
        return collection.stream().filter(s -> s != null && !s.trim().isEmpty()).map(String::trim)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * 拼接字符串
     *
     * @param delimiter  连接符
     * @param collection 字符串
     * @param mapper     转化器
     */
    public static <T> String join(CharSequence delimiter, Collection<T> collection, Function<? super T, String> mapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }
        Objects.requireNonNull(delimiter);
        return collection.stream()
                .map(mapper)
                .filter(s -> s != null && !s.trim().isEmpty()).map(String::trim)
                .collect(Collectors.joining(delimiter));
    }

    public static String joinName(String... elements) {
        return join(" ", elements);
    }

    public static String joinAddress(String... elements) {
        return join(URL_SEPARATOR, elements);
    }

    /**
     * 拼接链接
     *
     * @param path 路径
     */
    public static String joinUrl(String... path) {
        StringBuilder join = new StringBuilder();
        for (int i = 0; i < path.length; i++) {
            String s = path[i];
            if (StringUtils.isBlank(s)) {
                continue;
            }
            if (i == 0) {
                join.append(s);
            } else {
                int lastIndex = join.length() - 1;
                if (lastIndex > -1 && join.lastIndexOf(URL_SEPARATOR) == lastIndex) {
                    // 删除最后的斜杠
                    join.deleteCharAt(lastIndex);
                }
                join.append(s.startsWith(URL_SEPARATOR) ? s : URL_SEPARATOR + s);
            }
        }
        return join.toString();
    }

    public static String joinUrl(List<String> path) {
        return joinUrl(path.toArray(new String[0]));
    }

    /**
     * 驼峰->下划线
     * e.g. showFacebookIcon -> show_facebook_icon
     */
    public static String humpToUnderline(String str) {
        return humpConvert(str, "_").toLowerCase();
    }

    /**
     * 驼峰拆分
     *
     * @param str       字符串
     * @param separator 分隔符
     */
    public static String humpConvert(String str, String separator) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        Matcher m = HUMP_PATTEN.matcher(str.trim());
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, separator + m.group());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static boolean isEmail(String email) {
        return email != null && Pattern.matches(Constants.EMAIL_PATTERN, email);
    }

    /**
     * 查找字符
     */
    public static int indexOf(String str, String symbol, int jump) {
        if (str == null || symbol == null) {
            return -1;
        }

        int index = 0;
        do {
            int i = str.indexOf(symbol, index + 1);
            if (i == -1) {
                return -1;
            }
            index = i;
        } while (--jump > 0);

        return index;
    }

    /**
     * 根路径是否相同
     */
    public static boolean uriRootPathEq(String uri, String compareStr) {
        if (uri == null || compareStr == null) {
            return false;
        }
        if (uri.startsWith(URL_SEPARATOR)) {
            uri = uri.substring(1);
        }
        if (compareStr.startsWith(URL_SEPARATOR)) {
            compareStr = compareStr.substring(1);
        }
        String[] uriSplits = uri.split(URL_SEPARATOR);
        String[] compareStrSplits = compareStr.split(URL_SEPARATOR);
        return uriSplits[0].equals(compareStrSplits[0]);
    }

    public static String addSeparatorIfNot(String string) {
        return string.startsWith(URL_SEPARATOR) ? string : URL_SEPARATOR + string;
    }

    public static String removeStartSeparator(String string) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        return string.startsWith(URL_SEPARATOR) ? string.substring(1) : string;
    }

    /**
     * 是否以请求协议开头
     */
    public static boolean startWithRequestProtocol(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("//");
    }

    /**
     * 转为绝对路径，拼接 /
     */
    public static String toAbsolutePath(String path) {
        if (StringUtils.isBlank(path)) {
            return path;
        }
        if (startWithRequestProtocol(path)) {
            return path;
        }
        return path.startsWith(URL_SEPARATOR) ? path : URL_SEPARATOR + path;
    }

    /**
     * 转为父路径
     */
    public static String toParentPath(String path) {
        return path.endsWith(URL_SEPARATOR) ? path : path + URL_SEPARATOR;
    }

    /**
     * 截短字符串
     */
    public static String littleDesc(String text) {
        int maxLength = 200;
        if (text == null) {
            return "";
        }
        if (text.codePointCount(0, text.length()) < maxLength) {
            return text;
        }
        return text.substring(text.offsetByCodePoints(0, 0), text.offsetByCodePoints(0, maxLength));
    }

    /**
     * 去掉 url 末尾没用的斜杠
     */
    public static String substringEndSeparator(String path) {
        while (path.endsWith(URL_SEPARATOR)) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 链接拼接查询参数
     *
     * @param baseUrl 链接
     * @param params  查询参数
     */
    public static String appendUrlParam(String baseUrl, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return baseUrl;
        }
        String paramStr = params.entrySet().stream()
                .map(item -> {
                    String paramVal = item.getValue();
                    try {
                        paramVal = URLEncoder.encode(item.getValue() == null ? "" : item.getValue(),
                                StandardCharsets.UTF_8.displayName());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return item.getKey() + "=" + paramVal;
                }).collect(Collectors.joining("&"));
        return baseUrl.endsWith("?") ? baseUrl + paramStr : baseUrl + "?" + paramStr;
    }

    public static String getUniqueStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String joinOSSDomain(String... elements) {
        return join(".", elements);
    }

    public static String joinCompanyDomain(String subdomain, String baseDomain) {
        return join(".", subdomain, baseDomain);
    }

    public static boolean isValidUuid(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return false;
        }
        String[] components = uuid.split("-");
        return components.length == 5;
    }

    public static boolean isValidAddress(String addr) {
        String regex = "^0x[0-9a-fA-F]{40}$";
        if (addr.matches(regex)) {
            return true;
        }
        return false;
    }

    public static String parseSubdomain(String hostname) {
        if (StringUtils.isBlank(hostname)) {
            return null;
        }
        int index = hostname.indexOf(".");
        if (index == -1) {
            return null;
        }
        return hostname.substring(0, index);
    }

    public static String toUrl(String domain, String env) {
        if (StringUtil.startWithRequestProtocol(domain)) {
            return domain;
        }
        boolean localMode = StringUtils.equalsAnyIgnoreCase(env, RUNTIME_ENV_LOCAL);
        String requestSchema = localMode ? "http" : "https";
        return String.format("%s://%s", requestSchema, domain);
    }

    /**
     * 每个单词首字母大写
     *
     * @param input
     */
    public static String toCapitalizeFirstLetter(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        }
        StringBuilder ret = new StringBuilder();
        String[] split = input.split(" ");
        for (int i = 0; i < split.length; i++) {
            ret.append(split[i].substring(0, 1).toUpperCase()).append(split[i].substring(1).toLowerCase());
            if (i != (split.length - 1)) {
                ret.append(" ");
            }
        }
        return ret.toString();
    }

    /**
     * 根据Key查找字符串内出现的次数
     *
     * @param str
     * @param key
     * @return
     */
    public static int findKeyCountByStr(String str, String key) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(key)) {
            return 0;
        }
        return str.split(key).length - 1;
    }

    public static void main(String[] args) {
        String one = "rwdasdas";
        String str = "rwdasdas1dafdsadsas2dasffdasdqf3wd";

        int a = str.indexOf(one) + one.length();
        System.out.println(a);

        System.out.println(str.substring(one.length()));

    }
}
