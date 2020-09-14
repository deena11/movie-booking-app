package com.example.bookingservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int playId;
	private double totalPrice;
	private int userId;
	
	private int seatCount;
	
	
	public Booking() {
		super();
	}


	public Booking(int id, int playId, double totalPrice, int userId, int seatCount) {
		super();
		this.id = id;
		this.playId = playId;
		this.totalPrice = totalPrice;
		this.userId = userId;
		this.seatCount = seatCount;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getPlayId() {
		return playId;
	}


	public void setPlayId(int playId) {
		this.playId = playId;
	}


	public double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getSeatCount() {
		return seatCount;
	}


	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}


	@Override
	public String toString() {
		return "Booking [id=" + id + ", playId=" + playId + ", totalPrice=" + totalPrice + ", userId=" + userId
				+ ", seatCount=" + seatCount + "]";
	}


	
	
	

}
