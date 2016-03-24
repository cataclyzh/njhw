package com.cosmosource.base.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * @类描述:  json文件格式转换
 * 
 * @作者： WXJ
 */
@SuppressWarnings("unchecked")
public class JsonUtil {
	
	private static final Logger log = Logger.getLogger("com.cosmosource.base.util.JsonUtil");

	/**
	 * Jackson 公用 ObjectMapper 对象.
	 */
	public static final ObjectMapper MAPPER = new ObjectMapper();
	static {
		MAPPER.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}
	private static final JsonFactory JSONFACTORY = new JsonFactory();

	/**
	 * 转换Java Bean 为 json
	 */
	public static String beanToJson(Object o) {
		
		StringWriter sw = new StringWriter(300);
		JsonGenerator gen = null;
		try {
			gen = JSONFACTORY.createJsonGenerator(sw);
			MAPPER.writeValue(gen, o);
			return sw.toString();
		} catch (Exception e) {
			return null;
		} finally {
			if (gen != null) {
				try {
					gen.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 转换Java Bean 为 HashMap
	 */
	public static Map<String, Object> beanToMap(Object o) {
		try {
			return MAPPER.readValue(beanToJson(o), HashMap.class);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 转换Json String 为 HashMap
	 */
	public static Map<String, Object> jsonToMap(String json,
			boolean collToString) {
		try {
			Map<String, Object> map = MAPPER.readValue(json, HashMap.class);
			if (collToString) {
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof Collection
							|| entry.getValue() instanceof Map) {
						entry.setValue(beanToJson(entry.getValue()));
					}
				}
			}
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("serial")
	public static class JSONParseException extends Exception {
		public JSONParseException(String message) {
			super(message);
		}
	}

	public static String indentJSON(final String jsonIn)
			throws JSONParseException {
		JsonGenerator gen = null;
		try {
			JsonParser parser = JSONFACTORY.createJsonParser(new StringReader(
					jsonIn));
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = null;
			try {
				node = mapper.readTree(parser);
			} catch (JsonParseException ex) {
				throw new JSONParseException(ex.getMessage());
			}
			StringWriter out = new StringWriter();

			// Create pretty printer:
			gen = JSONFACTORY.createJsonGenerator(out);
			gen.useDefaultPrettyPrinter();

			// Now write:
			mapper.writeTree(gen, node);
			
			return out.toString();
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if (gen != null){
				try {
					gen.flush();
					gen.close();
				} catch (Exception e) {
				}
			}
		}
		return jsonIn;
	}

	public static String listToJson(List<Map<String, String>> list) {

		JsonGenerator gen =  null;
		try {
			StringWriter sw = new StringWriter();
			gen = JSONFACTORY.createJsonGenerator(sw);
			new ObjectMapper().writeValue(gen, list);
			gen.flush();
			return sw.toString();
		} catch (Exception e) {
			return null;
		}finally {
			if (gen != null){
				try {
					gen.flush();
					gen.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static List<Map<String, String>> jsonToList(String str) {
		try {
			if (StringUtils.isNotBlank(str)) {
				JsonParser parser = JSONFACTORY
						.createJsonParser(new StringReader(str));

				ArrayList<Map<String, String>> arrList = (ArrayList<Map<String, String>>) new ObjectMapper()
						.readValue(parser, ArrayList.class);
				return arrList;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	
	/**
	* @Description：把map类型转换成json字符串类型
	* @Author：hp
	* @Date：2013-1-23
	* @param map
	* @return
	**/
	public static String mapToJson(Map<String,?> map){
		JSONObject jsonObject = null;
		if(map == null){
			log.log(Level.INFO, "输入的map为空!");
			return null;
		}
		for (Object key : map.keySet()) {
			jsonObject = JSONObject.fromObject(map).element((String) key, map.get(key));
		}
		if(jsonObject.toString() == null){
			log.log(Level.INFO,"得到的json字符串为空!");
			return null;
		}
		return jsonObject.toString();
	}
	
	
	
	/**
	* @Description：把json转换成map类型，其中对于json中有数组的处理
	* @Author：hp
	* @Date：2013-1-23
	* @param json
	* @return
	**/
	public static Map jsonToMap(String json){
		if(json == null){
			log.log(Level.INFO, "输入的json字符串为空!");
			return null;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject = JSONObject.fromObject(json);
		for (Object key : jsonObject.keySet()) {
			Object value  = jsonObject.get(key);
			if(value.equals(null)){
				System.out.println("json的数据格式不正确!json字符串中的数组值为空!请检查json");
				break;
			}
			if(value instanceof JSONArray){
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				Iterator<JSONObject> it = ((JSONArray) value).iterator();
				while(it.hasNext()){
					JSONObject jsonObject2 = it.next();
					list.add(JsonUtil.jsonToMap(jsonObject2.toString()));
				}
				map.put((String)key, list);
			}else{
				map.put((String)key, value);
			}
		}
		if(map == null){
			log.log(Level.INFO, "得到结果map为空!");
			return null;
		}
		return map;
	}
	
	
	/**
	* @Description：把list转换成json格式
	* @Author：hp
	* @Date：2013-1-23
	* @param list
	* @return
	**/
	public static String listToJsonb(List<?> list){
		if(list == null){
			log.log(Level.INFO, "输入的集合为空!");
			return null;
		}
		String json = JSONArray.fromObject(list).toString();
		if(json == null){
			log.log(Level.INFO, "得到的字符串为空!");
			return null;
		}
		return json;
	}
	
		
	/**
	* @Description：json字符串转换成list集合，json字符串要以'['开始
	* @example:"[{'age':12,'color':'red'},{'age':23,'color':'black'}]"
	* @Author：hp
	* @Date：2013-1-23
	* @param json
	* @return
	**/
	public static List<?> jsonToListb(String json){
		if(json == null){
			log.log(Level.INFO, "输入的json字符串为空!");
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<?> list = (List<?>) jsonArray.toCollection(jsonArray);
		if(list == null || list.size() == 0){
			log.log(Level.INFO, "得到的集合为空!");
			return null;
		}
		return list;
	}
	
	
	/**
	* @Description：数组转换成json字符串
	* @Author：hp
	* @Date：2013-1-23
	* @param object
	* @return
	**/
	public static String arrayToJson(Object[] object){
		if(object == null || object.length < 0){
			log.log(Level.INFO, "输入的数组为空!");
			return null;
		}
		String json = JSONArray.fromObject(object).toString();
		if(json == null){
			log.log(Level.INFO, "得到的字符串为空!");
			return null;
		}
		return json;
	}
	
	
	/**
	* @Description：把json转换成数组
	* @Author：hp
	* @Date：2013-1-23
	* @param json
	* @return
	**/
	public static Object[] jsonToArray(String json){
		if(json == null){
			log.log(Level.INFO, "输入的json字符串为空!");
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(json);
		Collection<?> array = JSONArray.toCollection(jsonArray);
		if(array.toArray() == null){
			log.log(Level.INFO, "得到的数组为空!");
			return null;
		}
		return array.toArray();
	}
	
	
	/**
	* @Description：把对象转换成json字符串
	* @Author：hp
	* @Date：2013-1-23
	* @param entity
	* @return
	**/
	public static String entityToJson(Object entity){
		if(entity == null){
			log.log(Level.INFO, "输入的对象为空!");
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(entity);
		if(jsonObject.toString() == null){
			log.log(Level.INFO, "得到的字符串为空!");
			return null;
		}
		return jsonObject.toString();
	}
	
	
	/**
	* @Description：把json字符串转换为特定的对象 
	* @Author：hp
	* @Date：2013-1-23
	* @param json
	* @param entity
	* @return
	**/
	public static Object jsonToEntity(String json,Class entity){
		if(json == null){
			log.log(Level.INFO, "输入的json字符串为空!");
			return null;
		}
		if(entity ==  null){
			log.log(Level.INFO,"请输入指定的类!");
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		Object o = JSONObject.toBean(jsonObject,entity);
		if(o == null){
			log.log(Level.INFO,"得到的对象为空!");
			return null;
		}
		return o;
	}
	
	
    /*
     * ---------------------------下面对于特殊情况的处理，只限于简单模式情况下------------------------------------
     */
	
	
	/*
	 * 1、*********************对于list<Object>和list<Map>类型和json之间的解析
	 */
	
	
	/**
	* @Description：把json转换为List<Object>这种形式
	* @Author：hp
	* @Date：2013-1-24
	* @param json
	* @param entity
	* @return
	**/
	public static List<?> jsonToListObject(String json,Class entity){
		if(json == null){
			log.log(Level.INFO, "输入的json字符串为空!");
			return null;
		}
		if(entity == null){
			log.log(Level.INFO,"请输入集合中的对象!");
		}
		List list = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(json);
		Iterator<JSONObject> it = jsonArray.iterator();
		JSONObject jsonObject  = null;
		while(it.hasNext()){
			jsonObject = it.next();
			list.add(JsonUtil.jsonToEntity(jsonObject.toString(), entity));
		}
		if(list == null){
			log.log(Level.INFO, "得到的集合为空!");
			return null;
		}
		return list;
	}
	
	
	/**
	* @Description：把json转换为list<Map>这种形式
	* @Author：hp
	* @Date：2013-1-24
	* @param json
	* @return
	**/
	public static List<Map> jsonToListMap(String json){
		if(json == null){
			log.log(Level.INFO, "输入的json字符串为空!");
			return null;
		}
		List<Map> list = new ArrayList<Map>();
		JSONArray jsonArray = JSONArray.fromObject(json);
		Iterator<JSONObject> it = jsonArray.iterator();
		JSONObject jsonObject  = null;
		while(it.hasNext()){
			jsonObject = it.next();
			list.add(JsonUtil.jsonToMap(jsonObject.toString()));
		}
		if(list == null){
			log.log(Level.INFO, "得到的集合为空!");
			return null;
		}
		return list;
	}
}