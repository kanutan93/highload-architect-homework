package ru.hl.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.socialnetwork.mapper.post.PostMapper;
import ru.hl.socialnetwork.model.dao.PostDao;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;
import ru.hl.socialnetwork.repository.PostRepository;
import ru.hl.socialnetwork.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "posts", key = "#currentUserId")
  public List<PostResponseDto> getPostsFeed(Integer currentUserId) {
    log.info("Trying to get posts feed for user with id = {}", currentUserId);

    List<PostDao> allPostsFromFriends = postRepository.getAllPostsFromFriends(currentUserId);
    List<PostResponseDto> postsFeed = allPostsFromFriends.stream()
        .map(postMapper::toPostResponseDto)
        .collect(Collectors.toList());

    log.info("Posts feed for user with id = {} was successfully received", currentUserId);
    return postsFeed;
  }

  @Override
  @Transactional
  @CacheEvict(value = "posts")
  public void createPost(String text, Integer authorUserId) {
    log.info("Trying to create post by user with id = {} and text = {}", authorUserId, text);

    postRepository.createPost(text, authorUserId);

    log.info("Post by user with id = {} and text = {} was successfully created", authorUserId, text);
  }

  @Override
  @Transactional
  @CacheEvict(value = "posts")
  public void updatePost(Integer id, String text, Integer authorUserId) {
    log.info("Trying to update post with id = {} by user with id = {} and text = {}", id, authorUserId, text);

    postRepository.updatePost(id, text, authorUserId);

    log.info("Post with id = {} by user with id = {} and text = {} was successfully updated", id, authorUserId, text);
  }

  @Override
  @Transactional
  @CacheEvict(value = "posts")
  public void deletePost(Integer id) {
    log.info("Trying to delete post with id = {}", id);

    postRepository.deletePost(id);

    log.info("Post with id = {} was successfully deleted", id);
  }
}
