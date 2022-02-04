package com.aplikata.community.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.aplikata.community.dto.AccessTokenDTO;
import com.aplikata.community.dto.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {

	public String getAccessToken(AccessTokenDTO dto) {
		MediaType mdType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mdType, JSON.toJSONString(dto));
		Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();

		try (Response response = client.newCall(request).execute()) {
			String string = response.body().string();
			String tokenString = string.split("&")[0].split("=")[1];
			return tokenString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public GithubUser getUser(String accessToken) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.header("Authorization","Bearer "+accessToken)
				.url("https://api.github.com/user")
				.build();

		try {
			Response response = client.newCall(request).execute();
			String string = response.body().string();
			return JSON.parseObject(string, GithubUser.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
