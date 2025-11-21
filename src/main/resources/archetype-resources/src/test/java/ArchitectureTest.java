package ${package};

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureTest {

	// Packages

	public static final String PACKAGE_ROOT = "${package}";

	public static final String UI_PACKAGE = "..ui..";

	public static final String SECURITY_PACKAGE = "..security..";

	public static final String DOMAIN_PACKAGE = "..domain..";

	// Layers

	private static final String UI_LAYER = "UI";

	private static final String SECURITY_LAYER = "Security";

	private static final String DOMAIN_LAYER = "Domain";

	// Modules

	private static final String CORE_MODULE = "..core..";

	private static final String GREETING_MODULE = "..greeting..";

	private static final String PERSON_MODULE = "..person..";

	private final JavaClasses classes = new ClassFileImporter().importPackages(PACKAGE_ROOT);

	@Test
	void layered_architecture_check() {
		layeredArchitecture().consideringAllDependencies()

			.layer(UI_LAYER)
			.definedBy(UI_PACKAGE)
			.layer(SECURITY_LAYER)
			.definedBy(SECURITY_PACKAGE)
			.layer(DOMAIN_LAYER)
			.definedBy(DOMAIN_PACKAGE)

			.whereLayer(UI_LAYER)
			.mayNotBeAccessedByAnyLayer()
			.whereLayer(DOMAIN_LAYER)
			.mayOnlyBeAccessedByLayers(UI_LAYER, SECURITY_LAYER)

			.check(classes);
	}

	@Test
	void module_check_core_may_not_be_accessed_by_any_other_module() {
		noClasses().that()
			.resideInAPackage(CORE_MODULE)
			.should()
			.accessClassesThat()
			.resideInAnyPackage(GREETING_MODULE, PERSON_MODULE)
			.check(classes);
	}

	@Test
	void module_check_greeting_may_only_use_core() {
		noClasses().that()
			.resideInAPackage(GREETING_MODULE)
			.should()
			.dependOnClassesThat()
			.resideInAnyPackage(PERSON_MODULE)
			.check(classes);
	}

	@Test
	void module_check_operson_may_only_use_core() {
		noClasses().that()
			.resideInAPackage(PERSON_MODULE)
			.should()
			.dependOnClassesThat()
			.resideInAnyPackage(GREETING_MODULE)
			.check(classes);
	}

	@Test
	void verify_that_only_the_ui_layer_and_security_config_is_using_vaadin() {
		noClasses().that()
			.resideOutsideOfPackages(UI_PACKAGE, SECURITY_PACKAGE)
			.should()
			.accessClassesThat()
			.resideInAnyPackage("com.vaadin..")
			.check(classes);
	}

}
