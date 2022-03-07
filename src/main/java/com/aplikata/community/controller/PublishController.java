package com.aplikata.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aplikata.community.dto.QuestionDTO;
import com.aplikata.community.model.Question;
import com.aplikata.community.model.User;
import com.aplikata.community.service.QuestionService;

@Controller
public class PublishController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/publish")
	public String publish() {
		return "publish";
	}

	@GetMapping("/publish/{id}")
	public String edit(@PathVariable(name = "id")Integer id, Model model) {
		QuestionDTO question = questionService.findById(id);
		model.addAttribute("id", id);
		model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
		return "publish";
	}
	
	@PostMapping("/publish")
	public String doPublish(@RequestParam("id") Integer id, @RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("tag") String tag, HttpServletRequest request, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        
        if (StringUtils.isNullOrEmpty(title)) {
            model.addAttribute("error", "title is empty");
            return "publish";
        }
        if (StringUtils.isNullOrEmpty(description)) {
            model.addAttribute("error", "description is empty");
            return "publish";
        }
        if (StringUtils.isNullOrEmpty(tag)) {
            model.addAttribute("error", "tag is empty");
            return "publish";
        }
		
		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			model.addAttribute("error", "User need to login!");
			return "publish";
		}
		Question question = new Question();
		question.setId(id);
		question.setTag(tag);
		question.setTitle(title);
		question.setDescription(description);
		question.setCreator(user.getId());
		questionService.createOrUpdate(question);

		return "redirect:/";
	}
}
