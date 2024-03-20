plugins {
    kotlin("jvm") version "1.9.23"
    id("java-library")
    id("java-library-distribution")
}

group = "de.julasoftware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }

    modularity.inferModulePath = false
    withSourcesJar()
    withJavadocJar()
}