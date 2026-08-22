// The Maven Archetype plugin does not preserve file permissions when it copies
// archetype-resources (ARCHETYPE-278), so the generated mvnw would arrive without
// its executable bit and `./mvnw` would fail with "permission denied".
import java.nio.file.Files
import java.nio.file.Paths

def projectDirectory = Paths.get(request.outputDirectory, request.artifactId)

["mvnw"].each { name ->
    def script = projectDirectory.resolve(name)
    if (Files.exists(script)) {
        script.toFile().setExecutable(true, false)
    }
}
