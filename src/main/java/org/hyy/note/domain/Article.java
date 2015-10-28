package org.hyy.note.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 文章
 * 
 * @author huyanyan
 *
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
		@NamedQuery(name = "Article.GET_ALL", query = "FROM Article a ORDER BY a.created DESC"),
		@NamedQuery(name = "Article.GET_ALL_BY_USER_ID", query = "FROM Article a WHERE a.author.id = :id ORDER BY a.created DESC"),
		@NamedQuery(name = "Article.GET_ALL_BY_CATEGORY_NAME", query = "FROM Article a WHERE a.category.name = :name ORDER BY a.created DESC"),
		@NamedQuery(name = "Article.GET_ALL_BY_TAG_NAME", query = "SELECT a FROM Tag t JOIN t.articles a WHERE t.name = :name ORDER BY a.created DESC"),
		@NamedQuery(name = "Article.GET_ALL_BY_FRAGMENT", query = "FROM Article a WHERE a.content LIKE :fragment ORDER BY a.created DESC"),
		@NamedQuery(name = "Article.GET_COUNT_BY_FRAGMENT", query = "SELECT COUNT(a.id) FROM Article a WHERE a.content LIKE :fragment")})
public class Article extends BaseEntity {

	private static final long serialVersionUID = 38150497082508411L;

	/**
	 * Article 的标题
	 */
	@NotBlank(message = "{validation.article.title}")
	@Column(name = "title", nullable = false, length = 100)
	private String title;

	/**
	 * Article 的预览
	 */
	@NotBlank(message = "{validation.article.preview}")
	@Column(name = "preview", nullable = false)
	private String preview;

	/**
	 * Article 的内容
	 */
	@NotBlank(message = "{validation.article.content}")
	@Lob
	@Basic(fetch = FetchType.EAGER)
	// mysql
	@Column(name = "content", columnDefinition = "TEXT", nullable = true)
	// oracle
	// @Column(name = "content", columnDefinition = "CLOB", nullable = true)
	private String content;

	/**
	 * Article 创建的日期时间
	 */
	@Column(name = "created", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/**
	 * Article 改变的日期时间
	 */
	@Column(name = "last_modified")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	/**
	 * Article的view总数
	 */
	@Column(name = "view_count", insertable = false, columnDefinition = "INT DEFAULT 0")
	private int viewCount;

	/**
	 * Article的comment总数
	 */
	@Formula("(SELECT COUNT(c.id) FROM Comment c WHERE c.article_id = id)")
	private int commentCount;

	/**
	 * article的Author
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User author;

	/**
	 * article的Category
	 */
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	/**
	 * article的Comments
	 */
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("created desc")
	private Set<Comment> comments;

	/**
	 * article的Tags
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
			CascadeType.MERGE})
	@JoinTable(name = "article_tag", joinColumns = {@JoinColumn(name = "article_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
	@OrderBy("name")
	private Set<Tag> tags;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
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

		Article other = (Article) obj;

		if (getTitle() != null ? !getTitle().equals(other.getTitle()) : other
				.getTitle() != null) {
			return false;
		}
		if (getCreated() != null
				? getCreated().compareTo(other.getCreated()) != 0
				: other.getCreated() != null) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result
				+ ((getTitle() == null) ? 0 : getTitle().hashCode());
		result = prime * result
				+ ((getCreated() == null) ? 0 : getCreated().hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Article[id=%d, author=%s]", getId(),
				getAuthor() == null ? "null" : getAuthor().getLogin());
	}
}
