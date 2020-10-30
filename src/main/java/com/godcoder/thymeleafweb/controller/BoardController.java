package com.godcoder.thymeleafweb.controller;

import com.godcoder.thymeleafweb.domain.Board;
import com.godcoder.thymeleafweb.repository.BoardRepository;
import com.godcoder.thymeleafweb.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model,@PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){
        //Page<Board> boardList = boardRepository.findAll(pageable);
        Page<Board> boardList = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);

        //boardList.getPageable().getPageNumber() : 현재 페이지
        int startPage = Math.max(1, boardList.getPageable().getPageNumber() - 4); //마이너스가 나올 수 있으니 최소값을 0으로 설정
        int endPage = Math.min(boardList.getTotalPages(), boardList.getTotalPages() + 4);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boardList", boardList);

        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            model.addAttribute("board", new Board());
        }else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }

        return "board/form";
    }

    @PostMapping("/form")
    public String formSubmit(@Valid Board board, BindingResult bindingResult) {

        boardValidator.validate(board,bindingResult);

        if (bindingResult.hasErrors()) {
            return "board/form";
        }

        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
