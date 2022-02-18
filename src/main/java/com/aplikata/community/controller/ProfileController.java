package com.aplikata.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.aplikata.community.dto.PaginationDTO;
import com.aplikata.community.dto.QuestionDTO;
import com.aplikata.community.model.User;
import com.aplikata.community.service.QuestionService;

@Controller
public class ProfileController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/profile/{action}")
	public String publish(@PathVariable(name = "action") String action, Model model, HttpServletRequest request,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) {
		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			return "redirect:/";
		}

		if ("questions".equals(action)) {
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "My questions");
		} else if ("replies".equals(action)) {
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "My replies");
		}

		PaginationDTO<QuestionDTO> dto = questionService.list(user.getId(), page, size);
		model.addAttribute("pagination", dto);

		return "profile";
	}
}
