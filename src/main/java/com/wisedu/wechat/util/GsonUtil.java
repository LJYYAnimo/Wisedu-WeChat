package com.wisedu.wechat.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘金泳
 * @Date 2019/7/24
 */
public class GsonUtil{

    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new GsonBuilder().create();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    public static <T> Map<String,Object> JsonToMap(String json) {
        Gson gson = new GsonBuilder().create();
        //TODO
        Map<String,Object> map = gson.fromJson(json,new TypeToken<HashMap<String,Object>>(){}.getType());
        return map;
    }
}
