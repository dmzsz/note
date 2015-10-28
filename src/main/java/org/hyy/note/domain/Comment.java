package org.hyy.note.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

/**
 * article的评论
 * 
 * @author huyanyan
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
		@NamedQuery(name = "Comment.GET_ALL_BY_ARTICLE_ID", query = "SELECT com FROM Comment com WHERE com.article.id = :id ORDER BY com.created DESC")})
public class Comment extends BaseEntity {

	private static final long serialVersionUID = -4252140027137381170L;

	/**
	 * Comment 的内容
	 */
	@NotBlank(message = "{validation.comment.content}")
	@Column(name = "content", length = 500, nullable = false)
	private String content;

	/**
	 * 创建的日期时间
	 */
	@Column(name = "created", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/**
	 * 评论的发表者
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;

	/**
	 * 评论的文章
	 */
	@ManyToOne
	@JoinColumn(name = "article_id")
	private Article article;

	/**
	 * 只为了Hibernate
	 */
	protected Comment() {
		created = new Date();
	}

	public Comment(User author, Article article, String content) {
		created = new Date();
		this.author = author;
		this.article = article;
		this.content = content;
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Article getArticle() {
		return article;
	}

	/**
	 * @param article
	 *            article which owns this comment
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result
				+ ((getContent() == null) ? 0 : getContent().hashCode());
		result = prime * result
				+ ((getCreated() == null) ? 0 : getCreated().hashCode());
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

		Comment other = (Comment) obj;

		if (getContent() != null
				? !getContent().equals(other.getContent())
				: other.getContent() != null) {
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Comment[id=%d, author=%s, article_id=%s]",
				getId(), getAuthor() == null ? "null" : getAuthor().getLogin(),
				getArticle() == null ? "null" : getArticle().getId());
	}
}