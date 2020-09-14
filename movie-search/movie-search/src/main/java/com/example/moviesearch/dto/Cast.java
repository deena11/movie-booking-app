package com.example.moviesearch.dto;

import java.util.Arrays;

public class Cast {
	private int id;
    private String name;
    private String role;
    private byte[] picture;
	public Cast() {
		super();
	}
	public Cast(String name, String role, byte[] picture) {
		super();
		this.name = name;
		this.role = role;
		this.picture = picture;
	}
	public Cast(int id, String name, String role, byte[] picture) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.picture = picture;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "Cast [id=" + id + ", name=" + name + ", role=" + role + ", picture=" + Arrays.toString(picture) + "]";
	}

	
}
