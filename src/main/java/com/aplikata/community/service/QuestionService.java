package com.aplikata.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplikata.community.dto.PaginationDTO;
import com.aplikata.community.dto.QuestionDTO;
import com.aplikata.community.mapper.QuestionMapper;
import com.aplikata.community.mapper.UserMapper;
import com.aplikata.community.model.Question;
import com.aplikata.community.model.User;

@Service
public class QuestionService {
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	public PaginationDTO<QuestionDTO> list(Integer page, Integer size){
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();
		Integer count = questionMapper.count();
		paginationDTO.setPagination(count, page, size);
				
		Integer start = size * (paginationDTO.getPage() - 1);
		List<Question> list = questionMapper.list(start, size);
		List<QuestionDTO> dtoList = new ArrayList<QuestionDTO>();
		
		for(Question question: list) {
			QuestionDTO dto = new QuestionDTO();
			User user = userMapper.findById(question.getCreator());
			BeanUtils.copyProperties(question, dto);
			dto.setUser(user);
			dtoList.add(dto);
		}
		
		paginationDTO.setData(dtoList);
		return paginationDTO;
	}
	
	public PaginationDTO<QuestionDTO> list(Integer userId, Integer page, Integer size){
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();
		Integer count = questionMapper.countByUser(userId);
		paginationDTO.setPagination(count, page, size);
				
		Integer start = size * (paginationDTO.getPage() - 1);
		List<Question> list = questionMapper.listByUser(userId, start, size);
		List<QuestionDTO> dtoList = new ArrayList<QuestionDTO>();
		
		for(Question question: list) {
			QuestionDTO dto = new QuestionDTO();
			User user = userMapper.findById(question.getCreator());
			BeanUtils.copyProperties(question, dto);
			dto.setUser(user);
			dtoList.add(dto);
		}
		
		paginationDTO.setData(dtoList);
		return paginationDTO;
	}

	public QuestionDTO findById(Integer id) {
		Question question =  questionMapper.findById(id);
		QuestionDTO dto = new QuestionDTO();
		User user = userMapper.findById(question.getCreator());
		BeanUtils.copyProperties(question, dto);
		dto.setUser(user);
		return dto;
	}
}
