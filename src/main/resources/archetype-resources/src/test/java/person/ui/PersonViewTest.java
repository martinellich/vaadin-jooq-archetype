package ${package}.person.ui;

import ${package}.core.domain.Role;
import ${package}.core.ui.AbstractBrowserlessTest;
import ${package}.db.tables.records.PersonRecord;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@WithMockUser(username = "admin", roles = { Role.USER, Role.ADMIN })
class PersonViewTest extends AbstractBrowserlessTest {

	@Test
	void check_grid_size() {
		navigate(PersonView.class);

		Grid<PersonRecord> grid = $(Grid.class).single();
		assertThat(test(grid).size()).isEqualTo(100);
	}

	@Test
	void navigate_to_person() {
		navigate(PersonView.class, 1L);

		Grid<PersonRecord> grid = $(Grid.class).single();
		assertThat(test(grid).size()).isEqualTo(100);

		Set<PersonRecord> selectedItems = grid.getSelectedItems();
		assertThat(selectedItems).hasSize(1).first().extracting(PersonRecord::getFirstName).isEqualTo("Eula");

		TextField firstNameField = $(TextField.class).withCaption("First Name").single();
		assertThat(firstNameField.getValue()).isEqualTo("Eula");
	}

	@Test
	void delete_person() {
		navigate(PersonView.class);

		Grid<PersonRecord> grid = $(Grid.class).single();
		assertThat(test(grid).size()).isEqualTo(100);

		Component component = test(grid).getCellComponent(0, "actions");
		assertThat(component).isInstanceOf(SvgIcon.class);
		test((SvgIcon) component).click();

		ConfirmDialog confirmDialog = $(ConfirmDialog.class).single();
		test(confirmDialog).confirm();

		assertThat(test(grid).size()).isEqualTo(99);
	}

}
