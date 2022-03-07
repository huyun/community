package com.aplikata.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aplikata.community.dto.QuestionDTO;
import com.aplikata.community.service.QuestionService;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;

	@GetMapping("/question/{id}")
	public String question(@PathVariable(name="id") Integer id, Model model) {
		QuestionDTO questionDTO = questionService.findById(id);
		questionService.addViewAmount(id);
		model.addAttribute("question", questionDTO);
		return "question";
	}
}
