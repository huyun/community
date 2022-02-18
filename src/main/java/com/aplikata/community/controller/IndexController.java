package com.aplikata.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aplikata.community.dto.PaginationDTO;
import com.aplikata.community.dto.QuestionDTO;
import com.aplikata.community.service.QuestionService;

@Controller
public class IndexController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/")
	public String index(Model model, 
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) {

		PaginationDTO<QuestionDTO> dto = questionService.list(page, size);
		model.addAttribute("pagination", dto);
		return "index";
	}
}
