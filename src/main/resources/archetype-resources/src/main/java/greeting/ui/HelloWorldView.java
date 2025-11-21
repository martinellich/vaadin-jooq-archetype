package ${package}.greeting.ui;

import ${package}.core.ui.components.Notifier;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "hello")
@RouteAlias(value = "")
public class HelloWorldView extends VerticalLayout implements HasDynamicTitle {

	public HelloWorldView() {
		var name = new TextField(getTranslation("Your name"));
		name.setId("name");

		var sayHello = new Button(getTranslation("Say hello"));
		sayHello.setId("say-hello");
		sayHello.addClickListener(e -> Notifier.info(getTranslation("Hello {0}", name.getValue())));
		sayHello.addClickShortcut(Key.ENTER);

		add(name, sayHello);
	}

	@Override
	public String getPageTitle() {
		return getTranslation("Hello World");
	}

}
