package datalayer.repositories;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import buisness.models.ForumPost;
import buisness.models.User;

public class ForumPostRepository {

    private final String url = "jdbc:mysql://localhost:3306/sda_proj";
    private final String user = "root";
    private final String password = "root";

    public void save(ForumPost post) {
        String sql = "INSERT INTO forum_posts (title, content, author_username, category, timestamp) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setString(3, post.getAuthor().getUsername()); // Assuming User is the author
            pstmt.setString(4, post.getCategory().toString()); // Assuming ForumCategory is an enum
            pstmt.setTimestamp(5, Timestamp.valueOf(post.getTimestamp()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        post.setPostId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving forum post: " + e.getMessage());
        }
    }

    public ForumPost findById(int postId) {
        String sql = "SELECT * FROM forum_posts WHERE post_id = ?";
        ForumPost post = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    post = new ForumPost();
                    post.setPostId(rs.getInt("post_id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));

                    // Retrieve associated User (author)
                    String authorUsername = rs.getString("author_username");
                    // You'll need to fetch the User object from the database using authorUsername
                    // For example:
                    User author = new UserRepository().findByUsername(authorUsername);
                    post.setAuthor(author);

                    // ... (Set other attributes like category and timestamp)
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving forum post by ID: " + e.getMessage());
        }

        return post;
    }

    public List<ForumPost> findAll() {
        String sql = "SELECT * FROM forum_posts";
        List<ForumPost> posts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ForumPost post = new ForumPost();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));

                // Retrieve associated User (author)
                // ... (similar to findById method)

                // ... (Set other attributes like category and timestamp)

                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all forum posts: " + e.getMessage());
        }

        return posts;
    }

    public void update(ForumPost post) {
        String sql = "UPDATE forum_posts SET title = ?, content = ?, category = ? " +
                "WHERE post_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setString(3, post.getCategory().toString()); // Assuming ForumCategory is an enum
            pstmt.setInt(4, post.getPostId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating forum post: " + e.getMessage());
        }
    }

    public void delete(int postId) {
        String sql = "DELETE FROM forum_posts WHERE post_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting forum post: " + e.getMessage());
        }
    }
}