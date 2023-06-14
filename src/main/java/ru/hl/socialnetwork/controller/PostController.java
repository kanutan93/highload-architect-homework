package ru.hl.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

import java.util.List;

@RequestMapping("/api/post")
public interface PostController {

  @GetMapping("/feed")
  ResponseEntity<List<PostResponseDto>> getPostsFeed();


  @PostMapping("/create")
  ResponseEntity<Void> createPost(@RequestBody String text);

  @PostMapping("/update/{id}")
  ResponseEntity<Void> updatePost(Integer id, String text);

  @DeleteMapping("/delete/{id}")
  ResponseEntity<Void> deletePost(@PathVariable Integer id);
}
