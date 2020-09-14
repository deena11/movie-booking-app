package com.example.moviesearch.dto;

public class Screen {
	private int id;
	private String name;
	private Theatre theatre;
	
	private int totalSeatsPerScreen;

	public Screen() {
		super();
	}

	public Screen(int id, String name, Theatre theatre, int totalSeatsPerScreen) {
		super();
		this.id = id;
		this.name = name;
		this.theatre = theatre;
		this.totalSeatsPerScreen = totalSeatsPerScreen;
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

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public int getTotalSeatsPerScreen() {
		return totalSeatsPerScreen;
	}

	public void setTotalSeatsPerScreen(int totalSeatsPerScreen) {
		this.totalSeatsPerScreen = totalSeatsPerScreen;
	}

	@Override
	public String toString() {
		return "Screen [id=" + id + ", name=" + name + ", theatre=" + theatre + ", totalSeatsPerScreen="
				+ totalSeatsPerScreen + "]";
	}

	

	
}
