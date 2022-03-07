package com.aplikata.community.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplikata.community.dto.PaginationDTO;
import com.aplikata.community.dto.QuestionDTO;
import com.aplikata.community.exception.CustomizeErrorCode;
import com.aplikata.community.exception.CustomizeException;
import com.aplikata.community.mapper.QuestionExtMapper;
import com.aplikata.community.mapper.QuestionMapper;
import com.aplikata.community.mapper.UserMapper;
import com.aplikata.community.model.Question;
import com.aplikata.community.model.QuestionExample;
import com.aplikata.community.model.User;

@Service
public class QuestionService {
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private QuestionExtMapper questionExtMapper;

	@Autowired
	private UserMapper userMapper;

	public PaginationDTO<QuestionDTO> list(Integer page, Integer size) {
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();
		Integer count = (int) questionMapper.countByExample(new QuestionExample());
		paginationDTO.setPagination(count, page, size);

		Integer offset = size * (paginationDTO.getPage() - 1);
		List<Question> list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),
				new RowBounds(offset, size));
		List<QuestionDTO> dtoList = new ArrayList<QuestionDTO>();

		for (Question question : list) {
			QuestionDTO dto = new QuestionDTO();
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			BeanUtils.copyProperties(question, dto);
			dto.setUser(user);
			dtoList.add(dto);
		}

		paginationDTO.setData(dtoList);
		return paginationDTO;
	}

	public PaginationDTO<QuestionDTO> list(Integer userId, Integer page, Integer size) {
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();

		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andCreatorEqualTo(userId);
		Integer count = (int) questionMapper.countByExample(questionExample);

		paginationDTO.setPagination(count, page, size);

		Integer offset = size * (paginationDTO.getPage() - 1);
		List<Question> list = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
		List<QuestionDTO> dtoList = new ArrayList<QuestionDTO>();

		for (Question question : list) {
			QuestionDTO dto = new QuestionDTO();
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			BeanUtils.copyProperties(question, dto);
			dto.setUser(user);
			dtoList.add(dto);
		}

		paginationDTO.setData(dtoList);
		return paginationDTO;
	}

	public QuestionDTO findById(Integer id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		if(question == null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		QuestionDTO dto = new QuestionDTO();
		User user = userMapper.selectByPrimaryKey(question.getCreator());
		BeanUtils.copyProperties(question, dto);
		dto.setUser(user);
		return dto;
	}

	public void createOrUpdate(Question question) {
		if (question.getId() == null || question.getId() <= 0) {
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModify(question.getGmtCreate());
			questionMapper.insert(question);
		} else {
			Question updateQuestion = new Question();
			updateQuestion.setGmtModify(System.currentTimeMillis());
			updateQuestion.setTitle(question.getTitle());
			updateQuestion.setDescription(question.getDescription());
			updateQuestion.setTag(question.getTag());
			QuestionExample example = new QuestionExample();
			example.createCriteria().andIdEqualTo(question.getId());
			int update = questionMapper.updateByExampleSelective(updateQuestion, example);
			if(update != 1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
	}
	
	public void addViewAmount(Integer id) {
		Question question = new Question();
		question.setId(id);
		question.setViewCount(1);
		questionExtMapper.addViewAmount(question);
	}
}
