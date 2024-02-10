package ru.hl.primaryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.primaryservice.model.dto.request.post.CreatePostRequestDto;
import ru.hl.primaryservice.model.dto.request.post.UpdatePostRequestDto;
import ru.hl.primaryservice.model.dto.response.PostResponseDto;

import java.util.List;

@RequestMapping("/api/post")
public interface PostController {

  @GetMapping("/feed")
  ResponseEntity<List<PostResponseDto>> getPostsFeed();


  @PostMapping("/create")
  ResponseEntity<Void> createPost(@RequestBody CreatePostRequestDto createPostRequestDto);

  @PutMapping("/update")
  ResponseEntity<Void> updatePost(@RequestBody UpdatePostRequestDto updatePostRequestDto);

  @DeleteMapping("/delete/{id}")
  ResponseEntity<Void> deletePost(@PathVariable Integer id);
}
