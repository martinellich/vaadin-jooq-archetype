package ${package}.greeting.ui;

import ${package}.core.ui.AbstractBrowserlessTest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldViewTest extends AbstractBrowserlessTest {

	@Test
	void say_hello() {
		navigate(HelloWorldView.class);

		Div appName = $(Div.class).withClassName("text-xl").single();
		assertThat(appName.getText()).isEqualTo("Vaadin jOOQ Template");

		H2 title = $(H2.class).single();
		assertThat(title.getText()).isEqualTo("Hello World");

		test($(TextField.class).id("name")).setValue("Test");
		test($(Button.class).id("say-hello")).click();

		Notification notification = $(Notification.class).single();
		assertThat(test(notification).getText()).isEqualTo("Hello Test");
	}

}
