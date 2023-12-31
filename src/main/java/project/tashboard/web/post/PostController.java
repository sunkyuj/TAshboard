package project.tashboard.web.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.member.Member;
import project.tashboard.domain.post.Post;
import project.tashboard.domain.post.form.PostAddForm;
import project.tashboard.web.member.MemberService;

import java.util.List;

import static project.tashboard.domain.board.BoardLists.boards;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping()
    @ResponseBody
    public List<Post> getPosts() {
        return postService.findPosts();
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


    @GetMapping("/{boardPath}/{postId}")
    @ResponseBody
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


    @GetMapping("/{boardPath}/add")
    public String addForm(Model model) {
        model.addAttribute("post", new PostAddForm());
        return "posts/addForm";
    }


    @PostMapping("/{boardPath}/add")
    public String addPost(@PathVariable String boardPath, @Validated @ModelAttribute("post") PostAddForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드 예외가 아닌 전체 예외
//        if (form.getPrice() != null && form.getQuantity() != null) {
//            int resultPrice = form.getPrice() * form.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
//            }
//        }

        Member member = memberService.findByName(form.getWriterName());
        if(member == null) {
            bindingResult.reject("memberNotFound", new Object[]{form.getWriterName()}, null);
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "posts/addForm";
        }


        //성공 로직
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setContents(form.getContents());
        post.setBoardType(form.getBoardType());
        post.setMember(member);

        Post savedPost = postService.addPost(post);

        redirectAttributes.addAttribute("boardPath", boardPath);
        redirectAttributes.addAttribute("postId", savedPost.getPostId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/posts/{boardPath}/{postId}";
    }

    private static BoardType getBoardType(String boardPath) {
        return BoardType.valueOf(boardPath.toUpperCase());
    }

}
