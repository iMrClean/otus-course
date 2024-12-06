dependencies {
  implementation("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  implementation("ch.qos.logback:logback-classic")
  implementation("org.reflections:reflections")

  testImplementation("org.junit.jupiter:junit-jupiter-engine")
  testImplementation("org.junit.jupiter:junit-jupiter-params")
  testImplementation("org.assertj:assertj-core")
  testImplementation("org.mockito:mockito-junit-jupiter")
}
