import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.0-M2"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.graalvm.buildtools.native") version "0.9.26"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
}

group = "com.minone"
version = "0.0.1"
description = "bogus"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_20
}

graalvmNative {
    binaries {
        named("main") {
            buildArgs.add("--enable-preview")
            buildArgs.add("-J-Xmx7g")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        // Null-safety for Spring API in Kotlin
        freeCompilerArgs = listOf("-Xjsr305=strict")
        // Highest so far Java version supported by Kotlin
        jvmTarget = "20"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.gatling.highcharts:gatling-charts-highcharts:3.9.0")
}
