package org.hyy.note.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.hyy.note.domain.Article;
import org.hyy.note.exception.NotFoundException;
import org.hyy.note.service.ArticleService;
import org.hyy.note.service.CategoryService;
import org.hyy.note.service.TagService;
import org.hyy.note.web.constants.Common;
import org.hyy.note.web.constants.URL;
import org.hyy.note.web.constants.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主页控制器和静态页(关于页和联系页等)
 * 
 * @author huyanyan
 */
@Controller
public class MainController {
	@Resource
	private ArticleService articleService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private TagService tagService;

	/**
	 * home页，获得news在首页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.HOME)
	public ModelAndView home(HttpSession session) {

		return homeModelAndView(1, session);
	}

	/**
	 * home页，页面跳转
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.HOME_CUSTOM_PAGE)
	public ModelAndView home(@PathVariable("number") Integer pageNumber,
			HttpSession session) {

		if (pageNumber == 1) {
			return new ModelAndView("redirect:" + URL.HOME);
		}

		return homeModelAndView(pageNumber, session);
	}

	/**
	 * home页面跳转
	 */
	@SuppressWarnings("unchecked")
	private ModelAndView homeModelAndView(Integer pageNumber, HttpSession session) {

		if (pageNumber < 1) {
			throw new NotFoundException("Page < 1");
		}

		Map<String, Object> articlesData = articleService.getByPage(pageNumber,
				Common.ARTICLES_PER_PAGE);
		
		Integer pageCount = (Integer) articlesData.get("pageCount");
		List<Article> articlesByPage = (List<Article>) articlesData.get("articlesByPage");

		if (session.getAttribute("categories") == null) {
			session.setAttribute("categories", categoryService.getAllTransactionally());
		}
		if (session.getAttribute("tags") == null) {
			session.setAttribute("tags", tagService.getAllTransactionally());
		}

		return new ModelAndView(View.HOME).addObject("pageCount", pageCount)
				.addObject("articlesByPage", articlesByPage)
				.addObject("currentPage", pageNumber)
				.addObject("requestUrl", URL.HOME);
	}

	/**
	 * About页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.ABOUT)
	public String aboutPage() {

		return View.ABOUT;
	}

	/**
	 * Contacts页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.CONTACTS)
	public String contactsPage() {

		return View.CONTACTS;
	}
	
	/**
	 * 存档记录
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.ARCHIVES)
	public ModelAndView archive(HttpSession session) {

		return archiveModelAndView(1, session);
	}
	
	/**
	 * 存档页，页面跳转
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.ARCHIVES_CUSTOM_PAGE)
	public ModelAndView archive(@PathVariable("number") Integer pageNumber,
			HttpSession session) {

		if (pageNumber == 1) {
			return new ModelAndView("redirect:" + URL.ARCHIVES);
		}

		return archiveModelAndView(pageNumber, session);
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView archiveModelAndView(Integer pageNumber, HttpSession session) {

		if (pageNumber < 1) {
			throw new NotFoundException("Page < 1");
		}

		Map<String, Object> articlesData = articleService.getByPage(pageNumber,
				Common.ARTICLES_ARCHIVE_PER_PAGE);
		
		Integer pageCount = (Integer) articlesData.get("pageCount");
		List<Article> articlesByPage = (List<Article>) articlesData.get("articlesByPage");

		if (session.getAttribute("categories") == null) {
			session.setAttribute("categories", categoryService.getAllTransactionally());
		}
		if (session.getAttribute("tags") == null) {
			session.setAttribute("tags", tagService.getAllTransactionally());
		}

		return new ModelAndView(View.ARCHIVE).addObject("pageCount", pageCount)
				.addObject("articlesByPage", articlesByPage)
				.addObject("currentPage", pageNumber)
				.addObject("requestUrl", URL.ARCHIVES);
	}

}
