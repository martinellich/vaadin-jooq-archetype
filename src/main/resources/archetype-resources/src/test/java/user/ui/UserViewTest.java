package ${package}.user.ui;

import ${package}.core.domain.Role;
import ${package}.core.domain.UserWithRoles;
import ${package}.core.ui.AbstractBrowserlessTest;
import ${package}.core.ui.UserView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@WithMockUser(username = "admin", roles = Role.ADMIN)
class UserViewTest extends AbstractBrowserlessTest {

	@Test
	void check_grid_size() {
		navigate(UserView.class);

		Grid<UserWithRoles> grid = $(Grid.class).single();
		assertThat(test(grid).size()).isEqualTo(2);
	}

	@Test
	void navigate_to_user() {
		navigate(UserView.class, "admin");

		Grid<UserWithRoles> grid = $(Grid.class).single();
		assertThat(test(grid).size()).isEqualTo(2);

		Set<UserWithRoles> selectedItems = grid.getSelectedItems();
		assertThat(selectedItems).hasSize(1)
			.first()
			.extracting(userWithRoles -> userWithRoles.getUser().getFirstName())
			.isEqualTo("Emma");

		TextField firstNameTextField = $(TextField.class).withCaption("First Name").single();
		assertThat(firstNameTextField.getValue()).isEqualTo("Emma");
	}

	@Test
	void delete_person() {
		navigate(UserView.class);

		Grid<UserWithRoles> grid = $(Grid.class).single();
		assertThat(test(grid).size()).isEqualTo(2);

		Component component = test(grid).getCellComponent(0, "actions");
		assertThat(component).isInstanceOf(SvgIcon.class);
		test((SvgIcon) component).click();

		ConfirmDialog confirmDialog = $(ConfirmDialog.class).single();
		test(confirmDialog).confirm();

		assertThat(test(grid).size()).isEqualTo(1);
	}

	@Test
	void save_new_user() {
		navigate(UserView.class);

		Grid<UserWithRoles> grid = $(Grid.class).single();
		int initialSize = test(grid).size();

		SvgIcon addIcon = (SvgIcon) grid.getColumnByKey("actions").getHeaderComponent();
		test(addIcon).click();

		TextField usernameField = $(TextField.class).withCaption("Username").single();
		TextField firstNameField = $(TextField.class).withCaption("First Name").single();
		TextField lastNameField = $(TextField.class).withCaption("Last Name").single();
		PasswordField passwordField = $(PasswordField.class).withCaption("Password").single();
		MultiSelectComboBox<String> roleMultiSelect = $(MultiSelectComboBox.class).withCaption("Roles").single();

		test(usernameField).setValue("testuser");
		test(firstNameField).setValue("Test");
		test(lastNameField).setValue("User");
		test(passwordField).setValue("password123");
		roleMultiSelect.setValue(Set.of(Role.USER));

		Button saveButton = $(Button.class).withText("Save").single();
		test(saveButton).click();

		assertThat(test(grid).size()).isEqualTo(initialSize + 1);
	}

	@Test
	void save_existing_user() {
		navigate(UserView.class, "user");

		TextField firstNameField = $(TextField.class).withCaption("First Name").single();
		PasswordField passwordField = $(PasswordField.class).withCaption("Password").single();
		test(passwordField).setValue("password");

		String updatedFirstName = "UpdatedJohn";
		test(firstNameField).setValue(updatedFirstName);

		Button saveButton = $(Button.class).withText("Save").single();
		test(saveButton).click();

		navigate(UserView.class, "user");
		TextField updatedFirstNameField = $(TextField.class).withCaption("First Name").single();
		assertThat(updatedFirstNameField.getValue()).isEqualTo(updatedFirstName);
	}

	@Test
	void save_validation_fails_for_empty_required_fields() {
		navigate(UserView.class);

		Grid<UserWithRoles> grid = $(Grid.class).single();
		SvgIcon addIcon = (SvgIcon) grid.getColumnByKey("actions").getHeaderComponent();
		test(addIcon).click();

		Button saveButton = $(Button.class).withText("Save").single();
		test(saveButton).click();

		TextField usernameField = $(TextField.class).withCaption("Username").single();
		TextField firstNameField = $(TextField.class).withCaption("First Name").single();
		TextField lastNameField = $(TextField.class).withCaption("Last Name").single();
		PasswordField passwordField = $(PasswordField.class).withCaption("Password").single();

		assertThat(usernameField.isInvalid()).isTrue();
		assertThat(firstNameField.isInvalid()).isTrue();
		assertThat(lastNameField.isInvalid()).isTrue();
		assertThat(passwordField.isInvalid()).isTrue();
	}

	@Test
	void cancel_button_clears_form_and_refreshes_grid() {
		navigate(UserView.class);

		Grid<UserWithRoles> grid = $(Grid.class).single();
		SvgIcon addIcon = (SvgIcon) grid.getColumnByKey("actions").getHeaderComponent();
		test(addIcon).click();

		TextField usernameField = $(TextField.class).withCaption("Username").single();
		TextField firstNameField = $(TextField.class).withCaption("First Name").single();

		test(usernameField).setValue("testuser");
		test(firstNameField).setValue("Test");

		Button cancelButton = $(Button.class).withText("Cancel").single();
		test(cancelButton).click();

		assertThat(usernameField.getValue()).isEmpty();
		assertThat(firstNameField.getValue()).isEmpty();
		assertThat(usernameField.isReadOnly()).isFalse();
	}

}
