package Helpers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataConvertor {
    private static final Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> List<T> fromJsonArray(String json, Class<T[]> clazz) {
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, type);
    }

    public static int[] toIntArray(String json) {
        return fromJson(json, int[].class);
    }

    public static String[] toStringArray(String json) {
        return fromJson(json, String[].class);
    }

    public static char[] toCharArray(String json){
        return fromJson(json, char[].class);
    }

    public static int[][] to2DIntArray(String json) {
        return fromJson(json, int[][].class);
    }

    public static String[][] to2DStringArray(String json) {
        return fromJson(json, String[][].class);
    }

    public static int toInt(String json){
        return fromJson(json, int.class);
    }

    public static long toLong(String json){
        return fromJson(json, long.class);
    }

    public static float toFloat(String json){
        return fromJson(json, float.class);
    }
}
