package ru.hl.primaryservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.primaryservice.controller.PostController;
import ru.hl.primaryservice.model.dto.request.post.CreatePostRequestDto;
import ru.hl.primaryservice.model.dto.request.post.UpdatePostRequestDto;
import ru.hl.primaryservice.model.dto.response.PostResponseDto;
import ru.hl.primaryservice.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

  private final PostService postService;

  @Override
  public ResponseEntity<List<PostResponseDto>> getPostsFeed() {
    List<PostResponseDto> postsFeed = postService.getPostsFeed();
    return ResponseEntity.ok(postsFeed);
  }

  @Override
  public ResponseEntity<Void> createPost(CreatePostRequestDto createPostRequestDto) {
    postService.createPost(createPostRequestDto.getText());
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> updatePost(UpdatePostRequestDto updatePostRequestDto) {
    Integer id = updatePostRequestDto.getId();
    String text = updatePostRequestDto.getText();
    postService.updatePost(id, text);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deletePost(Integer id) {
    postService.deletePost(id);
    return ResponseEntity.ok().build();
  }
}
