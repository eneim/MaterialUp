package io.jari.materialup.utils;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class StringUtils {

    /**
     * Retrieves <code>true</code> in case the given text is empty (null or blank) or equals to "null" or "NULL".
     */
    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0 || text.trim().length() == 0 || "null".equalsIgnoreCase(text);
    }

    /**
     * Get string for number to avoid Exceptions with IDs
     * @param number
     * @return String value of passed number
     */
    public static String stringify(Number number) {
        return number.toString();
    }

}
