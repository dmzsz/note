package org.hyy.note.service;

import org.hyy.note.domain.Comment;


/**
 * Provides comment-related operations
 * 
 * @author huyanyan
 */
public interface CommentService extends AbstractService<Comment> {
	/**
	 * 保存评论
	 * 
	 * @param content 评论内容
	 * @param authorId 作者的id
	 * @param articleId article的id
	 */
	long addComment(String content, Long authorId, Long articleId);
	
}
