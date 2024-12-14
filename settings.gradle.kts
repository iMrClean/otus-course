rootProject.name = "otus-course"

include("L01")
include("L02")
include("L03")
include("L04")
include("L05")
include("L06")
include("L07")
include("L08")
include("L09")
include("L10")
include("L11")
include("L12")
include("L13")
include("L14")
include("L15")
include("L16")
include("L17")

pluginManagement {
  val jgitver: String by settings
  val dependencyManagement: String by settings
  val springframeworkBoot: String by settings
  val johnrengelmanShadow: String by settings
  val jib: String by settings
  val protobufVer: String by settings
  val sonarlint: String by settings
  val spotless: String by settings

  plugins {
    id("fr.brouillard.oss.gradle.jgitver") version jgitver
    id("io.spring.dependency-management") version dependencyManagement
    id("org.springframework.boot") version springframeworkBoot
    id("com.github.johnrengelman.shadow") version johnrengelmanShadow
    id("com.google.cloud.tools.jib") version jib
    id("com.google.protobuf") version protobufVer
    id("name.remal.sonarlint") version sonarlint
    id("com.diffplug.spotless") version spotless
  }
}
