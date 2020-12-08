package com.yipush.core.utils;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ly on 12/7/20.
 * Describe:LY
 */
public class JSONUtil {

    public static Map objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;

    }

    public static String marshal(Map<String, String> data) {
        return data==null||data.equals("")?null:JSON.toJSONString(data);
    }
}
