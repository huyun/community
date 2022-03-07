package com.aplikata.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplikata.community.mapper.UserMapper;
import com.aplikata.community.model.User;
import com.aplikata.community.model.UserExample;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public void createOrUpdate(User myUser) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andAccountIdEqualTo(myUser.getAccountId());
		List<User> users = userMapper.selectByExample(userExample);
		if (users == null || users.isEmpty()) {
			myUser.setGmtCreate(System.currentTimeMillis());
			myUser.setGmtModify(myUser.getGmtCreate());
			userMapper.insert(myUser);
		} else {
			User updateUser = new User();
			updateUser.setGmtModify(System.currentTimeMillis());
			updateUser.setAvatarUrl(myUser.getAvatarUrl());
			updateUser.setName(myUser.getName());
			updateUser.setToken(myUser.getToken());
			UserExample example = new UserExample();
			example.createCriteria().andIdEqualTo(users.get(0).getId());
			userMapper.updateByExampleSelective(updateUser, example);
		}
	}

}
