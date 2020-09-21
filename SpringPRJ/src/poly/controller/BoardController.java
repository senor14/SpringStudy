package poly.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.dto.BoardDTO;
import poly.service.IBoardService;

@Controller
public class BoardController {
	
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "BoardService")
	IBoardService boardService;
	
	@RequestMapping(value="board/boardList")
	public String boardList(ModelMap model) {
		
		log.info("boardList start!!");
		
		List<BoardDTO> rList = boardService.getBoardList();
		
		model.addAttribute("rList", rList);
		
		return "/board/boardList";
	}
	
	@RequestMapping(value="board/newPost")
	public String newPost() {
		
		log.info("newPost start!!");
		
		return "/board/newPost";
	}
	
	@RequestMapping(value="board/doPost")
	public String doPost(HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info("doPost start!!");
		
		String id = "admin";
		
		String post_title = request.getParameter("post_title");
		String post_content = request.getParameter("post_content");
		
		BoardDTO pDTO = new BoardDTO();
		
		pDTO.setReg_id(id);
		pDTO.setPost_title(post_title);
		pDTO.setPost_content(post_content);
		
		int res = boardService.insertPost(pDTO);
		
		String msg;
		String url = "/board/boardList.do";
		
		if(res>0) {
			msg = "등록에 성공했습니다.";
		} else {
			msg = "등록에 실패했습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/redirect";
	}
	
	@RequestMapping(value="/board/boardDetail")
	public String boardDetail(HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info("boardDetail start!!");
		
		String post_no = request.getParameter("no");
		
		BoardDTO pDTO = new BoardDTO();
		pDTO.setPost_no(post_no);
		
		BoardDTO rDTO = boardService.getBoardDetail(pDTO);
		
		if(rDTO == null) {
			model.addAttribute("msg", "존재하지 않는 게시물입니다.");
			model.addAttribute("url", "/board/boardList.do");
			return "/redirect";
		}
		
		model.addAttribute("rDTO", rDTO);
		
		return "/board/boardDetail";
	}
	
	@RequestMapping(value="/board/editPost")
	public String editPost(HttpServletRequest request, ModelMap model) throws Exception {
		log.info("editPost start!!");
		
		String post_no = request.getParameter("no");
		
		BoardDTO pDTO = new BoardDTO();
		pDTO.setPost_no(post_no);
		
		BoardDTO rDTO = boardService.getBoardDetail(pDTO);
		
		if(rDTO == null) {
			model.addAttribute("msg", "존재하지 않는 게시물입니다.");
			model.addAttribute("url", "/board/boardList.do");
			return "/redirect";
		}
		
		model.addAttribute("rDTO", rDTO);
		
		return "/board/editPost";
	}
	
	@RequestMapping(value="board/doEditPost")
	public String doEditPost(HttpServletRequest request, ModelMap model) throws Exception {
		
		String post_title = request.getParameter("post_title");
		String post_content = request.getParameter("post_content");
		String post_no = request.getParameter("post_no");
		
		BoardDTO pDTO = new BoardDTO();
		
		pDTO.setPost_title(post_title);
		pDTO.setPost_content(post_content);
		pDTO.setPost_no(post_no);
		
		int res = boardService.updatePost(pDTO);
		
		String msg;
		String url = "/board/boardList.do";
		
		if(res>0) {
			msg = "편집에 성공했습니다.";
		} else {
			msg = "편집에 실패했습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/redirect";
	}
	
	@RequestMapping(value="/board/deletePost")
	public String deletePost(HttpServletRequest request, ModelMap model) throws Exception {
		log.info("deletePost start!!");
		
		String post_no = request.getParameter("no");
		
		BoardDTO pDTO = new BoardDTO();
		pDTO.setPost_no(post_no);
		
		int res = boardService.deletePost(pDTO);
		
		String msg;
		String url = "/board/boardList.do";
		
		if(res>0) {
			msg = "삭제에 성공했습니다.";
		} else {
			msg = "삭제에 실패했습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/redirect";
	}
}
