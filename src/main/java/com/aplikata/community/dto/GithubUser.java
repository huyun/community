package com.aplikata.community.dto;

public class GithubUser {
	private Long id;
	private String name;
	private String bio;
	private String avatar_url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	@Override
	public String toString() {
		return "GithubUser [id=" + id + ", name=" + name + ", bio=" + bio + ", avatar_url=" + avatar_url + "]";
	}

	
}
