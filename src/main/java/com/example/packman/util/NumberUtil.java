package com.example.packman.util;

public class NumberUtil {
    public static Double convertAfterComma(Double number, Integer afterComma) {
        double scale = Math.pow(10, afterComma);
        double result = Math.ceil(number * scale) / scale;
        return result;
    }
}
