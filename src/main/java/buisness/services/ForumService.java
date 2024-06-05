package buisness.services;

import buisness.models.ForumCategory;
import buisness.models.ForumPost;
import buisness.models.User;
import datalayer.repositories.ForumPostRepository;

// ForumService.java
public class ForumService {
    private ForumPostRepository forumPostRepository;

    public ForumService(ForumPostRepository forumPostRepository) {
        this.forumPostRepository = forumPostRepository;
    }

    public ForumPost createForumPost(String title, String content, User author, ForumCategory category) {
        ForumPost post = new ForumPost(title, content, author, category);
        forumPostRepository.save(post);
        return post;
    }

    // ... (other forum-related operations)
}