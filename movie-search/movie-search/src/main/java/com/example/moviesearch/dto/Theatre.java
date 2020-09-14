package com.example.moviesearch.dto;

public class Theatre {
	private int id;
	private String name;
	private Location location;
	private Address address;
	private int noofScreens;
	public Theatre() {
		super();
	}
	public Theatre(int id, String name, Location location, Address address, int noofScreens) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.address = address;
		this.noofScreens = noofScreens;
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
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getNoofScreens() {
		return noofScreens;
	}
	public void setNoofScreens(int noofScreens) {
		this.noofScreens = noofScreens;
	}
	@Override
	public String toString() {
		return "Theatre [id=" + id + ", name=" + name + ", location=" + location + ", address=" + address
				+ ", noofScreens=" + noofScreens + "]";
	}
	
	
}
