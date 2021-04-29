import org.jetbrains.kotlin.gradle.tasks.*

val snippetsDir: String by extra("build/generated-snippets")
val springCloudVersion: String by extra { "Hoxton.SR8" }
val querydslVersion: String by extra { "4.3.1" }
val coroutineVersion: String by extra { "1.3.9" }
val springRestdocsVersion: String by extra { "2.0.4.RELEASE" }
val flywayVersion: String by extra("6.3.3")

buildscript {
    repositories {
        mavenCentral()
        maven ("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath ("com.epages:restdocs-api-spec-gradle-plugin:0.11.3") //1.2
    }
}

plugins {
    val kotlinVersion = "1.4.30"
    val springBootVersion = "2.3.3.RELEASE"
    val springDependencyManagementVersion = "1.0.11.RELEASE"

    // PLUGIN: Language
    java

    // PLUGIN: Kotlin
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("plugin.allopen") version kotlinVersion apply false

    // PLUGIN: Spring Boot
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion

    id("com.adarshr.test-logger") version "2.0.0"

    // PLUGIN: flyway
    id("org.flywaydb.flyway") version "6.5.5"
}

flyway {
    table = "member_flyway_schema_history"
    locations = arrayOf("filesystem:${rootProject.projectDir}/flyway/migration")
    url = property("database.url") as String
    user = property("database.username") as String
    password = property("database.password") as String
}

tasks {
    flywayClean {
        enabled = false
        doLast { println("flywayClean is not available.") }
    }
    flywayUndo {
        enabled = false
        doLast { println("flywayUndo is not available.") }
    }
}

allprojects {
    repositories { mavenCentral() }
}

subprojects {
    group = "com.sp"
    version = "1.0"

    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.jvm")


    tasks {
        val action: KotlinCompile.() -> Unit = {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xuse-experimental=kotlin.Experimental")
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }

        compileKotlin(action)
        compileTestKotlin(action)

        configure<JavaPluginConvention> {
            sourceCompatibility = JavaVersion.VERSION_11
        }
    }


    dependencies {

        apply(plugin = "org.jetbrains.kotlin.plugin.spring")
        apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
        apply(plugin = "org.jetbrains.kotlin.plugin.allopen")

        apply(plugin = "org.springframework.boot")
        apply(plugin = "io.spring.dependency-management")

        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))

        // Kotlin
        implementation(kotlin("gradle-plugin"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        // Spring Cloud
        implementation("org.springframework.cloud:spring-cloud-starter-config")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        // Flyway
        testImplementation("org.flywaydb:flyway-core:$flywayVersion")
        testImplementation("org.flywaydb.flyway-test-extensions:flyway-spring-test:$flywayVersion")

        // Query DSL
        implementation("com.querydsl:querydsl-jpa:${querydslVersion}")
        implementation("com.querydsl:querydsl-sql:${querydslVersion}") {
            exclude("joda-time")
        }
        kapt("com.querydsl:querydsl-apt:${querydslVersion}:jpa")

        // JSON
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")

        implementation("org.jasypt:jasypt-springsecurity4:1.9.3")

        dependencyManagement {
            imports {
                mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
            }
        }

        implementation("org.apache.commons:commons-pool2")

    }

    if (project.name != "core") {

        // Document
        apply(plugin = "com.epages.restdocs-api-spec")
        apply(plugin = "com.adarshr.test-logger")

        dependencies {
            implementation(project(":core"))
            implementation("org.springframework.boot:spring-boot-starter-actuator")
            kapt("org.springframework.boot:spring-boot-configuration-processor")
            // Spring Boot
            implementation("org.springframework.boot:spring-boot-starter-webflux")

            // Circuit Breaker
            implementation("org.springframework.cloud:spring-cloud-starter-netflix-hystrix")

            implementation("org.springframework.security:spring-security-core:3.0.3.RELEASE")

            implementation("org.modelmapper:modelmapper:2.3.2")
            implementation("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16")

            runtimeOnly("mysql:mysql-connector-java")

            testImplementation("org.springframework.boot:spring-boot-starter-test")
            testImplementation("io.projectreactor:reactor-test")
            implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

            // Log & Crash Dependency
            implementation("org.json:json:20171018")
            implementation("org.apache.httpcomponents:httpclient:4.5")
            implementation("ch.qos.logback:logback-classic:1.2.3")

            // coroutine
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutineVersion}")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutineVersion}")

            testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${coroutineVersion}")

            // Mock server
            testImplementation("org.mock-server:mockserver-netty:5.7.2")

            testImplementation("org.junit.platform:junit-platform-launcher")
            testImplementation("org.junit.jupiter:junit-jupiter-api")
            testImplementation("org.junit.jupiter:junit-jupiter-engine")

            // documentation
            testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient:${springRestdocsVersion}")
            testImplementation("com.epages:restdocs-api-spec:0.11.3")

            // MockK
            testImplementation("io.mockk:mockk:1.10.0")

            // Spring mockK
            testImplementation("com.ninja-squad:springmockk:2.0.3")

            implementation("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16")
            implementation("org.codehaus.janino:janino:3.0.15") // 설정 파일(xml)에서 JAVA 표현식(if 등)을 사용할 수 있게 해주는 라이브러리
        }

        tasks {
            test {
                useJUnitPlatform {
                    excludeEngines = setOf("junit-vintage")
                }
                testLogging {
                    showStandardStreams = false
                }
                outputs.dir(snippetsDir)
                jvmArgs = listOf("--illegal-access=permit")
            }
        }

    }
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.17")
}
