plugins {
    id("java")
}

group = "org.back6pace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
    options.memberLevel = JavadocMemberLevel.PUBLIC
    options.quiet()
}

