plugins {
    id("java")
}

group = "org.cup"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Define specific asset directories as resources
sourceSets {
    main {
        resources {
            srcDirs("src/main/java/org/cup/assets/sprites", "src/main/java/org/cup/assets/UI")
        }
    }
}

// Configure the Jar task to include only specified assets directories
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.cup.Main"
    }
    from("src/main/java/org/cup/assets/sprites") {
        into("org/cup/assets/sprites")
    }
    from("src/main/java/org/cup/assets/UI") {
        into("org/cup/assets/UI")
    }
}
