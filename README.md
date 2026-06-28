# HalpLibe

Helper library containing functions for common use cases, while ensuring compatibility between mods.

## Prerequisites
- JDK for Java 17 ([Eclipse Temurin](https://adoptium.net/temurin/releases/?version=17) recommended)
- IntelliJ IDEA
- Minecraft Development plugin (Optional, but highly recommended)

## Setup instructions
Follow the setup instructions on [the example mod](https://github.com/Turnip-Labs/bta-example-mod) GitHub page.

## Using HalpLibe as a dependency

If you're using the [example mod template](https://github.com/Turnip-Labs/bta-example-mod) HalpLibe should already
be set up as a dependency so you can safely skip this part.

> [!NOTE]
> This guide does not include registering a dependency with Fabric Loader, you can read more about
> that [here](https://docs.fabricmc.net/develop/loader/fabric-mod-json).

### Repository setup

```kotlin
// [build.gradle.kts]

respositories {
    // Some BTA modding projects (like HalpLibe) are hosted on the Signalum Maven
    maven("https://maven.thesignalumproject.net/releases") { name = "SignalumMavenReleases" }
    maven("https://maven.thesignalumproject.net/nightly") { name = "signalumMavenNightly" }
    maven("https://maven.thesignalumproject.net/infrastructure") { name = "SignalumMavenInfrastructure" }
}
```

### With version catalogs (recommended)

If you are unfamiliar with version catalogs, you can read about them in detail
[here](https://docs.gradle.org/current/userguide/version_catalogs.html).

```toml
# [gradle/libs.version.toml]

[versions]
halplibe = "6.0.2"

[libraries]
halplibe = { group = "turniplabs", name = "halplibe", version.ref = "halplibe" }
```

```kotlin
// [build.gradle.kts]

dependencies {
    implementation(libs.halplibe)
}
```

### Without version catalogs

```kotlin
// [build.gradle.kts]

dependencies {
    implementation("turniplabs:halplibe:6.0.2")
}
```

## Credits
- azurelmao
- Flamarine
- Jim Jim aka FatherCheese
- icanttellyou
- youngsditch
- sunsetsatellite
- useless
- LukeisStuff
- big sir
