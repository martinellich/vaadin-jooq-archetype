# Maven Archetype to Scaffold Applications<br>based on the Simon Martinelli Stack

https://martinelli.ch/the-simon-martinelli-stack-a-pragmatic-approach-to-full-stack-java-development/

## Usage

```bash
mvn archetype:generate                               \
-DarchetypeGroupId=ch.martinelli.oss                 \
-DarchetypeArtifactId=vaadin-jooq-archetype          \
-DarchetypeVersion=1.1.3                             \
-DgroupId=<my.groupId>                               \
-DartifactId=<my.artifactId>
```

## Release Notes

### 1.1.3
- Dependency updates

### 1.1.2
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