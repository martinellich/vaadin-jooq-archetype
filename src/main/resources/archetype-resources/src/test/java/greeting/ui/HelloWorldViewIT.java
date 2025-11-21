package ${package}.greeting.ui;

import ${package}.core.ui.PlaywrightIT;
import in.virit.mopo.Mopo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldViewIT extends PlaywrightIT {

	@Test
	void say_hello() {
		page.navigate("http://localhost:%d".formatted(localServerPort));
		var mopo = new Mopo(page);

		var appName = page.locator("h1");
		assertThat(appName.innerText()).isEqualTo("Vaadin jOOQ Template");

		var title = page.locator("h2.text-l.m-0");
		assertThat(title.innerText()).isEqualTo("Hello World");

		page.locator("vaadin-text-field[id='name'] > input").fill("Test");
		mopo.click("id=say-hello");

		var notification = page.locator("vaadin-notification-card");
		System.out.println(notification.innerText());
		assertThat(notification.innerText()).endsWith("Hello Test");
	}

}