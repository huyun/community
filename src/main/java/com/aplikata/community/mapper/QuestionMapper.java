package com.aplikata.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.aplikata.community.model.Question;

@Mapper
public interface QuestionMapper {
	
	@Insert("insert into question(title, description, gmt_create, gmt_modify, creator, tag) values (#{title},#{description},#{gmtCreate},#{gmtModify},#{creator},#{tag})")
	void insert(Question question);
}
