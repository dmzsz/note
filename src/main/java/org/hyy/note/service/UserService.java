package org.hyy.note.service;

import org.hyy.note.domain.User;


/**
 * user相关服务
 * 
 * @author huyanyan
 */
public interface UserService extends AbstractService<User> {

	User getByLogin(String login);
	
	User getByEmail(String email);
}
