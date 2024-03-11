package edu.pnu.myspring.dispatcher;

import com.google.gson.Gson;

public class MyJsonMapper {

    static Gson gson = new Gson();



    public static <T> T fromJson(String jsonString, Class<T> clazz) {

        return gson.fromJson(jsonString, clazz);

    }



    public static String toJson(Object result) {

        return gson.toJson(result);

    }

}

