package org.hyy.note.service;

import java.util.Map;

import org.hyy.note.domain.Article;
import org.hyy.note.exception.NotFoundException;


/**
 * 提供article相关操作
 * 
 * @author huyanyan
 */
public interface ArticleService extends AbstractService<Article> {
	
	/**
	 * 保存article到数据库
	 */
	void add(Article article, Long authorId, String categoryName, String tagString);
	
	/**
	 * 获得article从数据库通过user id并且增加浏览总数如果：
	 * 1. user是匿名的
	 * 2. user不是这个article的作者
	 * 3. requested URL不等于本地article URL (通过外网来访问的url)
	 * 
	 * @param articleId  article ID
	 * @param userId 登录的用户 id
	 * @param needIncreaseViewCount 是否需要增加view总数
	 * @return article
	 * @throws NotFoundException
	 */
	Article get(Long articleId, Long userId, boolean needIncreaseViewCount)
			throws NotFoundException;
	
	/**
	 * 更新 article
	 */
	void update(Article article, String categoryName, String tagString);
	
	/**
	 * 获得 指定页的articles
	 * 
	 * @param page
	 * @param articlesPerPage 前多少页
	 * @return articles Map和页数
	 */
	Map<String, Object> getByPage(int page, int articlesPerPage);

	/**
	 * 根据category查找article内容并指定前多少页
	 * 
	 * @param page 开始页数
	 * @param articlesPerPage 前多少页
	 * @param name category名字
	 * @return articles和页数的Map
	 * @throws 如果article没有找到抛出NotFoundException
	 */
	Map<String, Object> getByPageByCategoryName(int page, int articlesPerPage, String categoryName)
			throws NotFoundException;
	
	/**
	 * 根据关键字查找article内容并指定前多少页
	 * 
	 * @param page 开始页数
	 * @param articlesPerPage 前多少页
	 * @param fragment 关键字
	 * @return articles和页数的Map
	 */
	Map<String, Object> getByPageByFragment(int page, int articlesPerPage, String fragment);
	
	/**
	 * 根据tag查找article内容并指定前多少页
	 * 
	 * @param page 开始页
	 * @param articlesPerPage 前多少页
	 * @param tagName 标签名
	 * @return articles和页数的Map
	 * @throws 如果article没有找到抛出NotFoundException
	 */
	Map<String, Object> getByPageByTagName(int page, int articlesPerPage, String tagName)
			throws NotFoundException;
	
	/**
	 * 根据user id查找article内容并指定前多少页
	 * 
	 * @param page 开始页
	 * @param articlesPerPage 前多少页
	 * @param id user id
	 * @return articles和页数的Map
	 * @throws 如果user没有找到抛出NotFoundException
	 */
	Map<String, Object> getByPageByUserId(int page, int articlesPerPage, Long id)
			throws NotFoundException;
	
}
