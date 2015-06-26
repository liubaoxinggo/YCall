package com.fhit.ycall.entity;

public class Enterprise extends BaseEntity {

	private int Id;
	private int Uid;
	private String name;
	private String HomePage;
	private String Tags;
	public Enterprise() {
		super();
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getUid() {
		return Uid;
	}
	public void setUid(int uid) {
		Uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHomePage() {
		return HomePage;
	}
	public void setHomePage(String homePage) {
		HomePage = homePage;
	}
	public String getTags() {
		return Tags;
	}
	public void setTags(String tags) {
		Tags = tags;
	}
	
	
}
