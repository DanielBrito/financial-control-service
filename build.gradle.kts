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

    source.setFrom(
        files(
            "src/main/kotlin",
            "src/test/kotlin",
            "src/integrationTest/kotlin"
        )
    )
}

testSets {
    create("integrationTest") {
        dirName = "integrationTest"
    }
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
				"**/polymatus/**/FinancialControlServiceApplication.kt"
		)
	}
}

pitest {
    val skippedClasses = listOf(
        "com.polymatus.financialcontrolservice.FinancialControlServiceApplicationKt",
        "com.polymatus.financialcontrolservice.inbound.controllers.*",
    )

    junit5PluginVersion.set("1.2.0")
    targetClasses.set(listOf("com.polymatus.*"))
    excludedClasses.set(skippedClasses)
    targetTests.set(listOf("com.polymatus.financialcontrolservice.*"))
    outputFormats.set(listOf("HTML"))
    threads.set(2)
    jvmArgs.set(listOf("-Xmx2G"))
    mutationThreshold.set(80)
    failWhenNoMutations.set(false)

    mainSourceSets.set(listOf(sourceSets["main"]))
    testSourceSets.set(listOf(sourceSets["test"]))
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
    useJUnitPlatform()

    description = "Runs unit tests"
    group = "verification"
}

tasks.named<Test>("integrationTest") {
    useJUnitPlatform()

    description = "Runs integration tests"
    group = "verification"

    shouldRunAfter("test")

    extensions.configure(JacocoTaskExtension::class) {
        setDestinationFile(file("${layout.buildDirectory.file("jacoco/integrationTest.exec").get().asFile}"))
    }
}

tasks.register<JacocoReport>("jacocoReport") {
    description = "Generates JaCoCo report combining unit and integration tests"
    group = "verification"

    dependsOn("test", "integrationTest") // ensure both run before report

    val testExec = file("${layout.buildDirectory}/jacoco/test.exec")
    val integrationExec = file("${layout.buildDirectory}/jacoco/integrationTest.exec")

    executionData(testExec, integrationExec)
    sourceSets(sourceSets["main"])

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        "**/polymatus/**/*.java",
                        "**/polymatus/**/*.kts"
                    )
                }
            }
        )
    )
}
