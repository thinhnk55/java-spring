plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("io.freefair.lombok") version "8.10.2"
}

group = "work.vietdefi"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}
configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	compileOnly("org.projectlombok:lombok")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.withType<JavaCompile> {
	options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:unchecked"))
}
