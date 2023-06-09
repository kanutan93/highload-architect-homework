package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.model.dao.PostDao;

import java.util.List;

public interface PostRepository {

    List<PostDao> getAllPostsFromFriends(Integer userId);

    void createPost(String text, Integer authorUserId);

    void updatePost(Integer id, String text, Integer authorUserId);

    void deletePost(Integer id);

}
