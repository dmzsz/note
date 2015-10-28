package org.hyy.note.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * user 信息
 * 
 * @author huyanyan
 */
@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
		@NamedQuery(name = "User.GET_BY_LOGIN", query = "FROM User u WHERE u.login = :login"),
		@NamedQuery(name = "User.GET_BY_EMAIL", query = "FROM User u WHERE u.email = :email")})
public class User extends BaseEntity {

	private static final long serialVersionUID = 8091488929047153516L;

	/**
	 * User 邮箱名
	 */
	@Size(min = 4, max = 30, message = "{validation.user.loginSize}")
	@Column(name = "login", nullable = false, unique = true)
	private String login;

	/**
	 * User 的密码
	 */
	@Size(min = 7, max = 60, message = "{validation.user.passwordSize}")
	@Column(name = "password", nullable = false)
	private String password;

	/**
	 * User 的全名
	 */
	@Size(min = 1, max = 50, message = "{validation.user.nameNotBlank}")
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * User 的email
	 */
	@NotBlank(message = "{validation.user.emailNotBlank}")
	@Email(message = "{validation.user.emailValid}")
	@Column(name = "email", nullable = false, unique = true, length = 50)
	private String email;

	/**
	 * 注册的日期时间
	 */
	@Column(name = "registered", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date registered;

	/**
	 * user 的锁定状态 1是TRUE 或 0是FALSE
	 */
	@Column(name = "locked", insertable = false, columnDefinition = "CHAR DEFAULT 0")
	private char locked;

	/**
	 * user的可用状态 0是 fase，1是true
	 */
	@Column(name = "enabled", insertable = false, columnDefinition = "CHAR DEFAULT 1")
	private char enabled;

	/**
	 * User 的角色
	 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<UserRole> roles;

	/**
	 * user 的文章总数
	 */
	@Formula("(SELECT COUNT(a.id) FROM Article a WHERE a.user_id = id)")
	private int articleCount;

	/**
	 * user 的评论总数
	 */
	@Formula("(SELECT COUNT(c.id) FROM Comment c WHERE c.user_id = id)")
	private int commentCount;

	/**
	 * User 的文章 user中有一个属性为author
	 */
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private Set<Article> articles;

	/**
	 * User 的评论 comment中有一个属性为author
	 */
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private Set<Comment> comments;

	/**
	 * 默认构造器生成必需的字段
	 */
	public User() {
		registered = new Date();
		locked = 0;
		enabled = 1;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char isLocked() {
		return locked;
	}

	public void setLocked(char locked) {
		this.locked = locked;
	}

	public char isEnabled() {
		return enabled;
	}

	public void setEnabled(char enabled) {
		this.enabled = enabled;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Date getRegistered() {
		return registered;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result
				+ ((getLogin() == null) ? 0 : getLogin().hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		User other = (User) obj;

		if (getLogin() != null ? !getLogin().equals(other.getLogin()) : other
				.getLogin() != null) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("User[id=%d, login=%s]", getId(), getLogin());
	}
}
