import java.nio.file.Files

plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.minotaur)
    java
    `maven-publish`
}

val osName: String = System.getProperty("os.name").lowercase().replace(" ", "")
val lwjglNativeList = arrayOf("macos", "windows", "linux")
val lwjglNativesName = "natives-${lwjglNativeList.find { it in osName }}"

val modVersion: Provider<String> = providers.gradleProperty("mod_version")
val modGroup: Provider<String> = providers.gradleProperty("mod_group")
val modName: Provider<String> = providers.gradleProperty("mod_name")

val javaVersion: Provider<Int> = libs.versions.java.map { it.toInt() }

base.archivesName = modName
group = modGroup.get()
version = modVersion.get()
loom {
    customMinecraftMetadata.set("https://downloads.betterthanadventure.net/bta-client/${libs.versions.btaChannel.get()}/${libs.versions.bta.get()}/manifest.json")
}
repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/") { name = "Fabric" }
    maven("https://maven.thesignalumproject.net/infrastructure") { name = "SignalumMavenInfrastructure" }
    maven("https://maven.thesignalumproject.net/nightly") { name = "signalumMavenNightly" }
    maven("https://maven.thesignalumproject.net/releases") { name = "SignalumMavenReleases" }
    ivy("https://piston-data.mojang.com") {
        patternLayout { artifact("v1/[organisation]/[revision]/[module].jar") }
        metadataSources { artifact() }
    }
}

dependencies {
    minecraft("::${libs.versions.bta.get()}")

    // Required at compilation & runtime
    // included in builds as a runtime dependency
    implementation(libs.loader)

    // Only required at compilation
    // provides documentation, can be removed if that isn't needed
    compileOnly(libs.bundles.btaLwjgl)
    compileOnly(libs.joml)
    compileOnly(libs.joml.primitives)
    compileOnly(libs.slf4jApi)

    compileOnly(libs.jspecify)
    compileOnly(libs.errorprone)

    // Only required for development/launch at runtime, won't be part of any builds
    runtimeClasspath(libs.clientJar)
    localRuntime(libs.modMenu) // Optional, can be removed
    val lwjglVer = libs.versions.lwjgl.get()
    localRuntime(platform("org.lwjgl:lwjgl-bom:${lwjglVer}"))
    localRuntime("org.lwjgl:lwjgl::$lwjglNativesName")
    localRuntime("org.lwjgl:lwjgl-glfw::$lwjglNativesName")
    localRuntime("org.lwjgl:lwjgl-openal::$lwjglNativesName")
    localRuntime("org.lwjgl:lwjgl-opengl::$lwjglNativesName")
    localRuntime("org.lwjgl:lwjgl-stb::$lwjglNativesName")
}

java {
    toolchain {
        languageVersion = javaVersion.map { JavaLanguageVersion.of(it) }
        vendor = JvmVendorSpec.ADOPTIUM
    }
    sourceCompatibility = JavaVersion.toVersion(javaVersion.get())
    targetCompatibility = JavaVersion.toVersion(javaVersion.get())
    withSourcesJar()
}
val licenseFile = run {
    val rootLicense = layout.projectDirectory.file("LICENSE")
    val parentLicense = layout.projectDirectory.file("../LICENSE")
    when {
        rootLicense.asFile.exists() -> {
            logger.lifecycle("Using LICENSE from project root: {}", rootLicense.asFile)
            rootLicense
        }
        parentLicense.asFile.exists() -> {
            logger.lifecycle("Using LICENSE from parent directory: {}", parentLicense.asFile)
            parentLicense
        }
        else -> {
            logger.warn("No LICENSE file found in project or parent directory.")
            null
        }
    }
}
tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.get().toString()
        targetCompatibility = javaVersion.get().toString()
        if (javaVersion.get() > 8) options.release = javaVersion
    }
    withType<UpdateDaemonJvm>().configureEach {
        languageVersion = libs.versions.gradleJava.map { JavaLanguageVersion.of(it.toInt()) }
        vendor = JvmVendorSpec.ADOPTIUM
    }
    withType<JavaExec>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<Javadoc>().configureEach { options.encoding = "UTF-8" }
    withType<Test>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<Jar>().configureEach {
        licenseFile?.let {
            from(it) {
                rename { original -> "${original}_${archiveBaseName.get()}" }
            }
        }
    }
    processResources {
        val resourceMap = mapOf(
            "version" to modVersion.get(),
            "fabricloader" to libs.versions.loader.get(),
            "java" to libs.versions.java.get(),
            "modmenu" to libs.versions.modMenu.get()
        )
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        with(copySpec {
            from("src/main/resources/") {
                include("fabric.mod.json")
                include("*.mixins.json")
                expand(resourceMap)
            }
        })
    }
}
// Removes all outdated manifest.json dependencies
configurations.configureEach {
    exclude(group = "org.lwjgl.lwjgl")
    exclude(group = "net.java.jutils")
    exclude(group = "net.java.jinput")
    exclude(group = "net.sf.jopt-simple")
    exclude(group = "net.minecraft", module = "launchwrapper")
}

publishing {
    repositories {
        maven("https://maven.thesignalumproject.net/releases") {
            name = "signalumMaven"
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = modGroup.get()
            artifactId = modName.get()
            version = modVersion.get()
            from(components["java"])
        }
    }
}

val modrinthToken: Provider<String> = providers.gradleProperty("modrinthToken")

if (modrinthToken.isPresent) {
    modrinth {
        token = modrinthToken
        projectId = "halplibe"
        versionName = modVersion.map { "HalpLibe $it" }
        versionNumber = modVersion
        versionType = "release"
        uploadFile.set(tasks.jar)
        additionalFiles = listOf(tasks.named("sourcesJar"))
        gameVersions.add("b1.7.3")
        loaders.add("bta-babric")
        changelog = Files.readString(rootProject.projectDir.toPath().resolve("CHANGELOG.md"))
    }
}
