# HalpLibe

A helper library to ease babric mod development for BTA.

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
   ivy {
      url = "https://github.com/Turnip-Labs"
      patternLayout {
         artifact "[organisation]/releases/download/v[revision]/[module]-[revision].jar"
         m2compatible = true
      }
      metadataSources { artifact() }
   }
}

dependencies {
   
   modImplementation "bta-halplibe:halplibe:${project.halplibe_version}"
   
}
```

## Credits
- azurelmao
- pkstDev
- Jim Jim aka FatherCheese
- ICantTellYou
- youngsditch
