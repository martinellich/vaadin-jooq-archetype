package ${package}.core.ui.layout;

import ${package}.core.security.SecurityContext;
import ${package}.core.ui.UserView;
import ${package}.greeting.ui.HelloWorldView;
import ${package}.person.ui.PersonView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Locale;

@AnonymousAllowed
@Layout
public class MainLayout extends AppLayout {

	private final transient SecurityContext securityContext;

	private final AccessAnnotationChecker accessAnnotationChecker;

	private final H2 viewTitle = new H2();

	public MainLayout(SecurityContext securityContext, AccessAnnotationChecker accessAnnotationChecker) {
		this.securityContext = securityContext;
		this.accessAnnotationChecker = accessAnnotationChecker;

		setPrimarySection(Section.DRAWER);
		addDrawerContent();
		addHeaderContent();
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		viewTitle.setText(getCurrentPageTitle());
	}

	private void addHeaderContent() {
		var toggle = new DrawerToggle();
		toggle.setAriaLabel("Menu toggle");

		viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		addToNavbar(true, toggle, viewTitle);
	}

	private void addDrawerContent() {
		var appName = new H1("Vaadin jOOQ Template");
		appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		var header = new Header(appName);

		var scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller, createFooter());
	}

	private SideNav createNavigation() {
		var nav = new SideNav();

		if (accessAnnotationChecker.hasAccess(HelloWorldView.class)) {
			nav.addItem(
					new SideNavItem(getTranslation("Hello World"), HelloWorldView.class, VaadinIcon.GLOBE.create()));
		}
		if (accessAnnotationChecker.hasAccess(PersonView.class)) {
			nav.addItem(new SideNavItem(getTranslation("Persons"), PersonView.class, VaadinIcon.ARCHIVES.create()));
		}
		if (accessAnnotationChecker.hasAccess(UserView.class)) {
			nav.addItem(new SideNavItem(getTranslation("Users"), UserView.class, VaadinIcon.USER.create()));
		}

		return nav;
	}

	private Footer createFooter() {
		var footer = new Footer();
		var verticalLayout = new VerticalLayout();
		footer.add(verticalLayout);

		var locale = UI.getCurrent().getSession().getLocale();

		var languageSwitchEn = new Button("EN");
		languageSwitchEn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		languageSwitchEn.setEnabled(!Locale.ENGLISH.getLanguage().equals(locale.getLanguage()));
		languageSwitchEn.addClickListener(e -> switchLanguage(Locale.ENGLISH.getLanguage()));

		var languageSwitchDe = new Button("DE");
		languageSwitchDe.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		languageSwitchDe.setEnabled(!Locale.GERMAN.getLanguage().equals(locale.getLanguage()));
		languageSwitchDe.addClickListener(e -> switchLanguage(Locale.GERMAN.getLanguage()));

		var languageLayout = new HorizontalLayout(languageSwitchEn, languageSwitchDe);
		languageLayout.addClassNames(LumoUtility.Margin.SMALL, LumoUtility.Margin.Top.XLARGE);
		verticalLayout.add(languageLayout);

		var optionalUserRecord = securityContext.getLoggedInUser();
		if (optionalUserRecord.isPresent()) {
			var user = optionalUserRecord.get();

			var avatar = new Avatar("%s %s".formatted(user.getFirstName(), user.getLastName()));
			avatar.setImageHandler(downloadEvent -> {
				downloadEvent.setFileName("profile-pic.jpg");
				downloadEvent.getOutputStream().write(user.getPicture());
			});
			avatar.setThemeName("xsmall");
			avatar.getElement().setAttribute("tabindex", "-1");

			var userMenu = new MenuBar();
			userMenu.setThemeName("tertiary-inline contrast");

			var userName = userMenu.addItem("");

			var div = new Div();
			div.add(avatar);
			div.add("%s %s".formatted(user.getFirstName(), user.getLastName()));
			div.add(LumoIcon.DROPDOWN.create());
			div.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.Gap.SMALL);
			userName.add(div);
			userName.getSubMenu().addItem(getTranslation("Sign out"), e -> securityContext.logout());

			verticalLayout.add(userMenu);
		}
		else {
			var loginLink = new Anchor("login", getTranslation("Sign in"));
			verticalLayout.add(loginLink);
		}

		return footer;
	}

	private void switchLanguage(String language) {
		UI.getCurrent().getSession().setLocale(Locale.of(language, UI.getCurrent().getLocale().getCountry()));
		UI.getCurrent().getPage().reload();
	}

	private String getCurrentPageTitle() {
		if (getContent() instanceof HasDynamicTitle hasDynamicTitle) {
			return hasDynamicTitle.getPageTitle() == null ? "" : hasDynamicTitle.getPageTitle();
		}
		else if (getContent().getClass().getAnnotation(PageTitle.class) != null) {
			return getContent().getClass().getAnnotation(PageTitle.class).value();
		}
		else {
			return MenuConfiguration.getPageHeader(getContent()).orElse("");
		}
	}

}
