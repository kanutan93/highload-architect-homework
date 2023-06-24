package ru.hl.socialnetwork.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.hl.socialnetwork.ws.PostWebSocketHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  private static final String ENDPOINT = "api/post/feed/posted";
  private static final String ALLOWED_ORIGINS = "*";

  private final PostWebSocketHandler postWebSocketHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(postWebSocketHandler, ENDPOINT).setAllowedOrigins(ALLOWED_ORIGINS);
  }
}
