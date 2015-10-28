package org.hyy.note.service;

import java.util.List;
import java.util.Set;

import org.hyy.note.domain.Tag;


/**
 * 提供tag相关操作
 * 
 * @author huyanyan
 */
public interface TagService extends AbstractService<Tag> {
	
	Tag getByName(String name);
	
	List<String> getAllNames();
	
	/**
	 * 得到所有的tag的json
	 * 
	 * @return json
	 */
	String getAutocompleteJson();
	
	/**
	 * 将逗号分隔的tags字符串转为Set
	 */
	Set<Tag> getTagsFromString(String tagString);
	
	/**
	 * 将set样式的tag转为逗号分隔的字符串
	 */
	String getTagString(Set<Tag> tags);
}
