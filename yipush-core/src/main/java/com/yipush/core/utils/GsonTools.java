package com.yipush.core.utils;
import com.google.gson.Gson;
public class GsonTools {

	public static <T>T getGson(String jsonStr, Class<T> cls){
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(jsonStr.trim(), cls);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return t;
	}
}
