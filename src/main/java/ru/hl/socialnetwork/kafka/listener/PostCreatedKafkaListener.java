package ru.hl.socialnetwork.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.hl.socialnetwork.kafka.payload.PostPayload;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostCreatedKafkaListener {

  private static final String POSTS_FEED_CACHE = "postsFeedCache";

  private final CacheManager cacheManager;
  private final ObjectMapper objectMapper;

  @KafkaListener(
      topics = "${spring.kafka.template.default-topic}",
      groupId = "${spring.kafka.consumer.group-id}"
  )
  public void listen(@Payload String payload) {
    log.info("New message received: {}", payload);

    PostPayload postPayload = objectMapper.convertValue(payload, PostPayload.class);

    Integer userId = postPayload.getReceiverUserId();
    var messagePost = postPayload.getPostResponseDto();

    log.info("New post: {} created for userId: {}", messagePost, userId);

    Cache postsFeedCache = cacheManager.getCache(POSTS_FEED_CACHE);
    if (postsFeedCache != null) {
      List<PostResponseDto> postsFeed = postsFeedCache.get(userId, List.class);

      if (!CollectionUtils.isEmpty(postsFeed)) {
        LinkedList<PostResponseDto> updatedPostsFeed = postsFeed.stream()
            .filter(post -> !post.getAuthorUserId().equals(messagePost.getAuthorUserId()))
            .collect(Collectors.toCollection(LinkedList::new));
        updatedPostsFeed.addFirst(messagePost);
        postsFeedCache.put(userId, updatedPostsFeed);
        log.info("Posts feed cache for userId: {} was updated", userId);
      } else {
        log.info("Posts feed cache for userId: {} is empty", userId);
      }
    }
  }
}
