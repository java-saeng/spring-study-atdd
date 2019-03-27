package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/questions")
public class QnaController {

    @Resource(name = "qnaService")
    private QnaService qnaService;

    @GetMapping("/{id}")
    public String list(@PathVariable long id, Model model) throws Exception {
        model.addAttribute("question", qnaService.findById(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/show")
    public String updateForm(@PathVariable long id, Model model) throws Exception {
        model.addAttribute("question", qnaService.findById(id));
        return "/qna/updateForm";
    }

    @PostMapping("")
    public String create(@LoginUser User user, @Valid Question question) {
        qnaService.create(user, question);
        return "redirect:/questions";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User user, @PathVariable long id, @Valid Question question) {
        qnaService.update(user, id, question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User user, @PathVariable long id) throws Exception {
        qnaService.deleteQuestion(user, id);
        return "redirect:/questions";
    }

}