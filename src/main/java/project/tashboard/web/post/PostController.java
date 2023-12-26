package project.tashboard.web.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.tashboard.domain.post.Post;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getPosts() {

        return postService.findPosts()
                .stream()
                .map(post -> PostDTO.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .member(post.getMember())
                        .comments(post.getComments())
                        .build()
                )
                .toList();
    }

    @GetMapping("/{postId}")
    public PostDTO getPost(@PathVariable Long postId) {
        Post post = postService.findPost(postId).orElse(null);
        if (post == null) {
            return null;
        }
        return PostDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .member(post.getMember())
                .comments(post.getComments())
                .build();
    }


}
