package com.fhit.ycall.entity;

public class Enterprise extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -863992753991976496L;
	private int Id;
	private long Uid;
	private String Name;
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
	public long getUid() {
		return Uid;
	}
	public void setUid(int uid) {
		Uid = uid;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
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
