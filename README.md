# Maven Archetype to Scaffold Applications<br>based on the Simon Martinelli Stack

https://martinelli.ch/the-simon-martinelli-stack-a-pragmatic-approach-to-full-stack-java-development/

## Usage

```bash
mvn archetype:generate                               \
-DarchetypeGroupId=ch.martinelli.oss                 \
-DarchetypeArtifactId=vaadin-jooq-archetype          \
-DarchetypeVersion=1.4.0
```

## Release Notes

### 1.4.0
- Synchronised with vaadin-jooq-template: Spring Boot 4.1.0, Vaadin 25.2.6, jOOQ 3.21.7, Flyway plugin 13.2.0,
  Playwright 1.62.0, ArchUnit 1.5.0, JaCoCo 0.8.15, JSpecify 1.0.1, ErrorProne 2.50.0, NullAway 0.13.8
- Replaced `browserless-test-junit6` with `browserless-test-spring` and switched the tests from `$(...)` to `find(...)`
- Maven Wrapper 3.3.4
- `mvnw` is now executable in generated projects (post-generate script works around ARCHETYPE-278)

### 1.3.0
- Exchanged Vaadin Playwright helper Mopo with Dramafinder

### 1.2.0
- Dependency updates
- Browserless tests

### 1.1.7
- Formatting

### 1.1.6
- JaCoCo exclusions in path format
- Dependency updates

### 1.1.2, 1.1.3, 1.1.4, 1.1.5
- Dependency updates

### 1.1.1
- Fixed failing test

### 1.1.0
- Dependency updates

### 1.0.2
- Added missing Vaadin plugin

### 1.0.0
- Migration to Vaadin 25 and Spring Boot 4

### 0.0.3
- Fixed warnings because of the wrong configuration in archetype-metadata.xml

### 0.0.2
- Removed Testcontainers jOOQ Plugin because of compatibility issues with the newest Docker version

### 0.0.1
- Initial release
