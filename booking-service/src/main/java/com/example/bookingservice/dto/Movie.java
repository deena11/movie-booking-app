package com.example.bookingservice.dto;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Movie {
	private int id;
	
	private String name;
	
	private String title;
	
	private List<Cast> cast;
	private int duration;
	private List<Genre> genre;
	private String synopsis;
	private String facts;
	private byte[] trailer;
	private String language;
	private Date releaseDate;
	private byte[] picture;
	private int frequentView;
	public Movie() {
		super();
	}
	public Movie(int id, String name, String title, List<Cast> cast, int duration, List<Genre> genre, String synopsis,
			String facts, byte[] trailer, String language, Date releaseDate, byte[] picture, int frequentView) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.cast = cast;
		this.duration = duration;
		this.genre = genre;
		this.synopsis = synopsis;
		this.facts = facts;
		this.trailer = trailer;
		this.language = language;
		this.releaseDate = releaseDate;
		this.picture = picture;
		this.frequentView = frequentView;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Cast> getCast() {
		return cast;
	}
	public void setCast(List<Cast> cast) {
		this.cast = cast;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public List<Genre> getGenre() {
		return genre;
	}
	public void setGenre(List<Genre> genre) {
		this.genre = genre;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getFacts() {
		return facts;
	}
	public void setFacts(String facts) {
		this.facts = facts;
	}
	public byte[] getTrailer() {
		return trailer;
	}
	public void setTrailer(byte[] trailer) {
		this.trailer = trailer;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public int getFrequentView() {
		return frequentView;
	}
	public void setFrequentView(int frequentView) {
		this.frequentView = frequentView;
	}
	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + ", title=" + title + ", cast=" + cast + ", duration=" + duration
				+ ", genre=" + genre + ", synopsis=" + synopsis + ", facts=" + facts + ", trailer="
				+ Arrays.toString(trailer) + ", language=" + language + ", releaseDate=" + releaseDate + ", picture="
				+ Arrays.toString(picture) + ", frequentView=" + frequentView + "]";
	}
	
	

}
