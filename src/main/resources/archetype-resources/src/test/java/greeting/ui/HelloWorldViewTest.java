package ${package}.greeting.ui;

import ${package}.core.ui.AbstractBrowserlessTest;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldViewTest extends AbstractBrowserlessTest {

	@Test
	void shows_hello_world_title() {
		navigate(HelloWorldView.class);

		Div appName = find(Div.class).withClassName("text-xl").single();
		assertThat(appName.getText()).isEqualTo("Vaadin jOOQ Template");

		H2 title = find(H2.class).single();
		assertThat(title.getText()).isEqualTo("Hello World");
	}

}
