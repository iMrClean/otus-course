import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  id("java")
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
  implementation("org.slf4j:slf4j-api:2.0.0")
  implementation("ch.qos.logback:logback-classic:1.4.12")
}

tasks {
  named<ShadowJar>("shadowJar") {
    archiveBaseName.set("L04")
    archiveVersion.set("")
    archiveClassifier.set("")
    manifest {
      attributes(mapOf("Main-Class" to "ru.otus.course.CalcDemo"))
    }
  }

  build {
    dependsOn(shadowJar)
  }
}
