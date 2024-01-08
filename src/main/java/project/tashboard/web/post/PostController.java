package project.tashboard.web.post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.tashboard.domain.board.Board;
import project.tashboard.domain.board.BoardType;
import project.tashboard.domain.member.Member;
import project.tashboard.domain.post.Post;
import project.tashboard.domain.post.form.PostAddForm;
import project.tashboard.domain.post.form.PostUpdateForm;
import project.tashboard.web.member.MemberService;
import project.tashboard.web.member.SessionConst;

import java.util.List;

import static project.tashboard.domain.board.BoardLists.boardList;

@Controller
@RequestMapping("/posts/{boardPath}")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final MemberService memberService;


    // 특정 board_id를 갖는 post들을 조회하여 화면에 표시
    @GetMapping()
    public String getBoardPosts(@PathVariable String boardPath, Model model) {
        BoardType boardType = getBoardType(boardPath);
        List<Post> posts = postService.findBoardPosts(boardType);
        model.addAttribute("board", getBoard(boardPath));
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/add")
    public String addForm(@PathVariable String boardPath, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("post", new PostAddForm(loginMember));
        model.addAttribute("board", getBoard(boardPath));
        return "posts/addForm";
    }


    @PostMapping("/add")
    public String addPost(@PathVariable String boardPath,
                          @Validated @ModelAttribute("post") PostAddForm form,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        log.info("form={}", form);

        //특정 필드 예외가 아닌 전체 예외
//        if (form.getPrice() != null && form.getQuantity() != null) {
//            int resultPrice = form.getPrice() * form.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
//            }
//        }
        Member member = memberService.findByName(form.getWriterName());
        if(member == null) {
            bindingResult.reject("memberNotFound", "작성자가 존재하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("board", getBoard(boardPath));
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

    @GetMapping("/{postId}")
    public String getPost(@PathVariable String boardPath, @PathVariable Long postId, Model model) {
        Post post = postService.findPost(postId).orElseThrow();
        model.addAttribute("post", post);
        model.addAttribute("boardPath", boardPath);
        return "posts/post";
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable String boardPath, @PathVariable Long postId, Model model) {
        Post post = postService.findPost(postId).orElseThrow();
        PostUpdateForm form = new PostUpdateForm(post);
        model.addAttribute("post", form);
        model.addAttribute("board", getBoard(boardPath));
        return "posts/editForm";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable String boardPath,
                           @PathVariable Long postId,
                           @Validated @ModelAttribute("post") PostUpdateForm form,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        Member member = memberService.findByName(form.getWriterName());
        if(member == null) {
            bindingResult.reject("memberNotFound", "작성자가 존재하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("board", getBoard(boardPath));
            return "posts/editForm";
        }

        //성공 로직
        postService.updatePostWithForm(form);


        redirectAttributes.addAttribute("boardPath", boardPath);
        redirectAttributes.addAttribute("postId", postId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/posts/{boardPath}/{postId}";
    }

    private static BoardType getBoardType(String boardPath) {
        return BoardType.valueOf(boardPath.toUpperCase());
    }
    private static Board getBoard(String boardPath) {
        return boardList.get(getBoardType(boardPath).ordinal());
    }

}
