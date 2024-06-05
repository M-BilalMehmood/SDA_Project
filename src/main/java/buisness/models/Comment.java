package buisness.models;

import java.time.LocalDateTime;

public class Comment {
    private int commentId; // Unique identifier for the comment
    private String content; // The actual text content of the comment
    private User author; // The user who created the comment
    private LocalDateTime timestamp; // Date and time when the comment was created

    // Constructors
    public Comment() {
        // Default constructor
    }

    public Comment(int commentId, String content, User author, LocalDateTime timestamp) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
    }

    // Getters
    public int getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
