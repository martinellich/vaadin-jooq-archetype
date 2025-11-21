package ${package}.core.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.mvysny.kaributesting.v10.LocatorJ._get;

class LoginViewTest extends KaribuTest {

	@BeforeEach
	void setUp() {
		logout(); // Ensure we start logged out
		UI.getCurrent().navigate(LoginView.class);
	}

	@Test
	void navigate_to_login() {
		var title = _get(H2.class, spec -> spec.withText("Login"));
		Assertions.assertThat(title).isNotNull();
	}

}