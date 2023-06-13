package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.model.dao.PostDao;

import java.util.List;

public interface PostRepository {

    List<PostDao> getAllPostsFromFriends(Integer userId);

    PostDao getPostById(Integer id);

    int createPost(String text, Integer authorUserId);

    void updatePost(Integer id, String text, Integer authorUserId);

    void deletePost(Integer id);

}
