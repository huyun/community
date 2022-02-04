package com.aplikata.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aplikata.community.dto.AccessTokenDTO;
import com.aplikata.community.dto.GithubUser;
import com.aplikata.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
	
	@Autowired
	private GithubProvider githubProvider;
	
	@Value("${github.client.id}")
	private String clientId;

	@Value("${github.client.secret}")
	private String clientSecret;

	@Value("${github.redirect.uri}")
	private String url;

	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code,
						   @RequestParam(name="state") String state) {
		AccessTokenDTO dto = new AccessTokenDTO();
		dto.setClient_id(clientId);
		dto.setClient_secret(clientSecret);
		dto.setCode(code);
		dto.setState(state);
		dto.setRedirect_uri(url);
		String token = githubProvider.getAccessToken(dto);
		
		GithubUser user = githubProvider.getUser(token);
		System.out.println(user);
		if (user == null || user.getId() == null)
			return "redirect:/";

//		User myUser = new User();
//		myUser.setName(user.getName());
//		myUser.setAccountId(String.valueOf(user.getId()));
//		myUser.setToken(UUID.randomUUID().toString());
//		myUser.setGmtCreate(System.currentTimeMillis());
//		myUser.setGmtModify(myUser.getGmtCreate());
//		myUser.setAvatarUrl(user.getAvatar_url());
//		userMapper.insert(myUser);
//		response.addCookie(new Cookie("token", myUser.getToken()));

		return "redirect:/";
	}
}
