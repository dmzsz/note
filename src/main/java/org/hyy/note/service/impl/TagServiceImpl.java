package org.hyy.note.service.impl;

import static org.hyy.note.service.util.QueryParameters.setParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hyy.note.domain.Tag;
import org.hyy.note.exception.ServiceException;
import org.hyy.note.repository.GenericRepository;
import org.hyy.note.service.TagService;
import org.hyy.note.web.constants.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现接口 {@link TagService}
 * 
 * @author huyanyan
 */
@Service("TagService")
public class TagServiceImpl extends AbstractServiceImpl<Tag> implements TagService {
	
	@Autowired 
	public TagServiceImpl(GenericRepository<Tag, Long> repository) {
		super(repository);
	}
	
	/**
	 * @see org.hyy.note.service.TagService#getByName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Tag getByName(String name) {
		try {
			return repository.getByNamedQuery("Tag.GET_BY_NAME",
					setParam("name", name).buildMap());
		} catch (Exception e) {
			String message = String.format("Unable to get tag=%s", name);
			throw new ServiceException(message, e);
		}
	}
	
	/**
	 * @see org.hyy.note.service.impl.AbstractServiceImpl#getAllTransactionally()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Tag> getAllTransactionally() {
		try {
			return repository.getAllByNamedQuery(
					"Tag.GET_ALL", 0, Common.TAG_MAX_COUNT);
		} catch (Exception e) {
			throw new ServiceException("Unable to get all tags", e);
		}
	}

	/**
	 * @see org.hyy.note.service.TagService#getAllNames()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<String> getAllNames() {
		try {
			return repository.getAllNamesByNamedQuery(
					"Tag.GET_ALL_NAMES", 0);
		} catch (Exception e) {
			throw new ServiceException("Unable to get all tag names", e);
		}
	}
	
	/**
	 * @see org.hyy.note.service.TagService#getAutocompleteJson()
	 */
	@Override
	public String getAutocompleteJson() {
		List<String> tagNames = this.getAllNames();
		
		if (tagNames.isEmpty()) {
			return "[]";
		}
		
		StringBuilder result = new StringBuilder("[");
		for (String name : tagNames) {
			result.append("\"")
				.append(name)
				.append("\"")
				.append(",");
		}
		result.delete(result.length()-1, result.length())
			.append("]");
		return result.toString();
	}
	
	/**
	 * @see org.hyy.note.service.TagService#getTagsFromString(java.lang.String)
	 */
	@Override
	public Set<Tag> getTagsFromString(String tagString) {
		Set<Tag> result = new HashSet<Tag>();
		for (String tagName : tagString.split(",")) {
			tagName = tagName.replaceAll("\\s+", "");
			Tag tagObj = this.getByName(tagName);	// get persistent object
			if (tagObj == null) {
				tagObj = new Tag();
				tagObj.setName(tagName);
			}
			result.add(tagObj);
		}
		return result;
	}

	/**
	 * @see org.hyy.note.service.TagService#getTagString(java.util.Set)
	 */
	@Override
	public String getTagString(Set<Tag> tags) {
		StringBuilder result = new StringBuilder();
		for (Tag tag : tags) {
			result.append(tag.getName()).append(",");
		}
		result.delete(result.length()-1, result.length());
		return result.toString();
	}
}
