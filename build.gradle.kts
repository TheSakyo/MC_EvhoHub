plugins {
    java
    id("io.papermc.paperweight.userdev") version "1.3.8"
}

group = "fr.TheSakyo.EvhoHub"
version = "latest"

java { toolchain.languageVersion.set(JavaLanguageVersion.of(17)) }

tasks.withType<JavaCompile> { options.encoding = "UTF-8" }

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation("org.jetbrains:annotations:23.0.0")

    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.17.1-R0.1-SNAPSHOT")
    implementation(fileTree("src/libs") { include("*.jar") })
}

tasks { assemble { dependsOn(reobfJar) } }