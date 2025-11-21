package ${package}.person.ui;

import ${package}.core.domain.Role;
import ${package}.core.ui.KaribuTest;
import ${package}.db.tables.records.PersonRecord;
import com.github.mvysny.kaributesting.v10.GridKt;
import com.github.mvysny.kaributesting.v10.pro.ConfirmDialogKt;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.github.mvysny.kaributesting.v10.LocatorJ._click;
import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static org.assertj.core.api.Assertions.assertThat;

class PersonViewTest extends KaribuTest {

	@BeforeEach
	void navigate() {
		login("admin", List.of(Role.ADMIN));
		UI.getCurrent().getPage().reload();
		UI.getCurrent().navigate(PersonView.class);
	}

	@Test
	void check_grid_size() {
		var grid = _get(Grid.class);
		assertThat(GridKt._size(grid)).isEqualTo(100);
	}

	@Test
	void navigate_to_person() {
		UI.getCurrent().navigate(PersonView.class, 1L);

		var grid = _get(Grid.class);
		assertThat(GridKt._size(grid)).isEqualTo(100);

		@SuppressWarnings("unchecked")
		Set<PersonRecord> selectedItems = grid.getSelectedItems();
		assertThat(selectedItems).hasSize(1).first().extracting(PersonRecord::getFirstName).isEqualTo("Eula");

		var firstNameTextField = _get(TextField.class, s -> s.withLabel("First Name"));
		assertThat(firstNameTextField.getValue()).isEqualTo("Eula");
	}

	@Test
	void delete_person() {
		var grid = _get(Grid.class);
		assertThat(GridKt._size(grid)).isEqualTo(100);

		@SuppressWarnings("unchecked")
		Component component = GridKt._getCellComponent(grid, 0, "actions");
		assertThat(component).isInstanceOf(SvgIcon.class);
		_click((SvgIcon) component);

		ConfirmDialog confirmDialog = _get(ConfirmDialog.class);
		ConfirmDialogKt._fireConfirm(confirmDialog);

		assertThat(GridKt._size(grid)).isEqualTo(99);
	}

}