package ${package}.core.ui;

import ${package}.core.security.SecurityContext;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

	private final transient SecurityContext securityContext;

	public LoginView(SecurityContext securityContext) {
		this.securityContext = securityContext;
		setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

		var i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("Vaadin jOOQ Template");
		i18n.getHeader().setDescription("Login using user/user or admin/admin");
		i18n.setAdditionalInformation(null);
		setI18n(i18n);

		setForgotPasswordButtonVisible(false);
		setOpened(true);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (securityContext.getLoggedInUser().isPresent()) {
			// Already logged in
			setOpened(false);
			event.forwardTo("");
		}

		setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
	}

}
