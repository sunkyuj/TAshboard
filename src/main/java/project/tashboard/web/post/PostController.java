package project.tashboard.web.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.post.Post;

import java.util.List;

import static project.tashboard.domain.board.BoardLists.boards;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping()
    public String getPosts(Model model) {
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    // 특정 board_id를 갖는 post들을 조회하여 화면에 표시
    @GetMapping("/{boardPath}")
    public String getBoardPosts(@PathVariable String boardPath, Model model) {
        BoardType boardType = getBoardType(boardPath);
        List<Post> posts = postService.findBoardPosts(boardType);
        model.addAttribute("board", boards.get(boardType.ordinal()));
        model.addAttribute("posts", posts);
        return "posts/posts";
    }


    @ResponseBody
    @GetMapping("/{boardPath}/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        Post post = postService.findPost(postId).orElse(null);
        if (post == null) { // Not Found, TODO: 예외 처리
            return null;
        }
        return PostResponse.build(post);
    }


    @PostMapping
    public PostResponse addPost(@PathVariable Long boardId, @RequestBody PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
//                .member(postRequest.getMember())
                .build();
        Post newPost = postService.addPost(post);
        return PostResponse.build(newPost);
    }

    private static BoardType getBoardType(String boardPath) {
        return BoardType.valueOf(boardPath.toUpperCase());
    }

}
