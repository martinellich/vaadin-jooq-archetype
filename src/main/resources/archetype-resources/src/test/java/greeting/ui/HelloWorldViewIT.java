package ${package}.greeting.ui;

import ${package}.core.ui.PlaywrightIT;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldViewIT extends PlaywrightIT {

	@Test
	void shows_hello_world_title() {
		page.navigate("http://localhost:%d".formatted(localServerPort));

		var appName = page.locator("div.text-xl");
		assertThat(appName.innerText()).isEqualTo("Vaadin jOOQ Template");

		var title = page.locator("h2.text-l.m-0");
		assertThat(title.innerText()).isEqualTo("Hello World");
	}

}
