package org.hyy.note.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hyy.note.domain.User;
import org.hyy.note.exception.NotUniqueUserFieldException;
import org.hyy.note.service.ArticleService;
import org.hyy.note.service.UserService;
import org.hyy.note.web.constants.URL;
import org.hyy.note.web.constants.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * user相关控制器
 * 
 * @author huyanyan
 */
@Controller
public class UserController {

	// 不能使用@Autowired userService的构造器没办法加载
	@Resource
	private UserService userService;

	/**
	 * 登陆页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SIGN_IN)
	public void signinPage() {
	}

	/**
	 * 登录失败
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SIGN_IN_FAILURE)
	public ModelAndView signinFailure(Model model) {

		return new ModelAndView(View.SIGN_IN).addObject("error", "true");
	}

	/**
	 * 注册页
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SIGN_UP)
	public String signupPage() {

		return View.SIGN_UP;
	}

	/**
	 * 提交注册
	 */
	@RequestMapping(method = RequestMethod.POST, value = URL.SIGN_UP)
	public String registerSubmit(Model model, @Valid @ModelAttribute User user,
			BindingResult result, RedirectAttributes attr,
			HttpServletRequest request) {

		if (result.hasErrors()) {
			// 使用addFlashAttribute,参数不会出现在url地址栏中
			attr.addFlashAttribute(
					"org.springframework.validation.BindingResult.user", result);
			attr.addFlashAttribute("user", user);
			return "redirect:" + URL.SIGN_UP;
		}

		try {
			userService.add(user);
		} catch (NotUniqueUserFieldException e) {
			String field = e.getMessage();
			result.rejectValue(field, "validation.user.unique." + field);
			user.setPassword("");
			return View.SIGN_UP;
		}

		model.addAttribute("messageProperty", "success.registration");
		model.addAttribute("url", request.getServletContext().getContextPath());

		return View.SUCCESS;
	}

	/**
	 * 检验user login是否可用
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.CHECK_LOGIN)
	@ResponseBody
	public String checkLogin(@RequestParam String login) {
		String checkLogin = userService.getByLogin(login) == null ? "ok" : "no";
		System.out.println("checkEmail:" + checkLogin);
		return checkLogin;
	}

	/**
	 * 检验user email是否可用
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.CHECK_EMAIL)
	@ResponseBody
	public String checkEmail(@RequestParam String email) {
		String checkEmail = userService.getByEmail(email) == null ? "ok" : "no";
		System.out.println("checkEmail:" + checkEmail);
		return checkEmail;
	}

	/**
	 * user信息页
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET, value = URL.USER_PROFILE)
	public String profilePage(Model model, @PathVariable("id") Long userId) {
		
		User user = userService.get(userId);
		System.out.println(user);
		model.addAttribute("user", user);

		return View.PROFILE;
	}

	/**
	 * @return 新实例 {@link User}
	 */
	@ModelAttribute("user")
	public User populateUser() {
		return new User();
	}
}