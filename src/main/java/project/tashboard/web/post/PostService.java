package project.tashboard.web.post;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.post.Post;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> findPost(Long postId) {
        return postRepository.findById(postId);
    }

    public Post addPost(Post post) {
        // TODO: member 정보 추가
        return postRepository.save(post);
    }

    public List<Post> findBoardPosts(BoardType boardType) {
        return postRepository.findByBoardType(boardType);
    }
}
