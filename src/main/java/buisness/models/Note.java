package buisness.models;

import java.time.LocalDateTime;

public class Note {
    private int noteId; // Unique identifier for the note (optional)
    private String content; // The actual text content of the note
    private LocalDateTime timestamp; // Date and time when the note was created
    private User author; // The user who created the note (e.g., Case Officer)

    // Constructors
    public Note() {
        // Default constructor
    }

    public Note(String content, User author) {
        this.content = content;
        this.author = author;
        this.timestamp = LocalDateTime.now(); // Set timestamp to current time
    }

    // Getters
    public int getNoteId() {
        return noteId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public User getAuthor() {
        return author;
    }

    // Setters
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
