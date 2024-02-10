package ru.hl.primaryservice.service;

import ru.hl.primaryservice.model.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {

    List<PostResponseDto> getPostsFeed();

    void createPost(String text);

    void updatePost(Integer id, String text);

    void deletePost(Integer id);
}
