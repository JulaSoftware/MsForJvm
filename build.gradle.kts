import org.jreleaser.model.Active

plugins {
    kotlin("jvm") version "1.9.23"
    `java-library`
    `java-library-distribution`
    id("org.jreleaser") version "1.11.0"
}

group = "io.github.julasoftware"
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
                "Implementation-Title" to project.name, "Implementation-Version" to project.version
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

jreleaser {
    project {
        authors = listOf("Sebastian Krümling")
        license = "MIT"
        inceptionYear = "2024"
    }

    release {
        github {
            repoOwner = "JulaSoftware"
        }
    }

    distributions {
        signing {
            active = Active.ALWAYS
            armored = true
        }

    }
}