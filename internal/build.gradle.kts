import com.epages.restdocs.apispec.gradle.OpenApi3Extension

dependencies {

    // oAuth2.0
    /** @see https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Features-Matrix */
    implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure") {
        exclude("org.springframework.security.oauth", "spring-security-oauth2")
    }

    implementation("com.auth0:java-jwt:3.11.0")
    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

configure<OpenApi3Extension> {
    setServer(rootProject.property("internal.api.url") as String)
    title = "${rootProject.name}-${project.name}"
    version = project.version as String
    description = """
                  | Member Internal API
                  """.trimMargin()
    format = "yml"
    separatePublicApi = true
    outputFileNamePrefix = "${rootProject.name}-${project.name}"
}
