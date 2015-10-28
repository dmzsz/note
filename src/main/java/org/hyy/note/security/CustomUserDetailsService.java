package org.hyy.note.security;

import java.util.Collection;
import java.util.HashSet;

import org.hyy.note.domain.UserRole;
import org.hyy.note.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 接口于 {@link UserDetailsService}
 * 
 * @author huyanyan
 */
@Component("UserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	/**
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		if ("".equals(login)) {
			throw new UsernameNotFoundException("User not found");
		}
		
		org.hyy.note.domain.User user = userService.getByLogin(login);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;

		return new User(
				user.getLogin(),
				user.getPassword(),
				true,
				accountNonExpired,
				credentialsNonExpired,
				true,
				getAuthorities(user.getRoles()));
	}
	
	/**
	 * 获取角色. Roles must be {@link GrantedAuthority} implementation
	 * 
	 * @param roles
	 * @return
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(
			Collection<UserRole> roles) {

		Collection<GrantedAuthority> resultRoles = new HashSet<GrantedAuthority>();
		for (UserRole role : roles) {
			resultRoles.add(role);
		}
		return resultRoles;
	}
}
