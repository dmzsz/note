package org.hyy.note.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hyy.note.exception.ServiceException;
import org.hyy.note.repository.GenericRepository;
import org.hyy.note.service.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现接口 {@link AbstractService}</br> 用于捕获异常，初始化T类型
 * 
 * @param <T>
 * 
 * @author huyanyan
 */
public abstract class AbstractServiceImpl<T extends Serializable>
		implements
			AbstractService<T> {

	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractService.class);

	protected GenericRepository<T, Long> repository;

	/**
	 * 对象class的simpleName
	 */
	private String className;

	@SuppressWarnings("unchecked")
	public AbstractServiceImpl(GenericRepository<T, Long> repository) {
		ParameterizedType pt = (ParameterizedType) getClass()
				.getGenericSuperclass();
		Class<T> type = (Class<T>) pt.getActualTypeArguments()[0];
		className = type.getSimpleName();

		this.repository = repository;
		this.repository.setType(type);
	}

	/**
	 * @see org.hyy.note.service.AbstractService#add(java.io.Serializable)
	 */
	@Override
	public void add(T obj) {
		try {
			repository.add(obj);
			LOG.info("ADDED: " + obj);
		} catch (Exception e) {
			String message = String.format("Unable to add %s", obj);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.AbstractService#addTransactionally(java.io.Serializable)
	 */
	@Override
	@Transactional
	public void addTransactionally(T obj) {
		this.add(obj);
	}

	/**
	 * @see org.hyy.note.service.AbstractService#get(java.lang.Long)
	 */
	@Override
	public T get(Long id) {
		try {
			return repository.get(id);
		} catch (Exception e) {
			String message = String.format("Unable to get %s id=%d", className,
					id);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.AbstractService#getTransactionally(java.lang.Long)
	 */
	@Override
	@Transactional
	public T getTransactionally(Long id) {
		return this.get(id);
	}

	/**
	 * @see org.hyy.note.service.AbstractService#update(java.io.Serializable)
	 */
	@Override
	public void update(T obj) {
		try {
			repository.update(obj);
			LOG.info("UPDATED: " + obj);
		} catch (Exception e) {
			String message = String.format("Unable to update %s", obj);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.AbstractService#updateTransactionally(java.io.Serializable)
	 */
	@Override
	@Transactional
	public void updateTransactionally(T obj) {
		this.update(obj);
	}

	/**
	 * @see org.hyy.note.service.AbstractService#deleteTransactionally(java.io.Serializable)
	 */
	@Override
	@Transactional
	public void deleteTransactionally(T obj) {
		try {
			repository.delete(obj);
			LOG.info("DELETED: " + obj);
		} catch (Exception e) {
			String message = String.format("Unable to delete %s", obj);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.AbstractService#deleteByIdTransactionally(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteByIdTransactionally(Long id) {
		try {
			repository.deleteById(id);
			LOG.info("DELETED: {}[id={}]", className, id);
		} catch (Exception e) {
			String message = String.format("Unable to delete %s by id=",
					className, id);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.AbstractService#getAllTransactionally()
	 */
	@Override
	@Transactional
	public List<T> getAllTransactionally() {
		try {
			return repository.getAll();
		} catch (Exception e) {
			String message = String.format("Unable to get all: %s", className);
			throw new ServiceException(message, e);
		}
	}
}
