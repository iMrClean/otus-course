dependencies {
  implementation("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
  implementation("io.r2dbc:r2dbc-postgresql")
  implementation("org.postgresql:postgresql")
  implementation("org.flywaydb:flyway-core:8.5.11")
}
