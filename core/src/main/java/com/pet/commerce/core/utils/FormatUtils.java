package com.pet.commerce.core.utils;

import java.util.List;
import java.util.Random;

/**
 * @author Ray
 * @since 2022/3/2
 */
public class FormatUtils {

    public static String timeFormatHMS(Long second) {
        if (second == null) {
            return "00:00:00";
        }
        long h = second / 3600;
        long m = second % 3600 / 60;
        long s = second % 60;
        return h + ":" + m + ":" + s;
    }

    public static String getItemID(int n) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
            if ("char".equalsIgnoreCase(str)) {
                // 产生字母
                int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (nextInt + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(str)) {
                // 产生数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static String ListFormatSqlString(List<String> list) {
        StringBuffer sb = new StringBuffer();
        for (String str : list) {
            sb.append("'");
            sb.append(str);
            sb.append("',");
        }
        return sb.toString().substring(0, sb.toString().lastIndexOf(","));
    }

}
