package Helpers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class DataConvertor {
    private static final Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
			return gson.fromJson(json, clazz);
		} catch (JsonSyntaxException e) {
			System.out.println("Error while converting this json: " + json + "error message: " + e.getMessage());
            return null;
		}
    }

    public static <T> List<T> fromJson(String json, Type type) {
        try {
			return gson.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			System.out.println("Error while converting this json: " + json + "error message: " + e.getMessage());
            return null;
		}
    }

    public static <T> List<List<T>> toListOfList(String json, Class<T> clazz) {
        try {
			Type type = TypeToken.getParameterized(List.class, TypeToken.getParameterized(List.class, clazz).getType()).getType();
			return gson.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			System.out.println(e.getMessage());
            return null;
		}
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
			Type type = TypeToken.getParameterized(List.class, clazz).getType();
			return gson.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			System.out.println(e.getMessage());
            return null;
		}
    }

    public static <T> T[] toTypeArray(String json, Class<T> clazz){
        try {
			Type type = TypeToken.getArray(clazz).getType();
			return gson.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			System.out.println(e.getMessage());
            return null;
		}
    }

    public static int[] toIntArray(String json) {
        return fromJson(json, int[].class);
    }

    public static char[] toCharArray(String json){
        return fromJson(json, char[].class);
    }

    public static double[] toDoubleArray(String json){
        return fromJson(json, double[].class);
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

    public static double toDouble(String json){
        return fromJson(json, double.class);
    }
}
