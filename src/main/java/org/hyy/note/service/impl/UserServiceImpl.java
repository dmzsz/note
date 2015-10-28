package org.hyy.note.service.impl;

import static org.hyy.note.service.util.QueryParameters.setParam;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.hyy.note.domain.User;
import org.hyy.note.domain.UserRole;
import org.hyy.note.exception.NotUniqueUserFieldException;
import org.hyy.note.exception.ServiceException;
import org.hyy.note.repository.GenericRepository;
import org.hyy.note.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现接口 {@link UserService}
 * 
 * @author huyanyan
 */
@Service("UserService")
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	private GenericRepository<UserRole, Long> roleRepository;
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserServiceImpl(GenericRepository<User, Long> repository,
			GenericRepository<UserRole, Long> roleRepository,
			PasswordEncoder bCryptPasswordEncoder) {
		super(repository);
		this.roleRepository = roleRepository;
		this.roleRepository.setType(UserRole.class);
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * Add user into repository with default role ("ROLE_USER") and encoded password
	 * 
	 * @see org.hyy.note.service.impl.AbstractServiceImpl#add(java.io.Serializable)
	 */
	@Override
	@Transactional(readOnly = false)
	public void add(User user) {
		try {
			// 构造器中的设置没有起作用
//			roleRepository.setType(UserRole.class);
			UserRole defaultRole = roleRepository.getByNamedQuery(
					"UserRole.GET_BY_AUTHORITY",
					setParam("authority", "ROLE_USER").buildMap());
			Set<UserRole> userRoles = new HashSet<UserRole>();
			userRoles.add(defaultRole);
			user.setRoles(userRoles);
			
			String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			
			repository.add(user);
			LOG.info("ADDED: " + user);
		} catch (Exception e) {
			if (e.getCause().getClass() == ConstraintViolationException.class) {
				ConstraintViolationException ce = (ConstraintViolationException) e.getCause();
				if (ce.getConstraintName().indexOf("LOGIN") != -1) {
					throw new NotUniqueUserFieldException("login");
				} else if (ce.getConstraintName().indexOf("EMAIL") != -1) {
					throw new NotUniqueUserFieldException("email");
				}
			}
			String message = String.format("Unable to add %s", user);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.UserService#getByLogin(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public User getByLogin(String login) {
		try {
			User user = repository.getByNamedQuery(
					"User.GET_BY_LOGIN", setParam("login", login).buildMap());
			if (user != null) {
				user.getRoles().size();
			}
			return user;
		} catch (Exception e) {
			String message = String.format("Unable to get user by login=%s", login);
			throw new ServiceException(message, e);
		}
	}

	/**
	 * @see org.hyy.note.service.UserService#getByEmail(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public User getByEmail(String email) {
		try {
			User user = repository.getByNamedQuery(
					"User.GET_BY_EMAIL",
					setParam("email", email).buildMap());
			return user;
		} catch (Exception e) {
			String message = String.format("Unable to get user by email=%s", email);
			throw new ServiceException(message, e);
		}
	}
}
