# HalpLibe

Helper library containing functions for common use cases, while ensuring compatibility between mods.

## Prerequisites
- JDK for Java 17 ([Eclipse Temurin](https://adoptium.net/temurin/releases/?version=17) recommended)
- IntelliJ IDEA
- Minecraft Development plugin (Optional, but highly recommended)

## Setup instructions
Follow the setup instructions on [the example mod](https://github.com/Turnip-Labs/bta-example-mod) GitHub page.

## How to include HalpLibe in a project

Update the ``/gradle/libs.versions.toml`` config file:
```toml
[versions]
halplibe = "6.1.4"

[libraries]
halplibe = { group = "turniplabs", name = "halplibe", version.ref = "halplibe" }
```

Update the ``build.gradle.kts`` script:
```kotlin
dependencies {
    implementation(libs.halplibe)
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
