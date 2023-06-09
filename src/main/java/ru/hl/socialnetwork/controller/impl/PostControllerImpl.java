package ru.hl.socialnetwork.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.socialnetwork.controller.PostController;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;
import ru.hl.socialnetwork.service.PostService;
import ru.hl.socialnetwork.util.CurrentUserUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

  private final PostService postService;

  @Override
  public ResponseEntity<List<PostResponseDto>> getPostsFeed() {
    //TODO: get current user from security context
    var currentUser = CurrentUserUtil.getCurrentUser().getUsername();
//    List<PostResponseDto> postsFeed = postService.getPostsFeed(currentUser);
    return ResponseEntity.ok(null);
  }

  @Override
  public ResponseEntity<Void> createPost(String text, Integer authorUserId) {
    postService.createPost(text, authorUserId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> updatePost(Integer id, String text, Integer authorUserId) {
    postService.updatePost(id, text, authorUserId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deletePost(Integer id) {
    postService.deletePost(id);
    return ResponseEntity.ok().build();
  }
}
