package org.hyy.note.web;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hyy.note.domain.Article;
import org.hyy.note.domain.Tag;
import org.hyy.note.service.ArticleService;
import org.hyy.note.service.CategoryService;
import org.hyy.note.service.CommentService;
import org.hyy.note.service.TagService;
import org.hyy.note.web.constants.URL;
import org.hyy.note.web.constants.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * article相关actions
 * 
 * @author huyanyan
 */
@Controller
@RequestMapping("/article")
@SessionAttributes("article")
// session中会有name=article value=article对象的属性
public class ArticleController {
	
	@Autowired
	private ApplicationContext context;
	@Resource
	private ArticleService articleService;
	@Resource
	private CommentService commentService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private TagService tagService;

//	@Autowired
//	public ArticleController(ArticleService articleService,
//			CategoryService categoryService, TagService tagService,
//			ApplicationContext context) {
//		this.context = context;
//		this.articleService = articleService;
//		this.categoryService = categoryService;
//		this.tagService = tagService;
//	}

	
	/**
	 * 查看article，并记录查看总数
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SHOW_ARTICLE)
	public String viewArticlePage(Model model,
			@PathVariable("id") Long articleId, HttpSession session,
			HttpServletRequest request, HttpServletResponse resp) {
		// 跨域访问才算数
		boolean needIncreaseViewCount = request.getHeader("Referer") != null
				&& !request.getHeader("Referer").equals(
						request.getRequestURL().toString());
		Long loggedUserId = (Long) session.getAttribute("loggedUserId"); // 返回null为未登录

//		Article currentArticle = articleService.get(articleId, loggedUserId,
//				needIncreaseViewCount);
		Article currentArticle = articleService.get(articleId, null,
				false);

		model.addAttribute("article", currentArticle);

		return View.VIEW_ARTICLE;
	}

	/**
	 * 添加评论
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST, value = URL.ADD_COMMENT, produces="application/json")
	@ResponseBody
	public Map<String, Comparable> addCommentSubmit(HttpSession session,
			@RequestParam(value = "articleId") Long articleId,
			@RequestParam(value = "content", defaultValue = "") String content,
			@RequestParam(value = "deleteable", defaultValue = "false") boolean deleteable) {
		Map<String, Comparable> map = new HashMap<String, Comparable>();
		if (content.isEmpty()) {
			 map.put("error", context.getMessage("validation.comment.content", null,
					LocaleContextHolder.getLocale()));
			 return map;
		}
		long authorId = (Long) session.getAttribute("loggedUserId");
		Long id = commentService.addComment(content, authorId, articleId);
		
		map.put("status", "ok");
		map.put("commentId", id);
		map.put("deleteable", deleteable);
		return map;
	}
	
	/**
	 * 删除评论
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole(['ROLE_AUTHOR'])")
	@RequestMapping(method = RequestMethod.POST, value = URL.DELETE_COMMENT)
	@ResponseBody
	public Map<String, Comparable> deleteCommentSubmit(HttpSession session,
			@RequestParam(value = "commentId") Long id) {
		
		Map<String, Comparable> map = new HashMap<String, Comparable>();
		
		try {
			commentService.deleteByIdTransactionally(id);
		} catch (Exception e) {
			 map.put("error", "删除错误");
			 return map;
		}
		
		map.put("status", "ok");
		map.put("commentId", id);
		return map;
	}

	/**
	 * 添加新article的页面
	 */
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@RequestMapping(method = RequestMethod.GET, value = URL.ADD_ARTICLE)
	public String addArticlePage(Model model) {

		// 清除 article-sessionAttribute
		model.addAttribute("article", populateArticle());

		return View.EDIT_ARTICLE;
	}

