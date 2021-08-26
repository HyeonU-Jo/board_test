package com.test.board.controller;

import com.test.board.domain.repository.BoardRepository;
import com.test.board.dto.BoardDTO;
import com.test.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    private BoardService boardService;
    private BoardRepository boardRepository;

    public BoardController(BoardService boardService, BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    @GetMapping("/")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardDTO> boardDTOList = boardService.getBoardList(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("pageList", pageList);

        return "board/list.html";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDTO boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long id, Model model) {
        BoardDTO boardDTO = boardService.getPost(id);

        model.addAttribute("boardDTO", boardDTO);

        return "board/detail.html";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long id, Model model) {
        BoardDTO boardDTO = boardService.getPost(id);

        model.addAttribute("boardDTO", boardDTO);

        return "board/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BoardDTO boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/";
    }

    @GetMapping("/post/answer/{no}")
    public String answer(@PathVariable("no") Long id, Model model) {
        BoardDTO boardDTO = boardService.getPost(id);

        model.addAttribute("boardDTO", boardDTO);

        return "board/answer.html";
    }

    @PostMapping("/post/answer/{no}")
    public String answerProc(@PathVariable("no") Long id, BoardDTO boardDTO) {
        BoardDTO dto = boardService.getPost(id);
        boardService.savePostCount(dto);
        boardService.savePostAnswer(boardDTO, dto.getGno(), dto.getOno(), dto.getIndent(), dto.getCount());

        return "redirect:/";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long id) {
            boardService.deletePost(id);

        return "redirect:/";
    }

    @GetMapping("board/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDTO> boardDTOList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardDTOList);

        return "board/list.html";
    }
}
