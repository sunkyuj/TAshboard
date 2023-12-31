package project.tashboard.web.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.tashboard.domain.post.Post;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping
    public String posts(Model model) {
        List<PostResponse> posts = postService.findPosts()
                .stream()
                .map(PostResponse::build)
                .toList();
        model.addAttribute("posts", posts);
        log.info("posts={}", posts);
        log.info("model={}", model);
        return "board";
    }


    // 특정 board_id를 갖는 post들을 조회하여 화면에 표시
    @GetMapping("/{boardId}")
    public String getPostsByBoardId(@PathVariable Long boardId, Model model) {
        List<Post> posts = postService.findByBoardId(boardId);
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/{boardId}/{postId}")
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
