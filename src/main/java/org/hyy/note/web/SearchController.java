package org.hyy.note.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hyy.note.domain.Article;
import org.hyy.note.exception.NotFoundException;
import org.hyy.note.service.ArticleService;
import org.hyy.note.web.constants.Common;
import org.hyy.note.web.constants.URL;
import org.hyy.note.web.constants.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 搜索控制器，REST搜索风格
 * 
 * @author huyanyan
 */
@Controller
public class SearchController {
	@Resource
	private ArticleService articleService;
	@Autowired
	private ApplicationContext context;

	/**
	 * 查找文章 - 第1页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SEARCH_BY_CATEGORY)
	public ModelAndView viewArticlesByCategory(
			@PathVariable("name") String categoryName) {

		return categoryModelAndView(1, categoryName);
	}

	/**
	 * 查找文章 - 指定页数
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SEARCH_BY_CATEGORY_CUSTOM_PAGE)
	public ModelAndView viewArticlesByCategory(
			@PathVariable("name") String categoryName,
			@PathVariable("number") Integer pageNumber) {

		validatePageNumber(pageNumber);
		if (pageNumber == 1) {
			return new ModelAndView("redirect:" + URL.SEARCH_BY_CATEGORY);
		}
		return categoryModelAndView(pageNumber, categoryName);
	}

	/**
	 * 查找文章的map交给searchModelAndView处理
	 */
	private ModelAndView categoryModelAndView(Integer pageNumber,
			String categoryName) {

		Map<String, Object> articlesData = articleService
				.getByPageByCategoryName(pageNumber, Common.ARTICLES_PER_PAGE,
						categoryName);
		String requestUrl = String.format("/category/%s/", categoryName);
		String message = String.format(context.getMessage(
				"search.result.byCategory", null,
				LocaleContextHolder.getLocale()), categoryName);

		return searchModelAndView(articlesData, pageNumber, message, requestUrl);
	}

	/**
	 * 查找tags - 第1页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SEARCH_BY_TAG)
	public ModelAndView viewArticlesByTag(@PathVariable("name") String tagName) {

		return tagModelAndView(1, tagName);
	}

	/**
	 * 查找tags - 指定页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SEARCH_BY_TAG_CUSTOM_PAGE)
	public ModelAndView viewArticlesByTag(@PathVariable("name") String tagName,
			@PathVariable("number") Integer pageNumber) {

		validatePageNumber(pageNumber);
		if (pageNumber == 1) {
			return new ModelAndView("redirect:" + URL.SEARCH_BY_TAG);
		}
		return tagModelAndView(pageNumber, tagName);
	}

	/**
	 * 查找tag的map交给searchModelAndView处理
	 */
	private ModelAndView tagModelAndView(Integer pageNumber, String tagName) {

		Map<String, Object> articlesData = articleService.getByPageByTagName(
				pageNumber, Common.ARTICLES_PER_PAGE, tagName);
		String requestUrl = String.format("/tags/%s/", tagName);
		String message = String.format(context.getMessage(
				"search.result.byTag", null, LocaleContextHolder.getLocale()),
				tagName);

		return searchModelAndView(articlesData, pageNumber, message, requestUrl);
	}

	/**
	 * 查找user - 第1页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SEARCH_BY_USER)
	public ModelAndView viewArticlesByUser(@PathVariable("id") Long userId) {

		return userModelAndView(1, userId);
	}

	/**
	 * 查找user - 第1页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SEARCH_BY_USER_CUSTOM_PAGE)
	public ModelAndView viewArticlesByUser(@PathVariable("id") Long userId,
			@PathVariable("number") Integer pageNumber) {

		validatePageNumber(pageNumber);
		if (pageNumber == 1) {
			return new ModelAndView("redirect:" + URL.SEARCH_BY_USER);
		}
		return userModelAndView(pageNumber, userId);
	}

	/**
	 * 查找user的map交给searchModelAndView处理
	 */
	private ModelAndView userModelAndView(Integer pageNumber, Long userId) {

		Map<String, Object> articlesData = articleService.getByPageByUserId(
				pageNumber, Common.ARTICLES_PER_PAGE, userId);
		String userLogin = (String) articlesData.get("userLogin");
		String requestUrl = String.format("/user/%d/articles/", userId);
		String message = String.format(context.getMessage(
				"search.result.byUser", null, LocaleContextHolder.getLocale()),
				userLogin);

		return searchModelAndView(articlesData, pageNumber, message, requestUrl);
	}

	/**
	 * 搜索文章片段 - 通过搜索栏
	 */
	@RequestMapping(method = RequestMethod.POST, value = URL.SEARCH_BY_FRAGMENT_SUBMIT)
	public String searchSubmit(@RequestParam("fragment") String fragment) {

		return "redirect:/search/" + fragment;
	}

	/**
	 * 搜索文章片段 - 第1页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SEARCH_BY_FRAGMENT)
	public ModelAndView viewArticlesByFragment(
			@PathVariable("fragment") String fragment) {

		return fragmentModelAndView(1, fragment);
	}

	/**
	 * 查找文章片段 - 指定页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SEARCH_BY_FRAGMENT_CUSTOM_PAGE)
	public ModelAndView viewArticlesByFragment(
			@PathVariable("fragment") String fragment,
			@PathVariable("number") Integer pageNumber) {

		validatePageNumber(pageNumber);
		if (pageNumber == 1) {
			return new ModelAndView("redirect:" + URL.SEARCH_BY_FRAGMENT);
		}
		return fragmentModelAndView(pageNumber, fragment);
	}

	/**
	 * 查找包含片段文章的map交给searchModelAndView处理
	 */
	private ModelAndView fragmentModelAndView(Integer pageNumber,
			String fragment) {

		Map<String, Object> articlesData = articleService.getByPageByFragment(
				pageNumber, Common.ARTICLES_PER_PAGE, fragment);
		String requestUrl = String.format("/search/%s/", fragment);
		String message = String.format(context.getMessage(
				"search.result.byFragment", null,
				LocaleContextHolder.getLocale()), fragment);

		return searchModelAndView(articlesData, pageNumber, message, requestUrl);
	}

	/**
	 * 将查询好的map返回给指定页面
	 * 
	 * @param articlesData
	 *            文章map
	 * @param pageNumber
	 *            页数
	 * @param message
	 *            格式化的查找信息(/search/查找信息/)
	 * @param requestUrl
	 *            请求url(像是：/category/查找信息/，/tags/查找tag/，/search/查找信息/
	 *            ，/user/用户id/articles/)
	 * @return 填充 {@link ModelAndView}
	 */
	private ModelAndView searchModelAndView(Map<String, Object> articlesData,
			Integer pageNumber, String message, String requestUrl) {

		ModelAndView mav = new ModelAndView(View.SEARCH)
				.addObject("message", message)
				.addObject("requestUrl", requestUrl)
				.addObject("currentPage", pageNumber);

		if (articlesData.isEmpty()) { // articleCount == 0
			return mav;
		}

		Integer pageCount = (Integer) articlesData.get("pageCount");

		@SuppressWarnings("unchecked")
		List<Article> articlesByPage = (List<Article>) articlesData
				.get("articlesByPage");

		return mav.addObject("pageCount", pageCount).addObject(
				"articlesByPage", articlesByPage);
	}

	private void validatePageNumber(int pageNumber) {
		if (pageNumber < 1) {
			throw new NotFoundException("Page < 1");
		}
	}
}