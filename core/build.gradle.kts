import org.springframework.boot.gradle.tasks.bundling.*

tasks {
    getByName<BootJar>("bootJar") {
        enabled = false
    }
    getByName<Jar>("jar") {
        enabled = true
    }
}
