package com.yipush.core.utils;


import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyShared {
	private static final String SHAREDNAME = "sanmao";
	/**
	 * 更新内容缓存
	 */
	public static final String updateLog = "updateLog";
	/**
	 * 屏宽
	 */
	public static final String PHONE_WD = "PHONE_WD";
	/**
	 * 屏高
	 */
	public static final String PHONE_HIGHT = "PHONE_HIGHT";
	/**
	 * 账号
	 */
	public static final String USER_NAME = "USER_NAME";
	/**
	 * 账号密码
	 */
	public static final String USER_PASSWORD = "USER_PASSWORD";
	/**
	 * 版本记录
	 */
	public static final String OLD_VERSION_CODE = "OLD_VERSION_CODE";

	/**
	 * 是否测试服
	 */
	public static final String IS_TEST = "IS_TEST";
	/**
	 * token
	 */
	public static final String TOKEN_YIPUSH = "TOKEN_YIPUSH";



	/**
	 * 复制文本缓存  区别站内站外复制
	 */
	public static final String BUFFER_COPY_STR_INMYAPP = "bufferStr_inmyapp";


	 /** 正则表达式 用于匹配属性的第一个字母  **/
    private static final String REGEX = "[a-zA-Z]";




	 private static String convertToMethodName(String attribute, Class objClass, boolean isSet)
	    {
	        /** 通过正则表达式来匹配第一个字符 **/
	        Pattern p = Pattern.compile(REGEX);
	        Matcher m = p.matcher(attribute);
	        StringBuilder sb = new StringBuilder();
	        /** 如果是set方法名称 **/
	        if(isSet)
	        {
	            sb.append("set");
	        }else{
	        /** get方法名称 **/
	            try {
	                Field attributeField = objClass.getDeclaredField(attribute);
	                /** 如果类型为boolean **/
	                if(attributeField.getType() == boolean.class||attributeField.getType() == Boolean.class)
	                {
	                    sb.append("is");
	                }else
	                {
	                    sb.append("get");
	                }
	            } catch (SecurityException e) {
	                e.printStackTrace();
	            } catch (NoSuchFieldException e) {
	                e.printStackTrace();
	            }
	        }
	        /** 针对以下划线开头的属性 **/
	        if(attribute.charAt(0)!='_' && m.find())
	        {
	            sb.append(m.replaceFirst(m.group().toUpperCase()));
	        }else{
	            sb.append(attribute);
	        }
	        return sb.toString();
	    }
	/**删除对象
	 * @param objclass
	 */
	public static void removeObject( Class<?> objclass){
		SharedPreferences sp = YiPushManager.content
				.getSharedPreferences(SHAREDNAME, 0);
		SharedPreferences.Editor editor = sp.edit();
		
//		Class<?> objclass = obj.getClass();
		Field[] fields = objclass.getDeclaredFields();
		for (Field f : fields) {
			try {
				f.setAccessible(true);
				editor.remove(f.getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} 
		}
		editor.commit();
	}
	public static void saveData( String key, Object value) {
		if (value==null) return;
		SharedPreferences sp = YiPushManager.content
				.getSharedPreferences(SHAREDNAME, 0);
		SharedPreferences.Editor editor = sp.edit();
		if (value instanceof String) {
			editor.putString(key, value.toString());
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, Boolean.getBoolean(value.toString()));
		} else if (value instanceof Float) {
			editor.putFloat(key, Float.parseFloat(value.toString()));
		} else if (value instanceof Integer) {
			editor.putInt(key, Integer.parseInt(value.toString()));
		} else if (value instanceof Long) {
			editor.putLong(key, Long.parseLong(value.toString()));
		} else {
			editor.putString(key, value.toString());
		}
		editor.commit();
	}
	public static void removeData(String key) {
		try {
			SharedPreferences sp = YiPushManager.content
					.getSharedPreferences(SHAREDNAME, 0);
			if (sp.contains(key))
				sp.edit().remove(key).commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getString(String key, String defaultValue) {
		SharedPreferences sp = YiPushManager.content
				.getSharedPreferences(SHAREDNAME, 0);
		return sp.getString(key, defaultValue);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		SharedPreferences sp = YiPushManager.content
				.getSharedPreferences(SHAREDNAME, 0);
		return sp.getBoolean(key, defaultValue);
	}

	public static float getFloat(String key, float defaultValue) {
		SharedPreferences sp = YiPushManager.content
				.getSharedPreferences(SHAREDNAME, 0);
		return sp.getFloat(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return int 没有返回-1
	 */
	public static int getInt(String key, int defaultValue) {
		try {
			SharedPreferences sp = YiPushManager.content
					.getSharedPreferences(SHAREDNAME, 0);
			return sp.getInt(key, defaultValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public static long getLong(String key, long defaultValue) {
		SharedPreferences sp = YiPushManager.content
				.getSharedPreferences(SHAREDNAME, 0);
		return sp.getLong(key, defaultValue);
	}
}
