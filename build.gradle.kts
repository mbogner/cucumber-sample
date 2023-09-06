/*
 * Copyright (c) 2023.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://plugins.gradle.org/plugin/org.springframework.boot
    id("org.springframework.boot") version "3.1.3"
    // https://plugins.gradle.org/plugin/io.spring.dependency-management
    id("io.spring.dependency-management") version "1.1.3"
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    kotlin("jvm") version "1.9.10"
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.spring
    kotlin("plugin.spring") version "1.9.10"
}

group = "dev.mbo"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // ------ TEST ------
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // cucumber
    val cucumberVersion = "7.13.0"
    // https://mvnrepository.com/artifact/io.cucumber/cucumber-java
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    // https://mvnrepository.com/artifact/io.cucumber/cucumber-junit-platform-engine
    testImplementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")
    // https://mvnrepository.com/artifact/io.cucumber/cucumber-spring
    testImplementation("io.cucumber:cucumber-spring:$cucumberVersion")

    // junit platform suite used to integrate cucumber into test run
    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-suite
    testImplementation("org.junit.platform:junit-platform-suite:1.10.0")

}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    withType<Test> {
        useJUnitPlatform()
        systemProperty(
            "cucumber.junit-platform.naming-strategy",
            "long"
        )
    }

    withType<Wrapper> {
        // https://gradle.org/releases/
        gradleVersion = "8.3"
        distributionType = Wrapper.DistributionType.BIN
    }
}