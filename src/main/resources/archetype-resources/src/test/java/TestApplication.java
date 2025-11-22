package ${package};

import org.springframework.boot.SpringApplication;

public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestVjConfiguration.class).run(args);
	}

}
