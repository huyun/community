package com.aplikata.community.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.aplikata.community.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler {

	@ExceptionHandler(Exception.class)
	ModelAndView handle(Throwable ex, Model model, HttpServletRequest request) {
		if(ex instanceof CustomizeException) {
			model.addAttribute("message", ex.getMessage());
		}else {
			model.addAttribute("message", "Please contact system adminstrator!");
		}
		return new ModelAndView("error");
	}
}
