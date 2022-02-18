package com.aplikata.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aplikata.community.mapper.QuestionMapper;
import com.aplikata.community.model.Question;
import com.aplikata.community.model.User;

@Controller
public class PublishController {

	@Autowired
	private QuestionMapper questionMapper;

	@GetMapping("/publish")
	public String publish() {
		return "publish";
	}

	@PostMapping("/publish")
	public String doPublish(@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("tag") String tag, HttpServletRequest request, Model model) {

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

		Question question = new Question();
		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			model.addAttribute("error", "User need to login!");
			return "publish";
		}
		question.setTag(tag);
		question.setTitle(title);
		question.setDescription(description);
		question.setCreator(user.getId());
		question.setGmtCreate(System.currentTimeMillis());
		question.setGmtModify(question.getGmtCreate());
		questionMapper.insert(question);

		return "redirect:/";
	}
}
