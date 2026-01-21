import info.solidsoft.gradle.pitest.PitestPluginExtension

plugins {
	kotlin("jvm") version "2.0.10"
	kotlin("plugin.spring") version "2.0.10"
	kotlin("plugin.jpa") version "2.0.10"

	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"

	id("info.solidsoft.pitest") version "1.19.0-rc.2"
	id("org.unbroken-dome.test-sets") version "4.1.0"
	id("io.gitlab.arturbosch.detekt") version "1.23.7"
	id("jacoco")
	id("org.sonarqube") version "6.1.0.5360"
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
val kotestVersion = "5.9.1"
val kotestExtensionsSpringVersion = "1.3.0"
val wiremockVersion = "3.0.1"

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
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
	testImplementation("io.kotest:kotest-property:$kotestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestExtensionsSpringVersion")
    testImplementation("com.github.tomakehurst:wiremock-standalone:${wiremockVersion}")

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

    config.setFrom(files("$projectDir/detekt.yml"))

    source.setFrom(
        files(
            "src/main/kotlin",
            "src/test/kotlin",
            "src/integrationTest/kotlin",
            "src/componentTest/kotlin"
        )
    )
}

testSets {
    "integrationTest"()
    "componentTest"()
}

sonar {
	val projectKey = System.getenv("SONAR_PROJECT_KEY") ?: ""
	val organization = System.getenv("SONAR_ORGANIZATION") ?: ""

	properties {
		property("sonar.projectKey", projectKey)
		property("sonar.language", "kotlin")
		property("sonar.organization", organization)
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.exclusions", "**/polymatus/**/*.java," +
				"**/polymatus/**/*.kts," +
				"**/polymatus/**/FinancialControlServiceApplication.kt, " +
                "**/polymatus/**/infrastructure/repositories/entities/**",
		)
	}
}

pitest {
    junit5PluginVersion.set("1.2.3")
    excludedMethods.set(listOf("get*"))
    outputFormats.set(listOf("HTML"))
    threads.set(2)
    jvmArgs.set(listOf("-Xmx2G"))
    mutationThreshold.set(80)
    failWhenNoMutations.set(false)
    avoidCallsTo.set(setOf("kotlin.jvm.internal"))

    mainSourceSets.set(listOf(sourceSets["main"]))
    testSourceSets.set(listOf(sourceSets["test"]))
}

configure<PitestPluginExtension> {
    targetClasses.set(listOf("com.polymatus.financialcontrolservice.*"))
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
						"**/polymatus/**/*.kts",
                        "**/polymatus/**/FinancialControlServiceApplication*",
                        "**/polymatus/**/infrastructure/repositories/entities/**",
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
    description = "Generates the HTML documentation for this project"
    group = JavaBasePlugin.DOCUMENTATION_GROUP

    sourceSets(sourceSets.main.get())
    executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }

    ignorePackagesInJacocoReport(classDirectories)
}
