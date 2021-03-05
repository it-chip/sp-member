import org.jetbrains.kotlin.gradle.tasks.*

val snippetsDir: String by extra("build/generated-snippets")
val coroutineVersion: String by extra { "1.3.9" }
val springRestdocsVersion: String by extra { "2.0.4.RELEASE" }
val springCloudVersion: String by extra { "Hoxton.SR8" }

plugins {
    val kotlinVersion = "1.4.30"
    val springBootVersion = "2.3.3.RELEASE"
    val springDependencyManagementVersion = "1.0.11.RELEASE"

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
}

group = "com.sp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("gradle-plugin"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutineVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutineVersion}")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${coroutineVersion}")

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")        //kotlin extension 기능 적용 모

    // documentation
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient:${springRestdocsVersion}")
//    testImplementation("com.epages:restdocs-api-spec:0.8.2")

    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // JSON
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")            //jackson 에서 kotlin 지원을 위한 모듈
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")        //자바 8의 시간 타입을 지원하는 모듈
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")    //지연로딩과 조회 성능 최적화

    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")

    // MockK
    testImplementation("io.mockk:mockk:1.10.0")

    // Spring mockK
    testImplementation("com.ninja-squad:springmockk:2.0.3")

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    outputs.dir(snippetsDir)
}
