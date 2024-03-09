# HalpLibe

Helper library containing functions for common use cases, while ensuring compatibility between mods.

## Prerequisites
- JDK for Java 17 ([Eclipse Temurin](https://adoptium.net/temurin/releases/) recommended)
- IntelliJ IDEA
- Minecraft Development plugin (Optional, but highly recommended)

## Setup instructions
Follow the setup instructions on [the example mod](https://github.com/Turnip-Labs/bta-example-mod) GitHub page.

## How to include HalpLibe in a project
Add this in your `build.gradle`:
```groovy
repositories {
   maven { url = "https://jitpack.io" }
}

dependencies {
    modImplementation "com.github.Turnip-Labs:bta-halplibe:${project.halplibe_version}"
}
```

## Credits
- azurelmao
- pkstDev
- Jim Jim aka FatherCheese
- icanttellyou
- youngsditch
- sunsetsatellite
- useless
