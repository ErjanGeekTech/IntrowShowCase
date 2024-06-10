import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

// Compose Compiler
internal fun VersionCatalog.verComposeCompiler() = findVersionOrThrow("compose-compiler")

private fun VersionCatalog.findPluginOrThrow(name: String) = findPlugin(name).orElseThrow {
    NoSuchElementException("Plugin $name not found in version catalog")
}.get().pluginId

private fun VersionCatalog.findLibraryOrThrow(name: String) = findLibrary(name).orElseThrow {
    NoSuchElementException("Library $name not found in version catalog")
}.get()

private fun VersionCatalog.findVersionOrThrow(name: String) = findVersion(name).orElseThrow {
    NoSuchElementException("Version $name not found in version catalog")
}.requiredVersion