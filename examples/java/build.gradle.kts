plugins {
    id("java")
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
}

tasks.test {
    useJUnitPlatform()
}