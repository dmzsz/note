package org.hyy.note.service.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Class builds map of parameters for a named query
 * 
 * @author huyanyan
 */
public class QueryParameters {

	/**
	 * Map 参数
	 */
	private Map<String, Object> parameters;

	private QueryParameters(String name, Object value) {
		parameters = new HashMap<String, Object>();
		parameters.put(name, value);
	}
	/**
	 * 静态方法 返回新的对象
	 * @param name
	 * @param value
	 * @return
	 */
	public static QueryParameters setParam(String name, Object value) {
		return new QueryParameters(name, value);
	}
	
	/**
	 * 添加更多参数
	 * 
	 * @param name of parameter
	 * @param value of parameter
	 * @return this object
	 */
	public QueryParameters add(String name, Object value) {
		parameters.put(name, value);
		return this;
	}
	
	/**
	 * 返回创建的map
	 * 
	 * @return result Map
	 */
	public Map<String, Object> buildMap() {
		return parameters;
	}
}
