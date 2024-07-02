import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  id("com.github.johnrengelman.shadow")
}

dependencies {
  implementation("com.google.guava:guava")
}

tasks {
  named<ShadowJar>("shadowJar") {
    archiveBaseName.set("L01-fat")
    archiveVersion.set("")
    archiveClassifier.set("")
    manifest {
      attributes(mapOf("Main-Class" to "ru.otus.course.Application"))
    }
  }

  build {
    dependsOn(shadowJar)
  }
}
