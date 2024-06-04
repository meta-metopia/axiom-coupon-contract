plugins {
    id("java")
    kotlin("jvm") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // required web3j dependency
    implementation ("org.web3j:core:4.12.0")
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    implementation("org.json:json:20240303")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}