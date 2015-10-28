package org.hyy.note.service;

import org.hyy.note.domain.Category;


/**
 * 提供category相关操作
 * 
 * @author huyanyan
 */
public interface CategoryService extends AbstractService<Category> {
	
	Category getByName(String name);
}
