package com.pet.commerce.core.redis;

import com.pet.commerce.core.utils.CustomizeException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @since 2023-3-3
 */
public class CacheUtils {
    private CacheUtils() {
    }

    /**
     * 通过反射获取属性值
     *
     * @param fieldName 属性名
     * @param methods   目标对象的所有方法
     * @param obj       目标对象
     * @return 属性值
     * @throws IllegalAccessException 反射异常
     */
    public static Object getFieldValue(String fieldName, Method[] methods, Object obj) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase("get" + fieldName)) {
                //通过属性的get()获取属性值
                return method.invoke(obj);
            }
        }
        //未找到属性
        throw new CustomizeException("\"" + obj.getClass().getName() + "." + fieldName + "\" field not found or field has no get method");
    }

    /**
     * 获取缓存 Key
     *
     * @param prefix 缓存 key 前缀
     * @param unique 缓存对象的唯一索引字段的属性名
     * @param v      唯一索引字段的属性值
     * @return 缓存 Key
     */
    public static String generateKey(String prefix, String unique, Object v) {
        return prefix + ":" + unique + ":" + v.toString();
    }

    /**
     * 获取缓存对象的所有缓存 Key
     *
     * @param prefix  缓存 key 前缀
     * @param uniques 缓存对象的唯一索引字段的属性名
     * @param obj     缓存对象
     * @return obj的所有缓存 Key
     * @throws IllegalAccessException 反射异常
     */
    public static List<String> allCacheKey(String prefix, String[] uniques, Object obj) throws IllegalAccessException, InvocationTargetException {
        Class<?> objClass = obj.getClass();
        Method[] methods = objClass.getMethods();

        List<String> list = new ArrayList<>();

        Object id = getFieldValue("id", methods, obj);
        String dataKey = generateKey(prefix, "data", id);
        String idKey = generateKey(prefix, "id", id);
        list.add(dataKey);
        list.add(idKey);

        Object uid = getFieldValue("uid", methods, obj);
        String uidKey = generateKey(prefix, "uid", uid);
        list.add(uidKey);

        for (String unique : uniques) {
            Object v = getFieldValue(unique, methods, obj);
            if (v != null) {
                String uniqueKey = generateKey(prefix, unique, v);
                list.add(uniqueKey);
            }
        }

        return list;
    }

    /**
     * 获取缓存对象的所有待缓存映射
     *
     * @param prefix  缓存 key 前缀
     * @param uniques 缓存对象的唯一索引字段的属性名
     * @param obj     缓存对象
     * @return obj的所有待缓存映射
     * @throws IllegalAccessException 反射异常
     */
    public static Map<String, Object> allCacheMap(String prefix, String[] uniques, Object obj) throws IllegalAccessException, InvocationTargetException {
        Class<?> objClass = obj.getClass();
        Method[] methods = objClass.getMethods();

        Map<String, Object> map = new HashMap<>();

        Object id = getFieldValue("id", methods, obj);
        String dataKey = generateKey(prefix, "data", id);
        String idKey = generateKey(prefix, "id", id);
        map.put(dataKey, obj);
        map.put(idKey, dataKey);

        Object uid = getFieldValue("uid", methods, obj);
        String uidKey = generateKey(prefix, "uid", uid);
        map.put(uidKey, dataKey);

        for (String unique : uniques) {
            Object v = getFieldValue(unique, methods, obj);
            if (v == null || (v instanceof String && StringUtils.isBlank((String) v))) {
                continue;
            }
            String uniqueKey = generateKey(prefix, unique, v);
            map.put(uniqueKey, dataKey);
        }

        return map;
    }

    public static String getDefaultPrefix(JoinPoint joinPoint) {
        Object service = joinPoint.getTarget();
        String simpleName = service.getClass().getSimpleName();
        if (simpleName.endsWith("CoreService")) {
            simpleName = simpleName.replaceFirst("CoreService$", "");
        } else if (simpleName.endsWith("Service")) {
            simpleName = simpleName.replaceFirst("Service$", "");
        }
        return Character.toLowerCase(simpleName.charAt(0)) + (simpleName.length() > 1 ? simpleName.substring(1) : "");
    }

}
