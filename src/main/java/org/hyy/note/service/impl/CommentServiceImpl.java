package org.hyy.note.service.impl;

import org.hyy.note.domain.Article;
import org.hyy.note.domain.Comment;
import org.hyy.note.domain.User;
import org.hyy.note.exception.ServiceException;
import org.hyy.note.repository.GenericRepository;
import org.hyy.note.service.ArticleService;
import org.hyy.note.service.CommentService;
import org.hyy.note.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("CommentService")
public class CommentServiceImpl extends AbstractServiceImpl<Comment>
		implements CommentService {
	
	private UserService userService;
	private ArticleService articleService;
	
	@Autowired
	public CommentServiceImpl(GenericRepository<Comment, Long> repository,
			ArticleService articleService,
			UserService userService) {
		super(repository);
		this.articleService = articleService;
		this.userService = userService;
	}

	/**
	 * @see org.hyy.note.service.CommentService#addComment(org.hyy.note.domain.Comment,
	 *      java.lang.Long, org.hyy.note.domain.Article)
	 */
	@Override
	@Transactional(readOnly = false)
	public long addComment(String content, Long authorId, Long articleId) {
		Comment comment = null;
		try {
			User author = userService.get(authorId);
			Article article = articleService.get(articleId);
			comment = new Comment(author, article, content);
			return repository.add(comment);
		} catch (Exception e) {
			String message = String.format("Unable to add %s", comment);
			throw new ServiceException(message, e);
		}
	}

}
