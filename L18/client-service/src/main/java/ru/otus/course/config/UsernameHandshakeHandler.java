package ru.otus.course.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import reactor.util.annotation.Nullable;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class UsernameHandshakeHandler extends DefaultHandshakeHandler {

  @Override
  protected Principal determineUser(@Nullable ServerHttpRequest request, @Nullable WebSocketHandler wsHandler, @Nullable Map<String, Object> attributes) {
    return new WsPrincipal(UUID.randomUUID().toString());
  }

}
