package ${package}.core.security;

import ${package}.core.domain.UserDAO;
import ${package}.db.tables.records.UserRecord;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityContext {

	private final AuthenticationContext authenticationContext;

	private final UserDAO userDAO;

	public SecurityContext(AuthenticationContext authenticationContext, UserDAO userDAO) {
		this.userDAO = userDAO;
		this.authenticationContext = authenticationContext;
	}

	public Optional<UserRecord> getLoggedInUser() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
			return userDAO.findById(jwt.getSubject());
		}
		return Optional.empty();
	}

	public void logout() {
		authenticationContext.logout();
	}

}
