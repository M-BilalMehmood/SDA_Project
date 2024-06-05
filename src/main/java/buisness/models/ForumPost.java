package buisness.models;

import java.time.LocalDateTime;
import java.util.List;

public class ForumPost {
    private int postId; // Unique identifier
    private String title;
    private String content;
    private User author; // User who created the post
    private ForumCategory category; // Category of the forum post
    private LocalDateTime timestamp; // Date and time of the post
    private List<Comment> comments; // List of comments on the post
    private List<User> likes; // Users who liked the post

    // Constructors
    public ForumPost() {
        // Default constructor
    }

    public ForumPost(int postId, String title, String content, User author, ForumCategory category, LocalDateTime timestamp) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.timestamp = timestamp;
    }

    public ForumPost(String title, String content, User author, ForumCategory category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public int getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public ForumCategory getCategory() {
        return category;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<User> getLikes() {
        return likes;
    }

    // Setters
    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setCategory(ForumCategory category) {
        this.category = category;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }
}
