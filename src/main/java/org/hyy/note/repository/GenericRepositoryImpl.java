package org.hyy.note.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hyy.note.domain.Article;
import org.hyy.note.domain.Category;
import org.hyy.note.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 持久类操作实现类 实现JPA接口 {@link GenericRepository}
 * 
 * @param <T>
 *            持久类类型
 * @param <PK>
 *            主键类型
 * 
 * @see org.hyy.note.repository.GenericRepository
 * 
 * @author huyanyan
 */
@Repository
@Scope("prototype")
public class GenericRepositoryImpl<T extends Serializable, PK extends Serializable>
		implements GenericRepository<T, PK> {

	/**
	 * 持久类对象的类型
	 */
	private Class<T> clazz;

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * @see org.hyy.note.repository.GenericRepository#setType(java.lang.Class)
	 */
	public void setType(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Class<T> getType() {
		return this.clazz;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	private Session getHibernateSession() {
		Session session = this.getSessionFactory().getCurrentSession();
		return session;
	}

	/**
	 * @return 
	 * @see org.hyy.note.repository.GenericRepository#add(java.lang.Object)
	 */
	@Override
	public long add(T obj) {
		return (long) getHibernateSession().save(obj);
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		return (T) getHibernateSession().get(clazz, id);
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#update(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T update(T obj) {
		return (T) getHibernateSession().merge(obj);
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(T obj) {
		getHibernateSession().delete(obj);
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#deleteById(java.io.Serializable)
	 */
	@Override
	public void deleteById(PK id) {
		T obj = this.get(id);
		this.delete(obj);
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#getAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		Session session = getHibernateSession();
		return session.createCriteria(clazz).list();
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#getByNamedQuery(java.lang.String,
	 *      java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getByNamedQuery(String namedQuery, Map<String, Object> parameters) {

		Query query = getHibernateSession().getNamedQuery(namedQuery);
		query = setParameters(query, parameters);
		return (T) query.list().get(0);

	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#getAllByNamedQuery(java.lang.String)
	 */
	@Override
	public List<T> getAllByNamedQuery(String namedQuery) {

		return getAllByNamedQuery(namedQuery, 0, 0);
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#getAllByNamedQuery(java.lang.String,
	 *      int, int)
	 */
	@Override
	public List<T> getAllByNamedQuery(String namedQuery, int firstResult,
			int maxResults) {

		return getAllByNamedQuery(namedQuery, null, firstResult, maxResults);
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#getAllByNamedQuery(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public List<T> getAllByNamedQuery(String namedQuery,
			Map<String, Object> parameters) {

		return getAllByNamedQuery(namedQuery, parameters, 0, 0);
	}

	/**
	 * 所有的getAllByNamedQuery(...)方法都加入了这个方法
	 * 
	 * @see org.hyy.note.repository.GenericRepository#getAllByNamedQuery(java.lang.String,
	 *      java.util.Map, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByNamedQuery(String namedQuery,
			Map<String, Object> parameters, int firstResult, int maxResults) {

		Query query = getHibernateSession().getNamedQuery(namedQuery)
				.setFirstResult(firstResult);
		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		if (parameters != null) {
			query = setParameters(query, parameters);
		} 
		return query.list();
	}
	/**
	 * @see org.hyy.note.repository.GenericRepository#getAllNamesByNamedQuery(java.lang.String,
	 *      java.util.Map, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllNamesByNamedQuery(String namedQuery,
			int maxResults) {

		Query query = getHibernateSession().getNamedQuery(namedQuery);
		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#getCount()
	 */
	@Override
	public int getCount() {

		return (int) getHibernateSession()
				.createQuery(
						"SELECT count(x.id) FROM " + clazz.getSimpleName() + " x")
				.uniqueResult().hashCode();
	}

	/**
	 * @see org.hyy.note.repository.GenericRepository#getCountByNamedQuery(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public int getCountByNamedQuery(String namedQuery,
			Map<String, Object> parameters) {

		Query query = getHibernateSession().getNamedQuery(namedQuery);
		query = setParameters(query, parameters);
		return Integer.parseInt(query.list().get(0).toString());
	}

	/**
	 * 绑定参数
	 */
	private Query setParameters(Query query, Map<String, Object> parameters) {

		Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String name = entry.getKey();
			Object val = entry.getValue();
			query.setParameter(name, val);
		}
		return query;
	}
}
