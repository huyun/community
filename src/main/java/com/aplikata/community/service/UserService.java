package com.aplikata.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplikata.community.mapper.UserMapper;
import com.aplikata.community.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	public void createOrUpdate(User myUser) {
		User user = userMapper.findByAccountId(myUser.getAccountId());
		if(user == null) {
			myUser.setGmtCreate(System.currentTimeMillis());
			myUser.setGmtModify(myUser.getGmtCreate());
			userMapper.insert(myUser);
		}else {
			user.setGmtModify(System.currentTimeMillis());
			user.setName(myUser.getName());
			user.setAvatarUrl(myUser.getAvatarUrl());
			user.setToken(myUser.getToken());
			userMapper.update(user);
		}
	}
	
	
}
