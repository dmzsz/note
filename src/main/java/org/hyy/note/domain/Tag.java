package org.hyy.note.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

/**
 * article的标签
 * 
 * @author huyanyan
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
		@NamedQuery(name = "Tag.GET_ALL", query = "FROM Tag t ORDER BY t.name"),
		@NamedQuery(name = "Tag.GET_ALL_NAMES", query = "SELECT t.name FROM Tag t ORDER BY t.name"),
		@NamedQuery(name = "Tag.GET_BY_NAME", query = "FROM Tag t WHERE t.name = :name")})
public class Tag extends BaseEntity {

	private static final long serialVersionUID = 984410247121721301L;

	/**
	 * Tag name
	 */
	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;

	/**
	 * Article 的tag总数
	 */
	@Formula("(SELECT COUNT(a.id) FROM article_tag at "
			+ "JOIN Article a ON at.article_id = a.id "
			+ "JOIN Tag t ON at.tag_id = t.id WHERE t.id = id)")
	private int articleCount;

	/**
	 * Articles 的所有tag
	 */
	@ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
	private Set<Article> articles;

	public Tag() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getArticleCount() {
		return articleCount;
	}

	/**
	 * 
	 * @return
	 */
	public int getScale() {
		// need to change
		return articleCount > 9 ? 9 : articleCount;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
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

		Tag other = (Tag) obj;

		if (getName() != null ? !getName().equals(other.getName()) : other
				.getName() != null) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Tag[id=%d, name=%s]", getId(), getName());
	}
}
