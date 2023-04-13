package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStudImpl implements PostRepository{
    private Map<Long, Post> posts = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong();

    // Method of repository realizing
    // return all Post
    public List<Post> all() {
        List<Post> postList = new ArrayList<>(posts.values());
        return postList;
    }

    // return by id
    public Optional<Post> getById(long id) {
        // assume the transmitted value can be null
        Optional<Post> opt = Optional.ofNullable(posts.get(id));
        return opt;
    }

    // save Post
    public Post save(Post post) {
        // f this is a new post
        if (post.getId() == 0) {
            Post newPost = new Post(getNewId(), post.getContent());
            posts.put(newPost.getId(), newPost);
            return newPost;
        }
        // if there is already a post
        if (isContainsId(post.getId())) {
            posts.put(post.getId(), post);
            return post;
        }

        throw new NotFoundException("Post not found!!!");

    }

    // remove Post
    public void removeById(long id) {
        posts.remove(id);
    }

    public boolean isContainsId(Long id) {
        return posts.containsKey(id);
    }

    public Long getNewId() {
        return id.incrementAndGet();
    }
}
