plugins {
    id("java")
    id("edu.sc.seis.launch4j") version "3.0.3"
}

group = "org.cup"
version = "1.0"

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

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.cup.Main"
    }
}

launch4j {
    mainClassName = "org.cup.Main"
    icon = "${projectDir}/src/main/java/org/cup/logo.ico"
    outputDir = "executable"
    // jar = "${project.buildDir}/libs/${project.name}-${project.version}.jar"
    
    // Optional additional configuration
    jvmOptions = listOf("-Xms256m", "-Xmx1024m")
    // bundledJvm64Bit = true
    bundledJrePath = "jre"
    windowTitle = "Economy35"
}