	/**
	 * 提交新article
	 */
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@RequestMapping(method = RequestMethod.POST, value = URL.ADD_ARTICLE)
	public String addArticleSubmit(
			Model model,
			@Valid @ModelAttribute("article") Article article,
			BindingResult result,
			HttpSession session,
			SessionStatus status,
			@RequestParam(value = "categoryName", defaultValue = "") String categoryName,
			@RequestParam(value = "tagString", defaultValue = "") String tagString) {

		if (result.hasErrors()) {
			model.addAttribute("tagString", tagString);
			return View.EDIT_ARTICLE;
		}

		Long authorId = (Long) session.getAttribute("loggedUserId");
		articleService.add(article, authorId, categoryName, tagString);
		status.setComplete();
		updateSessionAttributes(session);

		return "redirect:/article/" + article.getId();
	}

	/**
	 * 编辑article页，ROLE_ADMIN没有权限修改
	 */
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@RequestMapping(method = RequestMethod.GET, value = URL.EDIT_ARTICLE)
	public String editArticlePage(Model model,
			@PathVariable("id") Long articleId, HttpSession session,
			HttpServletRequest request) {

		Long loggedUserId = (Long) session.getAttribute("loggedUserId");
		Article articleToEdit = articleService.getTransactionally(articleId);

		if (loggedUserId != articleToEdit.getAuthor().getId()
				&& !request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/article/" + articleId;
		}

		Set<Tag> tags = articleToEdit.getTags();
		if (tags != null && !tags.isEmpty()) {
			String tagString = tagService.getTagString(tags);
			model.addAttribute("tagString", tagString);
		}
		model.addAttribute("category", articleToEdit.getCategory().getName());
		model.addAttribute("article", articleToEdit);

		return View.EDIT_ARTICLE;
	}

	/**
	 * 编辑 article
	 */
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@RequestMapping(method = RequestMethod.POST, value = URL.EDIT_ARTICLE)
	public String editArticleSubmit(
			Model model,
			@Valid @ModelAttribute("article") Article article,
			BindingResult result,
			HttpSession session,
			SessionStatus status,
			@RequestParam(value = "categoryName", defaultValue = "") String categoryName,
			@RequestParam(value = "tagString", defaultValue = "") String tagString) {

		if (result.hasErrors()) {
			model.addAttribute("tagString", tagString);
			return View.EDIT_ARTICLE;
		}

		articleService.update(article, categoryName, tagString);
		status.setComplete();
		updateSessionAttributes(session);

		return "redirect:/article/" + article.getId();
	}

	/**
	 * 删除article
	 */
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@RequestMapping(method = RequestMethod.GET, value = URL.DELETE_ARTICLE)
	public ModelAndView deleteArticle(@PathVariable("id") Long id,
			HttpSession session, SessionStatus status,
			HttpServletRequest request) {

		articleService.deleteByIdTransactionally(id);
		status.setComplete();
		updateSessionAttributes(session);

		return new ModelAndView(View.SUCCESS)
				.addObject("messageProperty","success.article.deleted")
				.addObject("url",request.getServletContext().getContextPath());
	}

	/**
	 * 取消编辑article，销除session属性
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.CANCEL)
	public String cancelArticleEdit(@PathVariable("id") Long id,
			SessionStatus status) {

		status.setComplete();
		if (id == 0)
			return "redirect:" + URL.HOME;

		return "redirect:/article/" + id;
	}

	/**
	 * @return 返回新实例{@link Article}
	 */
	@ModelAttribute("article")
	public Article populateArticle() {
		return new Article();
	}
	
	/**
	 * Tags字符串转换为json
	 * 
	 * @return tags-json
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.TAGS_AUTOCOMPLETE)
	@ResponseBody
	public String tagsAutocomplete() {

		return tagService.getAutocompleteJson();
	}
	
	/**
	 * 更新session属性，添加categories和tags
	 * 
	 * @param session
	 */
	private void updateSessionAttributes(HttpSession session) {
		session.setAttribute("categories",
				categoryService.getAllTransactionally());
		session.setAttribute("tags", tagService.getAllTransactionally());
	}
}