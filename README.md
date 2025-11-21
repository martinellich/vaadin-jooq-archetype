# Maven Archetype to scaffold Applications with the Simon Martinelli Stack

https://martinelli.ch/the-simon-martinelli-stack-a-pragmatic-approach-to-full-stack-java-development/

## Usage

mvn archetype:generate                                \
-DarchetypeGroupId=ch.martinelli.oss                  \
-DarchetypeArtifactId=vaadin-jooq-archetype           \
-DarchetypeVersion=0.0.1-SNAPSHOT                     \
-DgroupId=<my.groupId>                                \
-DartifactId=<my.artifactId>                          \