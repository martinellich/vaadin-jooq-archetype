# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Maven archetype for scaffolding full-stack Java applications using the "Simon Martinelli Stack": Vaadin (UI framework), jOOQ (database access), Spring Boot, and PostgreSQL. The archetype generates a complete application with authentication, security, and a modular architecture.

## Building and Running the Archetype

### Install the Archetype Locally
```bash
./mvnw clean install
```

### Generate a New Project from the Archetype
```bash
mvn archetype:generate \
  -DarchetypeGroupId=ch.martinelli.oss \
  -DarchetypeArtifactId=vaadin-jooq-archetype \
  -DarchetypeVersion=0.0.1-SNAPSHOT \
  -DgroupId=<my.groupId> \
  -DartifactId=<my.artifactId>
```

## Generated Project Structure

### Layered Architecture (Enforced by ArchUnit)
The generated applications follow a strict layered architecture:

1. **UI Layer** (`..ui..` packages): Vaadin views and components
2. **Domain Layer** (`..domain..` packages): Data access objects (DAOs), domain models, business logic
3. **Security Layer** (`..security..` packages): Spring Security configuration, authentication

**Architectural Rules:**
- UI layer may NOT be accessed by any other layer
- Domain layer may ONLY be accessed by UI and Security layers
- Only UI and Security layers may use Vaadin classes

### Module Structure (Enforced by ArchUnit)
Applications are organized into modules that are isolated from each other:

- **core**: Shared infrastructure, security, configuration, base UI components
  - May NOT access other modules (greeting, person, etc.)
- **greeting**, **person**, etc.: Feature modules
  - May ONLY depend on core module
  - May NOT depend on each other

When adding new features, create a new module package (e.g., `product`, `order`) following the same pattern.

## Technology Stack

### Core Technologies
- **Spring Boot 3.5.8**: Application framework (Java 25)
- **Vaadin 24.9.5**: UI framework (server-side Java UI)
- **jOOQ 3.20.8**: Type-safe database access
- **PostgreSQL**: Database (via Testcontainers for development)
- **Spring Security**: Authentication and authorization with JWT tokens
- **Flyway**: Database migrations

### Key Libraries
- **jooq-spring** (0.4.0): jOOQ integration with Spring transactions
- **vaadin-jooq** (2.0.4): Vaadin Grid data providers for jOOQ
- **Line Awesome**: Icon library

## Database and jOOQ Code Generation

### Generate jOOQ Classes
jOOQ classes are generated from the database schema using Flyway migrations and Testcontainers:

```bash
./mvnw generate-sources
```

This plugin:
1. Starts a PostgreSQL container using Testcontainers
2. Runs Flyway migrations from `src/main/resources/db/migration`
3. Generates jOOQ classes into `${package}.db` package
4. Uses custom generator `ch.martinelli.oss.jooq.EqualsAndHashCodeJavaGenerator` for proper equals/hashCode

**Important Configuration:**
- Database schema migrations: `src/main/resources/db/migration/V*.sql`
- Test data migrations: `src/test/resources/db/migration/afterMigrate.sql`
- jOOQ records have optimistic locking enabled for `version` fields (configured in `VjJooqConfiguration`)

### Creating Database Migrations
1. Create a new file in `src/main/resources/db/migration/` with pattern `V{number}__{description}.sql`
2. Run `./mvnw generate-sources` to regenerate jOOQ classes
3. Update corresponding DAO classes in `domain` packages

## Development Workflow

### Run the Application
```bash
./mvnw spring-boot:run
```

Application runs on port 8080 with remote debugging on port 5679.

### Run Tests
```bash
# Unit tests only
./mvnw test

# Unit tests + integration tests
./mvnw verify

# With coverage report
./mvnw verify -Pcoverage
```

### Testing Strategy
Generated applications include three types of tests:

1. **Unit Tests** (`*Test.java`): Karibu Testing for Vaadin UI components
   - Extend `KaribuTest` base class
   - Provides `login(username, roles)` and `logout()` helpers
   - Mock Spring context with `TestConfiguration`

2. **Integration Tests** (`*IT.java`): Playwright for end-to-end browser testing
   - Extend `PlaywrightIT` base class
   - Full application context with random port
   - Headless browser testing

3. **Architecture Tests** (`ArchitectureTest`): ArchUnit for enforcing architectural rules
   - Validates layer dependencies
   - Validates module isolation
   - Ensures Vaadin is only used in UI/Security layers

### Code Quality Tools

**Error Prone + NullAway**: Enabled by default during compilation
- NullAway enforces null safety with `@NullMarked` annotations
- All packages use `package-info.java` with `@NullMarked`
- Generated code in `target/generated-sources` is excluded

**Spring Java Format**: Code formatting validation
```bash
# Check formatting
./mvnw spring-javaformat:validate

# Apply formatting
./mvnw spring-javaformat:apply
```

**JaCoCo**: Code coverage reporting (use `-Pcoverage` profile)

**SonarQube**: Static analysis support
```bash
./mvnw sonar:sonar
```

## Security Configuration

Generated applications use:
- **Spring Security** with JWT stateless authentication (Vaadin Stateless Security)
- **BCrypt** password encoding
- JWT secret configured via `jwt.auth.secret` property (Base64-encoded)
- Login view at `/login` route
- Health endpoint publicly accessible
- Static resources (`/images/*.png`, `/line-awesome/*`) publicly accessible

## Production Build

```bash
./mvnw clean package -Pproduction
```

The production profile:
- Builds optimized Vaadin frontend bundle
- Excludes development dependencies
- Creates executable JAR

## Important Implementation Patterns

### Creating a New Feature Module
1. Create package structure: `${package}.{module}.ui` and `${package}.{module}.domain`
2. Add database migration in `src/main/resources/db/migration`
3. Regenerate jOOQ: `./mvnw generate-sources`
4. Create DAO in `domain` package (use jOOQ DSLContext)
5. Create View in `ui` package (extend `com.vaadin.flow.component.orderedlayout.VerticalLayout` or similar)
6. Use `@Route` annotation for navigation
7. Use `@PermitAll` or `@RolesAllowed` for security
8. Update `ArchitectureTest` with new module name if needed

### Working with jOOQ DAOs
- Inject `DSLContext` for database operations
- Use `@Transactional` for write operations
- Records with `version` field have automatic optimistic locking
- Use `vaadin-jooq` data providers for Grid components

### Internationalization
- Translation files: `src/main/resources/vaadin-i18n/translations.properties`
- German translations: `translations_de.properties`
- Use `TranslationProvider` for accessing translations