package org.hyy.note.service.impl;

import static org.hyy.note.service.util.QueryParameters.setParam;

import java.util.List;

import org.hyy.note.domain.Category;
import org.hyy.note.exception.ServiceException;
import org.hyy.note.repository.GenericRepository;
import org.hyy.note.service.CategoryService;
import org.hyy.note.web.constants.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现接口 {@link CategoryService}
 * 
 * @author huyanyan
 */
@Service("CategoryService")
public class CategoryServiceImpl extends AbstractServiceImpl<Category>
		implements
			CategoryService {

	@Autowired
	public CategoryServiceImpl(GenericRepository<Category, Long> repository) {
		super(repository);
	}

	/**
	 * @see org.hyy.note.service.CategoryService#getByName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Category getByName(String name) {
		try {
			return repository.getByNamedQuery("Category.GET_BY_NAME",
					setParam("name", name).buildMap());
		} catch (Exception e) {
			String message = String.format("Unable to get category=%s", name);
			throw new ServiceException(message, e);
		}
	}

}
