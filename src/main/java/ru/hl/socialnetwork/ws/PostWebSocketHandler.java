package ru.hl.socialnetwork.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;
import ru.hl.socialnetwork.repository.UserRepository;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostWebSocketHandler implements WebSocketHandler {

  private static final String USER_ID = "userId";

  private final ObjectMapper objectMapper;
  private final UserRepository userRepository;

  private Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    int userId = getUserId(session);
    session.getAttributes().put(USER_ID, userId);
    log.info("New WebSocket connection established with id: {}, userId: {}", session.getId(), userId);
    sessions.put(userId, session);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {

  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    log.error("Error in WebSocket connection", exception);
    Object userId = session.getAttributes().get(USER_ID);
    sessions.remove(userId);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
    log.info("WebSocket connection closed for id: {}", session.getId());
    int userId = (int) session.getAttributes().get(USER_ID);
    sessions.remove(userId);
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

  @SneakyThrows
  public void sendPostsToClient(Integer userId, LinkedList<PostResponseDto> postsFeed) {
    WebSocketSession session = sessions.get(userId);
    if (session != null && session.isOpen()) {
      String message = objectMapper.writeValueAsString(postsFeed);
      session.sendMessage(new TextMessage(message));
    }
  }

  private int getUserId(WebSocketSession session) {
    String email = session.getPrincipal().getName();
    UserDao userDao = userRepository.getByEmail(email);
    return userDao.getId();
  }
}
