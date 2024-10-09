dependencies {
  implementation("ch.qos.logback:logback-classic")
  implementation("org.flywaydb:flyway-core:8.5.11")
  implementation("org.postgresql:postgresql")
  implementation("com.zaxxer:HikariCP")
  implementation("org.springframework:spring-core")

  testImplementation("org.junit.jupiter:junit-jupiter-engine")
  testImplementation("org.junit.jupiter:junit-jupiter-params")
  testImplementation("org.assertj:assertj-core")
  testImplementation("org.mockito:mockito-junit-jupiter")

  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:postgresql")
}
