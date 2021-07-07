package top.noname.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import top.noname.domain.BbsPageVO;
import top.noname.domain.BbsPostVO;
import top.noname.service.BbsPostService;

@Controller
@RequestMapping("/bbs")
public class BbsController {
	private static final Logger log = LoggerFactory.getLogger(BbsController.class);
	
	@Autowired
	private BbsPostService postService;
	
	// 게시글 작성
	@GetMapping("/write")
	public void write() {
		log.info("@@@ bbs/write");
	}
	// 게시글 작성 제출
	@PostMapping("/write")
	public String write(BbsPostVO postVO, RedirectAttributes attr) {
		log.info("@@@ bbs/write submit");
		
		if (postService.writePost(postVO)) {
			attr.addFlashAttribute("result", "success");
		} else {
			attr.addFlashAttribute("result", "failure");
		}
		return "redirect:/bbs";
	}
	
	// 게시글 읽기
	@GetMapping("/read")
	public void read(int num, BbsPageVO pageVO, Model model) {
		log.info("@@@ bbs/read");
		
		model.addAttribute("page", pageVO);
		model.addAttribute("post", postService.readPost(num));
	}
	
	// 게시글 목록 읽기
	@GetMapping("")
	public String list(BbsPageVO pageVO, Model model) {
		log.info("@@@ bbs");
		
		model.addAttribute("list", postService.readPostList(pageVO));
		model.addAttribute("page", pageVO);
		return "bbs/list";
	}
	
	// 게시글 수정
	@GetMapping("/edit")
	public void edit(int num, Model model) {
		log.info("@@@ bbs/edit");
		
		model.addAttribute("post", postService.readPost(num));
	}
	// 게시글 수정 제출
	@PostMapping("/edit")
	public String edit(BbsPostVO postVO, RedirectAttributes attr) {
		log.info("@@@ bbs/edit");
		
		if (postService.editPost(postVO)) {
			attr.addFlashAttribute("result", "success");
		} else {
			attr.addFlashAttribute("result", "failure");
		}
		return "redirect:/bbs/read?num=" + postVO.getNum();
	}
	
	// 게시글 삭제
	@DeleteMapping("/delete")
	@ResponseBody
	public String delete(BbsPostVO postVO) {
		log.info("@@@ bbs/delete");
		
		if (postService.deletePost(postVO)) {
			return "success";
		}
		return "failure";
	}
}