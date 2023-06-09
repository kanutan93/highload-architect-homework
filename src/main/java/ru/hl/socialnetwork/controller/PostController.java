package ru.hl.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

import java.util.List;

public interface PostController {

  ResponseEntity<List<PostResponseDto>> getPostsFeed();

  ResponseEntity<Void> createPost(String text, Integer authorUserId);

  ResponseEntity<Void> updatePost(Integer id, String text, Integer authorUserId);

  ResponseEntity<Void> deletePost(Integer id);
}
