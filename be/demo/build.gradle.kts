plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
	maven(url = "https://repo.thingsboard.io/artifactory/libs-release-public")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.0")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.3.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.flywaydb:flyway-core:9.7.0")
	implementation("org.postgresql:postgresql:42.7.3")
	implementation("org.springframework.data:spring-data-jpa:3.3.0")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.3.0")
	implementation("org.springframework.boot:spring-boot-starter-security:3.3.0")
	implementation("org.springframework.boot:spring-boot-starter-tomcat:3.3.0")
	implementation("io.jsonwebtoken:jjwt:0.12.5")
	implementation("commons-io:commons-io:2.11.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("com.h2database:h2")
	implementation("org.thingsboard:rest-client:3.6.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
