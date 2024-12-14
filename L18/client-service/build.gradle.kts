dependencies {
  implementation("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-websocket")
  implementation("org.webjars:webjars-locator-core")
  implementation("org.webjars:sockjs-client")
  implementation("org.webjars:stomp-websocket")
  implementation("org.webjars:bootstrap")
}
