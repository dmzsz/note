package org.hyy.note.service;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract service 提供基本的对持久性象的事务和非事务操作
 * 
 * @author huyanyan
 */
public interface AbstractService<T extends Serializable> {
	
	/**
	 * 保存对象到 repository
	 * 
	 * @param obj 保存的对象
	 */
	void add(T obj);
	
	/**
	 * 事务保存对象
	 * 
	 * @param obj 保存的对象
	 */
	void addTransactionally(T obj);
	
	/**
	 * 获取对象从 repository
	 * 
	 * @param id 对象id
	 * @return 持久对象或null
	 */
	T get(Long id);
	
	/**
	 * 事务获取对象从 repository
	 * 
	 * @param id 对象id
	 * @return 持久对象或null
	 */
	T getTransactionally(Long id);
	
	/**
	 * 更新对象
	 * 
	 * @param obj 
	 */
	void update(T obj);
	
	/**
	 * 事务更新对象
	 * 
	 * @param obj 
	 */
	void updateTransactionally(T obj);
	
	/**
	 * 事务删除对象
	 * 
	 * @param obj 
	 */
	void deleteTransactionally(T obj);
	
	/**
	 * 事务删除对象通过id
	 * 
	 * @param id 对象id
	 */
	void deleteByIdTransactionally(Long id);
	
	/**
	 * 事务从数据库中获取对象的所有记录
	 * 
	 * @return list of objects
	 */
	List<T> getAllTransactionally();
}
