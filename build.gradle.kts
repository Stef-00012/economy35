plugins {
    id 'java'
}

group = 'org.cup'
version = '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}

test {
    useJUnitPlatform()
}

tasks.withType(Jar) {
    manifest {
        attributes(
            'Main-Class': 'org.cup.Main'
        )
    }
    
    // This will include assets in the JAR
    from('src/main/java') {
        include 'org/cup/assets/sprites/**'
        into 'assets/sprites'
    }

    // This will include fonts in the JAR
    from('src/main/java') {
        include 'org/cup/assets/UI/fonts/**'
        into 'assets/fonts'
    }
}
