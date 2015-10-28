package org.hyy.note.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernateQuery;

/**
 * 提供常用的repository功能
 * 
 * @param <T>
 *            持久类的类型
 * @param <PK>
 *            主键类型
 * 
 * @author huyanyan
 */
public interface GenericRepository<T extends Serializable, PK extends Serializable> {

	/**
	 * 设置类型
	 * 
	 * @param type
	 */
	void setType(Class<T> type);
	Class<T> getType();

	/**
	 * 添加对象到数据库
	 * 
	 * @param newInstance
	 *            插入数据库中的对象
	 * @return 插入数据库中对象的id
	 */
	long add(T newInstance);

	/**
	 * 获取对象从数据库
	 * 
	 * @param id
	 *            对象在数据库id
	 * @return 持久类对象或者 null
	 */
	T get(PK id);

	/**
	 * 
	 * 更新对象
	 * 
	 * @param transientObject
	 *            更新的对象，没有就插入
	 */
	T update(T transientObject);

	/**
	 * 从数据库中删除对象
	 * 
	 * @param persistentObject
	 *            删除的对象
	 */
	void delete(T persistentObject);

	/**
	 * 从数据库中删除对象通过id
	 * 
	 * @param id
	 *            数据库中删除对象的id
	 */
	void deleteById(PK id);

	/**
	 * 使用命名的查询获取数据库中的对象
	 * 
	 * @param namedQuery
	 *            命名查询
	 * @param map
	 *            参数表
	 * @return 持久类实例或null
	 */
	T getByNamedQuery(String namedQuery, Map<String, Object> parameters);

	/**
	 * 获取对象的list从数据库中
	 * 
	 * @return list 持久类对象的list
	 */
	List<T> getAll();

	/**
	 * 使用命名的查询获取数据库中的对象list
	 * 
	 * @param namedQuery
	 *            命名查询
	 * @return list 持久类对象的list
	 */
	List<T> getAllByNamedQuery(String namedQuery);

	/**
	 * 使用命名的查询获取数据库中的对象部分list
	 * 
	 * @param namedQuery
	 *            命名查询
	 * @param firstResult
	 *            返回结果的开始位置 从0开始
	 * @param resultLimit
	 *            返回结果的偏移量
	 * @return list 持久类对象的list
	 */
	List<T> getAllByNamedQuery(String namedQuery, int firstResult,
			int resultLimit);

	/**
	 * 使用命名的查询获取数据库中的对象list
	 * 
	 * @param namedQuery
	 *            命名查询
	 * @param parameters
	 *            参数map
	 * @return list 持久类对象的list
	 */
	List<T> getAllByNamedQuery(String namedQuery, Map<String, Object> parameters);

	/**
	 * 使用命名的查询获取数据库中的对象部分list
	 * 
	 * @param namedQuery
	 *            命名查询
	 * @param parameters
	 *            参数map
	 * @param firstResult
	 *            返回结果的开始位置, 从0开始
	 * @param maxResults
	 *            返回结果数
	 * @return 持久类对象的list
	 */
	List<T> getAllByNamedQuery(String namedQuery,
			Map<String, Object> parameters, int firstResult, int resultLimit);

	/**
	 * 获取names
	 * 
	 * @param namedQuery
	 *            命名查询
	 * @param resultLimit
	 *            返回结果的偏移量
	 * @return list names的list
	 */
	List<String> getAllNamesByNamedQuery(String namedQuery, int resultLimit);

	/**
	 * 数据库中对象的总数
	 * 
	 * @return 总数
	 */
	int getCount();

	/**
	 * 使用命名查询赶回对象总数
	 * 
	 * @param namedQuery
	 *            命名查询
	 * @param parameters
	 *            参数map
	 * @return 对象总数
	 */
	int getCountByNamedQuery(String namedQuery, Map<String, Object> parameters);
}
