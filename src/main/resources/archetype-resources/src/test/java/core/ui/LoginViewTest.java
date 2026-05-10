package ${package}.core.ui;

import com.vaadin.flow.component.html.H2;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginViewTest extends AbstractBrowserlessTest {

	@Test
	void navigate_to_login() {
		navigate(LoginView.class);

		H2 title = $(H2.class).withText("Login").single();
		assertThat(title).isNotNull();
	}

}
