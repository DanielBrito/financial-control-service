plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"

	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"

	id("info.solidsoft.pitest") version "1.15.0"
	id("org.unbroken-dome.test-sets") version "4.1.0"
	id("io.gitlab.arturbosch.detekt") version "1.23.7"
	id("jacoco")
	id("org.sonarqube") version "5.1.0.4882"
}

group = "com.polymatus"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val assertJVersion = "3.26.3"
val detektVersion = "1.23.7"
val mockkVersion = "1.13.12"
val jacksonVersion = "2.18.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")

	compileOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.assertj:assertj-core:$assertJVersion")
	testImplementation("io.mockk:mockk:$mockkVersion")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
	detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detektVersion")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

detekt {
	toolVersion = detektVersion
	autoCorrect = true
}

configurations.all {
	resolutionStrategy.eachDependency {
		if (requested.group == "org.jetbrains.kotlin") {
			useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

fun ignorePackagesInJacocoReport(classDirectories: ConfigurableFileCollection) {
	classDirectories.setFrom(
		files(
			classDirectories.files.map {
				fileTree(it).apply {
					exclude(
						"**/polymatus/**/*.java",
						"**/polymatus/**/*.kts"
					)
				}
			}
		)
	)
}

tasks.test {
	finalizedBy("jacocoReport")
}

tasks.register<JacocoReport>("jacocoReport") {
	sourceSets(sourceSets.main.get())
	executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.required.set(true)
	}
	ignorePackagesInJacocoReport(classDirectories)
}
