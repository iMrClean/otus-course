dependencies {
  implementation("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
  implementation("org.postgresql:postgresql")
  implementation("org.flywaydb:flyway-core:8.5.11")
}
