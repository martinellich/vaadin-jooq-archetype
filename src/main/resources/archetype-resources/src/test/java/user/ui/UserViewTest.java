package ${package}.user.ui;

import ${package}.core.domain.Role;
import ${package}.core.domain.UserWithRoles;
import ${package}.core.ui.KaribuTest;
import ${package}.core.ui.UserView;
import com.github.mvysny.kaributesting.v10.GridKt;
import com.github.mvysny.kaributesting.v10.pro.ConfirmDialogKt;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.github.mvysny.kaributesting.v10.LocatorJ.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserViewTest extends KaribuTest {

	@BeforeEach
	void navigate() {
		login("admin", List.of(Role.ADMIN));
		UI.getCurrent().getPage().reload();
		UI.getCurrent().navigate(UserView.class);
	}

	@Test
	void check_grid_size() {
		var grid = _get(Grid.class);
		assertThat(GridKt._size(grid)).isEqualTo(2);
	}

	@Test
	void navigate_to_user() {
		UI.getCurrent().navigate(UserView.class, "admin");

		var grid = _get(Grid.class);
		assertThat(GridKt._size(grid)).isEqualTo(2);

		@SuppressWarnings("unchecked")
		Set<UserWithRoles> selectedItems = grid.getSelectedItems();
		assertThat(selectedItems).hasSize(1)
			.first()
			.extracting(userWithRoles -> userWithRoles.getUser().getFirstName())
			.isEqualTo("Emma");

		var firstNameTextField = _get(TextField.class, s -> s.withLabel("First Name"));
		assertThat(firstNameTextField.getValue()).isEqualTo("Emma");
	}

	@Test
	void delete_person() {
		var grid = _get(Grid.class);
		assertThat(GridKt._size(grid)).isEqualTo(2);

		@SuppressWarnings("unchecked")
		var component = GridKt._getCellComponent(grid, 0, "actions");
		assertThat(component).isInstanceOf(SvgIcon.class);
		_click((SvgIcon) component);

		var confirmDialog = _get(ConfirmDialog.class);
		ConfirmDialogKt._fireConfirm(confirmDialog);

		assertThat(GridKt._size(grid)).isEqualTo(1);
	}

	@Test
	void save_new_user() {
		var grid = _get(Grid.class);
		var initialSize = GridKt._size(grid);

		var addIcon = _get(SvgIcon.class);
		_click(addIcon);

		var usernameField = _get(TextField.class, spec -> spec.withLabel("Username"));
		var firstNameField = _get(TextField.class, spec -> spec.withLabel("First Name"));
		var lastNameField = _get(TextField.class, spec -> spec.withLabel("Last Name"));
		var passwordField = _get(PasswordField.class, spec -> spec.withLabel("Password"));
		@SuppressWarnings("unchecked")
		var roleMultiSelect = _get(MultiSelectComboBox.class, spec -> spec.withLabel("Roles"));

		usernameField.setValue("testuser");
		firstNameField.setValue("Test");
		lastNameField.setValue("User");
		passwordField.setValue("password123");
		roleMultiSelect.setValue(Set.of(Role.USER));

		var saveButton = _get(Button.class, spec -> spec.withText("Save"));
		_click(saveButton);

		assertThat(GridKt._size(grid)).isEqualTo(initialSize + 1);
	}

	@Test
	void save_existing_user() {
		UI.getCurrent().navigate(UserView.class, "user");

		var firstNameField = _get(TextField.class, spec -> spec.withLabel("First Name"));
		var passwordField = _get(PasswordField.class, spec -> spec.withLabel("Password"));
		_setValue(passwordField, "password");

		var updatedFirstName = "UpdatedJohn";

		firstNameField.setValue(updatedFirstName);

		var saveButton = _get(Button.class, spec -> spec.withText("Save"));
		_click(saveButton);

		UI.getCurrent().navigate(UserView.class, "user");
		var updatedFirstNameField = _get(TextField.class, spec -> spec.withLabel("First Name"));
		assertThat(updatedFirstNameField.getValue()).isEqualTo(updatedFirstName);
	}

	@Test
	void save_validation_fails_for_empty_required_fields() {
		var addIcon = _get(SvgIcon.class);
		_click(addIcon);

		var saveButton = _get(Button.class, spec -> spec.withText("Save"));
		_click(saveButton);

		var usernameField = _get(TextField.class, spec -> spec.withLabel("Username"));
		var firstNameField = _get(TextField.class, spec -> spec.withLabel("First Name"));
		var lastNameField = _get(TextField.class, spec -> spec.withLabel("Last Name"));
		var passwordField = _get(PasswordField.class, spec -> spec.withLabel("Password"));

		assertThat(usernameField.isInvalid()).isTrue();
		assertThat(firstNameField.isInvalid()).isTrue();
		assertThat(lastNameField.isInvalid()).isTrue();
		assertThat(passwordField.isInvalid()).isTrue();
	}

	@Test
	void cancel_button_clears_form_and_refreshes_grid() {
		var addIcon = _get(SvgIcon.class);
		_click(addIcon);

		var usernameField = _get(TextField.class, spec -> spec.withLabel("Username"));
		var firstNameField = _get(TextField.class, spec -> spec.withLabel("First Name"));

		usernameField.setValue("testuser");
		firstNameField.setValue("Test");

		var cancelButton = _get(Button.class, spec -> spec.withText("Cancel"));
		_click(cancelButton);

		assertThat(usernameField.getValue()).isEmpty();
		assertThat(firstNameField.getValue()).isEmpty();
		assertThat(usernameField.isReadOnly()).isFalse();
	}

}