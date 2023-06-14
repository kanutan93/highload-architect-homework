package ru.hl.socialnetwork.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.socialnetwork.kafka.payload.PostPayload;
import ru.hl.socialnetwork.mapper.post.PostMapper;
import ru.hl.socialnetwork.model.dao.PostDao;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;
import ru.hl.socialnetwork.repository.PostRepository;
import ru.hl.socialnetwork.service.FriendService;
import ru.hl.socialnetwork.service.PostService;
import ru.hl.socialnetwork.service.ProfileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.hl.socialnetwork.kafka.payload.PostPayload.Action.CREATE;
import static ru.hl.socialnetwork.kafka.payload.PostPayload.Action.DELETE;
import static ru.hl.socialnetwork.kafka.payload.PostPayload.Action.UPDATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private static final String POSTS_FEED_CACHE = "postsFeedCache";
  private final PostRepository postRepository;
  private final PostMapper postMapper;
  private final KafkaTemplate kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final FriendService friendService;
  private final ProfileService profileService;
  private final CacheManager cacheManager;

  @Value("${spring.kafka.template.default-topic}")
  private String kafkaTopic;

  @Override
  @Transactional(readOnly = true)
  public List<PostResponseDto> getPostsFeed() {
    int currentUserId = profileService.getCurrentProfile().getId();

    log.info("Trying to get posts feed for user with id = {}", currentUserId);

    Cache cache = cacheManager.getCache(POSTS_FEED_CACHE);
    List<PostResponseDto> postsFeedFromCache = Optional.ofNullable(cache)
        .map(it -> it.get(currentUserId, List.class))
        .orElse(null);
    if (postsFeedFromCache != null) {
      log.info("Posts feed for user with id = {} was successfully received from cache", currentUserId);
      return postsFeedFromCache;
    } else {
      List<PostDao> allPostsFromFriends = postRepository.getAllPostsFromFriends(currentUserId);
      List<PostResponseDto> postsFeed = allPostsFromFriends.stream()
          .map(postMapper::toPostResponseDto)
          .collect(Collectors.toList());
      cache.put(currentUserId, postsFeed);
      log.info("Posts feed for user with id = {} was successfully received", currentUserId);
      return postsFeed;
    }
  }

  @Override
  @SneakyThrows
  @Transactional
  public void createPost(String text, Integer authorUserId) {
    log.info("Trying to create post by user with id = {} and text = {}", authorUserId, text);

    int id = postRepository.createPost(text, authorUserId);
    PostResponseDto post = postMapper.toPostResponseDto(postRepository.getPostById(id));

    sendPostToFriends(CREATE, post);

    log.info("Post by user with id = {} and text = {} was successfully created", authorUserId, text);
  }

  @Override
  @Transactional
  public void updatePost(Integer id, String text, Integer authorUserId) {
    log.info("Trying to update post with id = {} by user with id = {} and text = {}", id, authorUserId, text);

    postRepository.updatePost(id, text, authorUserId);
    PostResponseDto post = postMapper.toPostResponseDto(postRepository.getPostById(id));

    sendPostToFriends(UPDATE, post);

    log.info("Post with id = {} by user with id = {} and text = {} was successfully updated", id, authorUserId, text);
  }

  @Override
  @Transactional
  public void deletePost(Integer id) {
    log.info("Trying to delete post with id = {}", id);

    PostResponseDto post = postMapper.toPostResponseDto(postRepository.getPostById(id));
    postRepository.deletePost(id);

    sendPostToFriends(DELETE, post);

    log.info("Post with id = {} was successfully deleted", id);
  }

  @SneakyThrows
  private void sendPostToFriends(PostPayload.Action action, PostResponseDto post) {
    for (var friendId : friendService.getAllFriendsIds()) {
      PostPayload postPayload = postMapper.toPostPayload(friendId, action, post);
      kafkaTemplate.send(kafkaTopic, objectMapper.writeValueAsString(postPayload));
    }
  }
}