package ${package}.greeting.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "hello")
@RouteAlias(value = "")
public class HelloWorldView extends VerticalLayout implements HasDynamicTitle {

	public HelloWorldView() {
	}

	@Override
	public String getPageTitle() {
		return getTranslation("view.hello.world.title");
	}

}
