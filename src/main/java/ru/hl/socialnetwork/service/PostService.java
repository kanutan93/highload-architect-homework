package ru.hl.socialnetwork.service;

import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {

    List<PostResponseDto> getPostsFeed();

    void createPost(String text);

    void updatePost(Integer id, String text);

    void deletePost(Integer id);
}
