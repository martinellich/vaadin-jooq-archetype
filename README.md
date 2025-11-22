# Maven Archetype to Scaffold Applications<br>based on the Simon Martinelli Stack

https://martinelli.ch/the-simon-martinelli-stack-a-pragmatic-approach-to-full-stack-java-development/

## Usage

```bash
mvn archetype:generate                                \
-DarchetypeGroupId=ch.martinelli.oss                  \
-DarchetypeArtifactId=vaadin-jooq-archetype           \
-DarchetypeVersion=0.0.1                              \
-DgroupId=<my.groupId>                                \
-DartifactId=<my.artifactId>
```