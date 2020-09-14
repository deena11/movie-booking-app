package com.example.bookingservice.dto;

public class Comment {
	private int id;
	private String comments;
	private double rating;
	private Movie movie;

	public Comment() {
		super();
	}

	public Comment(int id, String comments, double rating, Movie movie) {
		super();
		this.id = id;
		this.comments = comments;
		this.rating = rating;
		this.movie = movie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	
	
}
