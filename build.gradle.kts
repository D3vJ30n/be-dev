plugins {
    id("java")
    id("war")
    id("org.springframework.boot") version "3.3.5"  // 최신 버전으로 업데이트
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

    // 테스트
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}