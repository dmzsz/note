package org.hyy.note.web.constants;

/**
 * Collects all URL's as static constants in one place
 * 
 * @author huyanyan
 */
public class URL {

	/**
	 * 主要的URL
	 */
	public static final String HOME = "/";
	public static final String HOME_CUSTOM_PAGE = "/page/{number}";
	public static final String ABOUT = "/about";
	public static final String ARCHIVES = "/archives";
	public static final String ARCHIVES_CUSTOM_PAGE = "/archives/page/{number}";
	public static final String CONTACTS = "/contacts";
	public static final String ERROR = "/error";
	
	/**
	 * user相关的URL
	 */
	public static final String SIGN_IN = "/signin";
	public static final String SIGN_IN_FAILURE = "/signinfailure";
	public static final String SIGN_UP = "/signup";
	public static final String CHECK_LOGIN = "/check-login";
	public static final String CHECK_EMAIL = "/check-email";
	public static final String USER_PROFILE = "/user/{id}";
	
	/**
	 * 上传URL
	 */
	public static final String UPLOAD_IMAGE = "**/uploadimage";
	public static final String SHOW_IMAGE = "**/images/{name}.{type}";
	
	/**
	 * Article相关URL
	 */
	public static final String SHOW_ARTICLE = "/{id}";
	public static final String ADD_COMMENT = "/{id}/addcomment";
	public static final String DELETE_COMMENT = "/comment/{id}/deletecomment";
	public static final String ADD_ARTICLE = "/add";
	public static final String EDIT_ARTICLE = "/{id}/edit";
	public static final String DELETE_ARTICLE = "/{id}/delete";
	public static final String CANCEL = "/{id}/cancel";
	public static final String TAGS_AUTOCOMPLETE = "**/tags/autocomplete";
	
	/**
	 * 搜索URL
	 */
	public static final String SEARCH_BY_CATEGORY = "/category/{name}";
	public static final String SEARCH_BY_CATEGORY_CUSTOM_PAGE = "/category/{name}/page/{number}";
	public static final String SEARCH_BY_FRAGMENT = "/search/{fragment}";
	public static final String SEARCH_BY_FRAGMENT_CUSTOM_PAGE = "/search/{fragment}/page/{number}";
	public static final String SEARCH_BY_FRAGMENT_SUBMIT = "/search";
	public static final String SEARCH_BY_TAG = "/tags/{name}";
	public static final String SEARCH_BY_TAG_CUSTOM_PAGE = "/tags/{name}/page/{number}";
	public static final String SEARCH_BY_USER = "/user/{id}/articles";
	public static final String SEARCH_BY_USER_CUSTOM_PAGE = "/user/{id}/articles/page/{number}";
}
