package com.aplikata.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.aplikata.community.model.Question;

@Mapper
public interface QuestionMapper {
	
	@Insert("insert into question(title, description, gmt_create, gmt_modify, creator, tag) values (#{title},#{description},#{gmtCreate},#{gmtModify},#{creator},#{tag})")
	void insert(Question question);
	
	@Select("select * from question limit #{start}, #{size}")
	List<Question> list(Integer start, Integer size);
	
	@Select("select count(1) from question")
	Integer count();
	
	@Select("select * from question where creator=#{userId} limit #{start}, #{size}")
	List<Question> listByUser(Integer userId, Integer start, Integer size);
	
	@Select("select count(1) from question where creator=#{userId}")
	Integer countByUser(Integer userId);

	@Select("select * from question where id=#{id}")
	Question findById(Integer id);
}
