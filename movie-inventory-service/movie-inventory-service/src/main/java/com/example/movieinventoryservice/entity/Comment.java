package com.example.movieinventoryservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String comments;
	private double rating;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="movie_id")
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

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comments=" + comments + ", rating=" + rating + ", movie=" + movie + "]";
	}

	
	
	
}
