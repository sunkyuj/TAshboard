package project.tashboard.api.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tashboard.domain.post.Post;
import project.tashboard.web.post.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostApiController {
    private final PostService postService;

    @GetMapping()
    public List<Post> getPosts() {
        return postService.findPosts();
    }


    @GetMapping("/{boardPath}/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        Post post = postService.findPost(postId).orElse(null);
        if (post == null) { // Not Found, TODO: 예외 처리
            return null;
        }
        return PostResponse.build(post);
    }

    @PostMapping("/{boardPath}")
    public PostResponse addPost(@PathVariable String boardPath, @RequestBody PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .contents(postRequest.getContent())
//                .member(postRequest.getMember())
                .build();
        Post newPost = postService.addPost(post);
        return PostResponse.build(newPost);
    }

}
