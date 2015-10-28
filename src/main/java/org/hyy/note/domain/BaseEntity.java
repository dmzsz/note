package org.hyy.note.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 基础类. 定义主键(id)和版本(version)
 * 
 * @author huyanyan
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1520556867799623763L;

	/**
	 * persistent对象的主键
	 */
	@Id
//	@GeneratedValue(generator = "paymentableGenerator")
//	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	protected Long id;

	/**
	 * persistent对象的版本
	 */
//	@Version
//	@Column(nullable = false, insertable = false, columnDefinition = "INT DEFAULT 0")
//	Integer version;

	/**
	 * get persistent对象的主键
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
