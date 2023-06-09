package ru.hl.socialnetwork.service;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.hl.socialnetwork.mapper.post.PostMapper;
import ru.hl.socialnetwork.mapper.post.PostMapperImpl;
import ru.hl.socialnetwork.model.dao.PostDao;
import ru.hl.socialnetwork.repository.PostRepository;
import ru.hl.socialnetwork.service.impl.PostServiceImpl;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PostServiceImpl.class, PostMapperImpl.class})
class PostServiceTest {

  @Autowired
  private PostService postService;

  @Autowired
  private PostMapper postMapper;

  @MockBean
  private PostRepository postRepository;

  private final EasyRandom easyRandom = new EasyRandom();

  @Test
  void getPostsFeed() {
    int currentUserId = 1;

    var allPostsFromFriends = easyRandom.objects(PostDao.class, 10).collect(Collectors.toList());

    when(postRepository.getAllPostsFromFriends(currentUserId)).thenReturn(allPostsFromFriends);

    var postsFeed = postService.getPostsFeed(currentUserId);

    assertThat(postsFeed)
        .hasSize(allPostsFromFriends.size())
        .containsAll(allPostsFromFriends.stream().map(postMapper::toPostResponseDto).collect(Collectors.toList()));
  }

  @Test
  void createPost() {
    doNothing().when(postRepository).createPost(anyString(), anyInt());

    postService.createPost("text", 1);

    verify(postRepository, times(1)).createPost(anyString(), anyInt());
  }

  @Test
  void updatePost() {
    doNothing().when(postRepository).updatePost(anyInt(), anyString(), anyInt());

    postService.updatePost(1, "text", 1);

    verify(postRepository, times(1)).updatePost(anyInt(), anyString(), anyInt());
  }

  @Test
  void deletePost() {
    doNothing().when(postRepository).deletePost(anyInt());

    postService.deletePost(1);

    verify(postRepository, times(1)).deletePost(anyInt());
  }
}