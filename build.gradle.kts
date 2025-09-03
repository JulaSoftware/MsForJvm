import org.jreleaser.model.Active
import java.net.URI

plugins {
    kotlin("jvm") version "1.9.23"
    `java-library`
    `java-library-distribution`
    `maven-publish`
    signing
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

signing {
    sign(publishing.publications["maven"])
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }

        repositories {
            mavenLocal {

            }
            mavenCentral {
                url = URI("https://oss.sonatype.org/service/local/staging/deploy/maven2")

            }
        }
    }
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

    signing {
        active = Active.ALWAYS
        armored = true
    }

    deploy {
        maven {
            nexus2 {
                release {

                }
            }
        }
    }
}