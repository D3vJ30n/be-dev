plugins {
    id("java")
    id("war")
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_23
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")

    // Spring MVC
    implementation("org.springframework:spring-webmvc")

    // JSP 지원
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper")
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api")

    // SQLite
    implementation("org.xerial:sqlite-jdbc:3.43.0.0")

    // HTTP Client - Spring Boot에 이미 포함되어 있으므로 okhttp3는 제외

    // JSON 처리 - Spring Boot에 이미 포함되어 있으므로 gson은 제외

    // Lombok
//    compileOnly("org.projectlombok:lombok:1.18.30")
//    annotationProcessor("org.projectlombok:lombok:1.18.30")
//    testCompileOnly("org.projectlombok:lombok:1.18.30")         // 테스트용 추가
//    testAnnotationProcessor("org.projectlombok:lombok:1.18.30") // 테스트용 추가

    // 테스트
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    doFirst {
        jvmArgs("-XX:+EnableDynamicAgentLoading", "-Djdk.instrument.traceUsage", "-Xshare:off")
    }
}