package org.hyy.note.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hyy.note.service.util.QueryParameters.setParam;

import org.hyy.note.domain.Article;
import org.hyy.note.domain.Category;
import org.hyy.note.domain.Tag;
import org.hyy.note.domain.User;
import org.hyy.note.exception.NotFoundException;
import org.hyy.note.exception.ServiceException;
import org.hyy.note.repository.GenericRepository;
import org.hyy.note.service.ArticleService;
import org.hyy.note.service.CategoryService;
import org.hyy.note.service.TagService;
import org.hyy.note.service.UserService;
import org.hyy.note.web.constants.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现接口 {@link ArticleService}
 * 
 * @author huyanyan
 */
@Service("ArticleService")
public class ArticleServiceImpl extends AbstractServiceImpl<Article>
		implements
			ArticleService {
	private static final Logger LOG = LoggerFactory
			.getLogger(ArticleServiceImpl.class);

	private static final String ERROR_BY_PAGE = "Unable to get all articles by page=";

	private CategoryService categoryService;
	private TagService tagService;
	private UserService userService;

	@Autowired
	public ArticleServiceImpl(GenericRepository<Article, Long> repository,
			CategoryService categoryService, TagService tagService,
			UserService userService) {
		super(repository);
		this.categoryService = categoryService;
		this.tagService = tagService;
		this.userService = userService;
	}

	/**
	 * @see org.hyy.note.service.ArticleService#add(org.hyy.note.domain.Article,java.lang.Long,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public void add(Article article, Long authorId, String categoryName,
			String tagString) {
		try {
			article = populateArticle(article, authorId, categoryName,
					tagString);
			this.add(article);
		} catch (Exception e) {
			String message = String.format("Unable to add %s", article);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.ArticleService#update(org.hyy.note.domain.Article,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public void update(Article article, String categoryName, String tagString) {
		try {
			article = populateArticle(article, null, categoryName, tagString);
			article.setLastModified(new Date());
			this.update(article);
		} catch (Exception e) {
			String message = String.format("Unable to update %s", article);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * 创建 article 附属有 author, category 和 逗号分隔的tags字符串
	 * 
	 * @param article
	 * @param authorId
	 * @param categoryName
	 * @param tagString
	 * @return populated {@link Article}
	 * @throws Exception
	 */
	private Article populateArticle(Article article, Long authorId,
			String categoryName, String tagString) throws Exception {

		if (authorId != null) { // Add article process
			User author = userService.get(authorId);
			article.setAuthor(author);
		}

		if (categoryName.isEmpty()) {
			categoryName = Common.DEFAULT_CATEGORY_NAME;
		}
		Category category = categoryService.getByName(categoryName);
		article.setCategory(category);

		if (!tagString.isEmpty()) {
			Set<Tag> tags = tagService.getTagsFromString(tagString);
			article.setTags(tags);
		}
		return article;
	}

	/**
	 * @see org.hyy.note.service.ArticleService#get(java.lang.Long,
	 *      java.lang.Long, boolean)
	 */
	@Override
	@Transactional(readOnly = false)
	public Article get(Long articleId, Long userId,
			boolean needIncreaseViewCount) {

		Article article = repository.get(articleId);

		if (article == null) {
			throw new NotFoundException("Article not found");
		}

		if (needIncreaseViewCount && userId != article.getAuthor().getId()) {
			int viewCount = article.getViewCount();
			article.setViewCount(++viewCount);
			article = repository.update(article);
		}
		// 懒加载comments.需要修改(使用 resultLimit进行分页)
		article.getComments().size();
		return article;
	}

	/**
	 * @see org.hyy.note.service.ArticleService#getByPage(int, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getByPage(int page, int articlesPerPage) {
		try {
			return getArticlesData(page, articlesPerPage, "Article.GET_ALL",
					repository.getCount());
		} catch (Exception e) {
			String message = String.format("%s%d", ERROR_BY_PAGE, page);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.ArticleService#getByPageByCategoryName(int,
	 *      int, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getByPageByCategoryName(int page,
			int articlesPerPage, String name) {
		try {
			Category category = categoryService.getByName(name);
			if (category == null) {
				throw new NotFoundException("Category not found");
			}
			return getArticlesData(page, articlesPerPage,
					"Article.GET_ALL_BY_CATEGORY_NAME", setParam("name", name)
							.buildMap(), category.getArticleCount());
		} catch (Exception e) {
			String message = String.format("%s%d, category=%s", ERROR_BY_PAGE,
					page, name);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.ArticleService#getByPageByTagName(int, int,
	 *      java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getByPageByTagName(int page,
			int articlesPerPage, String name) {
		try {
			Tag tag = tagService.getByName(name);
			if (tag == null) {
				throw new NotFoundException("Tag not found");
			}
			return getArticlesData(page, articlesPerPage,
					"Article.GET_ALL_BY_TAG_NAME", setParam("name", name)
							.buildMap(), tag.getArticleCount());
		} catch (Exception e) {
			String message = String.format("%s%d, tag=%s", ERROR_BY_PAGE, page,
					name);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.ArticleService#getByPageByUserId(int, int,
	 *      java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getByPageByUserId(int page, int articlesPerPage,
			Long id) {
		try {
			User user = userService.get(id);
			if (user == null) {
				throw new NotFoundException("User not found");
			}
			Map<String, Object> articlesData = getArticlesData(page,
					articlesPerPage, "Article.GET_ALL_BY_USER_ID",
					setParam("id", id).buildMap(), user.getArticleCount());
			articlesData.put("userLogin", user.getLogin());
			return articlesData;
		} catch (Exception e) {
			String message = String.format("%s%d, user_id=%d", ERROR_BY_PAGE,
					page, id);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.ArticleService#getByPageByFragment(int, int,
	 *      java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getByPageByFragment(int page,
			int articlesPerPage, String fragment) {
		try {
			Map<String, Object> parameters = setParam("fragment",
					"%" + fragment + "%").buildMap();
			int articleCount = repository.getCountByNamedQuery(
					"Article.GET_COUNT_BY_FRAGMENT", parameters);
			return getArticlesData(page, articlesPerPage,
					"Article.GET_ALL_BY_FRAGMENT", parameters, articleCount);
		} catch (Exception e) {
			String message = String.format("%s%d, fragment=%s", ERROR_BY_PAGE,
					page, fragment);
			throw new ServiceException(message, e);
		}
	}

	private Map<String, Object> getArticlesData(int page, int articlesPerPage,
			String namedQuery, int articleCount) throws Exception {
		
		return getArticlesData(page, articlesPerPage, namedQuery, null,
				articleCount);
	}
	
	/**
	 * 	
	 * @param page
	 * @param articlesPerPage
	 * @param namedQuery
	 * @param params
	 * @param articleCount
	 * @return key："articlesByPage"，value：article list
	 * 		   key:"pageCount", value: page count
	 * @throws Exception
	 */
	private Map<String, Object> getArticlesData(int page, int articlesPerPage,
			String namedQuery, Map<String, Object> params, int articleCount)
			throws Exception {

		Map<String, Object> articlesData = new HashMap<String, Object>();

		if (articleCount == 0) {
			return articlesData;
		}

		int pageCount = articleCount / articlesPerPage
				+ (articleCount % articlesPerPage == 0 ? 0 : 1);
		if (page > pageCount) {
			throw new NotFoundException("page > pageCount");
		}

		articlesData.put("pageCount", pageCount);
		
		int firstResult = articlesPerPage * (page - 1);
		articlesData.put("articlesByPage", repository.getAllByNamedQuery(
				namedQuery, params, firstResult, articlesPerPage));
		return articlesData;
	}

}
