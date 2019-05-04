package com.fengjie.courseprogram.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtils {

    public static boolean hasLength(String inStr) {
        return inStr != null && !"".equals(inStr.trim());
    }

    public static boolean isValidEmail(String email) {
        return !isStartWithSpecialChar(email) && email.endsWith(".com") && email.contains("@") && !email.contains(" ");
    }

    public static boolean isStartWithSpecialChar(String inStr) {
        int ascii = (int) inStr.charAt(0);
        if ((48 <= ascii && ascii <= 57) || (97 <= ascii && ascii <= 122) || (65 <= ascii && ascii <= 90)) {
            return false;
        }
        return true;
    }

    //TODO 判断请求路径是否的请求的静态资源,供interceptor使用
    public static boolean isStaticResources(String uri) {
        if (uri.startsWith("/static")) {
            return true;
        }
        if (uri.startsWith("/resources/static")) {
            return true;
        }
        return uri.startsWith("/resources");
    }

    public static boolean isTeacherUri(String uri){
        return uri.startsWith("/teacher");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getDaliyFilePath(Long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = format.format(new Date(timeStamp));
        String year = formatDate.substring(0, 4);
        String mouth = formatDate.substring(5, 7);
        return "/" + year + "/" + mouth;
    }

    public static String getEmailCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 5).toUpperCase();
    }

}
