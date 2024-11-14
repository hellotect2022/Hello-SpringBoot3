package com.dhhan.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class LogHelper {

    public static Marker marker = MarkerFactory.getMarker("STDOUT");

    public static void debug(String msg, Object ref) {
        Logger LOGGER = LoggerFactory.getLogger(ref.getClass());
        LOGGER.debug(marker,"({}) # {}", ref.getClass().getName(),msg);
    }

    public static void info(String msg, Object ref) {
        Logger LOGGER = LoggerFactory.getLogger(ref.getClass());
        LOGGER.info(marker,"({}) # {}", ref.getClass().getName(),msg);
    }

    public static void info(Object obj, Object ref) throws IllegalAccessException {
        Logger LOGGER = LoggerFactory.getLogger(ref.getClass());
        if (obj != null) {
            LOGGER.info("========== {} ==========", obj.getClass().getName());
            if (obj instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) obj; // Map으로 타입 캐스팅
                StringBuilder str= new StringBuilder();
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    str.append(key).append(": ").append(value).append(", "); // 필드 이름과 값 출력
                }
                LOGGER.info("{{}}", str);
            }else if (obj instanceof List) {
                List<?> list = (List<?>) obj; // List로 타입 캐스팅
                for (Object item : list) {
                    LOGGER.info("List Item: {}", item);
                }
            }else {
                Field[] fields = obj.getClass().getDeclaredFields();
                StringBuilder str= new StringBuilder();
                for (Field field : fields) {
                    field.setAccessible(true); // private 필드 접근
                    String fieldName = field.getName();
                    Object fieldValue = field.get(obj);
                    str.append(fieldName).append(": ").append(fieldValue).append(", "); // 필드 이름과 값 출력
                }
                LOGGER.info("{{}}", str);
            }
        }
    }

    public static void error(Exception e) {
        Logger LOGGER = LoggerFactory.getLogger(LogHelper.class);
        StackTraceElement[] stackTraces2 = new Throwable().getStackTrace();
        LOGGER.error(stackTraces2[0].toString());
        LOGGER.error(stackTraces2[1].toString());


        LOGGER.error("Error occurred:", e.toString());
        int loop  = e.getStackTrace().length > 8 ? 8: e.getStackTrace().length;
        for (int i = 0; i < loop; i++) {
            LOGGER.error("-> " + e.getStackTrace()[i]);
        }
    }
}
