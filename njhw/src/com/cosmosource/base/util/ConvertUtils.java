package com.cosmosource.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import com.cosmosource.base.util.ReflectionUtil;

@SuppressWarnings({"unchecked","rawtypes"})
public class ConvertUtils {

	static {
		registerDateConverter();
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtil.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtil.convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 定义日期Converter的格式: yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
	 */
	private static void registerDateConverter() {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
		org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
	}
	
	/**
	 * @描述: 将POJO对象转成Map
	 * @作者： WXJ
	 * @param POJO对象
	 * @return
	 */
	public static Map pojoToMap(Object obj) {
		Map hashMap = new HashMap();
		try {
			Class c = obj.getClass();
			Method m[] = c.getDeclaredMethods();
			
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().indexOf("get") == 0) {
//					 System.out.println("方法名："+m[i].getName().substring(3,4).toLowerCase()+m[i].getName().substring(4));
//					 System.out.println("值："+ m[i].invoke(obj, new
//					 Object[0]));
					hashMap
							.put(m[i].getName().substring(3,4).toLowerCase()+m[i].getName().substring(4), m[i]
									.invoke(obj, new Object[0]));
				}
			}
			if(!"java.lang.Object".equals(c.getSuperclass().getName())){
				Method sm[] = c.getSuperclass().getDeclaredMethods();
				
				for (int i = 0; i < sm.length; i++) {
					if (sm[i].getName().indexOf("get") == 0) {
						hashMap
								.put(sm[i].getName().substring(3,4).toLowerCase()+sm[i].getName().substring(4), sm[i]
										.invoke(obj, new Object[0]));
					}
				}
			}
			
		} catch (Exception e) {
			System.err.println(e);
		}
		return hashMap;
	}
	public static void copyValue(Object from, Object to){
		
		
//		try {
//			PropertyUtils.copyProperties(to, from);//BeanUtils.copyProperties(to, from);
//		} catch (IllegalAccessException e1) {
//			e1.printStackTrace();
//		} catch (InvocationTargetException e1) {
//			e1.printStackTrace();
//		} catch (NoSuchMethodException e1) {
//			e1.printStackTrace();
//		}
		
		Class dataClass = from.getClass();
		Class formClass = to.getClass();
		Field[] fs = formClass.getDeclaredFields();

		for(int i = 0; i < fs.length; i ++){
			Field f = (Field)fs[i];					
			try{ 
				if("logger".equals(f.getName())
					|| "class".equals(f.getName()) 
					|| f.getName().indexOf("class$") > 0) continue;
				Method setm = null;
			
				Method getm = dataClass.getMethod("get" + getProperty(f.getName()), new Class[]{});
				Object o = getm.invoke(from, new Object[]{});
			
				setm = formClass.getMethod("set" + getProperty(f.getName()), new Class[]{f.getType()});
				
				setm.invoke(to, new Object[]{o});
				
			} catch (NullPointerException e) {				
			} catch (NoSuchMethodException e) {		
			} catch(Exception ex){}
		}
	}
	public static String getProperty(String fieldName){
		char ch = fieldName.charAt(0);
		ch = Character.toUpperCase(ch);
		return ch + fieldName.substring(1);
		
	} 

}
