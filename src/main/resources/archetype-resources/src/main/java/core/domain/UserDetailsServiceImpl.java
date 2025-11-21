package ${package}.core.domain;

import ${package}.db.tables.records.UserRecord;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserDAO userDAO;

	public UserDetailsServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userDAO.findById(username)
			.orElseThrow(() -> new UsernameNotFoundException("No user present with username: " + username));
		return new User(user.getUsername(), user.getHashedPassword(), getAuthorities(user));
	}

	private List<SimpleGrantedAuthority> getAuthorities(UserRecord user) {
		return userDAO.findRolesByUsername(user.getUsername())
			.stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
			.toList();
	}

}
