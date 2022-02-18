package com.aplikata.community.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GithubUser {
	private Long id;
	private String name;
	private String bio;
	private String avatarUrl;

}
