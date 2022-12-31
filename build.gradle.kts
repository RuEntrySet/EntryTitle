plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version("6.1.0")
}

group = "ru.entryset"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.rosewooddev.io/repository/public/")
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/RuEntrySet/EntryAPI")
        credentials {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_PASSWORD")
        }
    }
}

dependencies {
    val library = "C:\\Users\\t9154\\Desktop\\Исходники\\Library/"

    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    implementation("ru.entryset:api:3.2.5")

    compileOnly("org.black_ixx:playerpoints:3.0.3")
    compileOnly("me.clip:placeholderapi:2.10.9")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {
    jar {
        enabled = false
        dependsOn(shadowJar)
    }
}