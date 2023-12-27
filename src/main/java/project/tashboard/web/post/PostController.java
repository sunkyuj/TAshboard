package project.tashboard.web.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.tashboard.domain.post.Post;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.findPosts()
                .stream()
                .map(PostResponse::build)
                .toList();
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        Post post = postService.findPost(postId).orElse(null);
        if (post == null) { // Not Found, TODO: 예외 처리
            return null;
        }
        return PostResponse.build(post);
    }

    @PostMapping
    public PostResponse addPost(@RequestBody PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
//                .member(postRequest.getMember())
                .build();
        Post newPost = postService.addPost(post);
        return PostResponse.build(newPost);
    }


}
