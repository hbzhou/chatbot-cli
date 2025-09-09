import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.expressions.builder.buildArgumentList

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.10.4"
}

group = "com.itsz"
version = "0.0.1-SNAPSHOT"
description = "Azure Open AI"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.1"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.ai:spring-ai-starter-model-azure-openai")
    implementation("info.picocli:picocli-spring-boot-starter:4.7.7")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("chatbot-cli")
            buildArgs.add("--initialize-at-build-time=org.slf4j.helpers")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
