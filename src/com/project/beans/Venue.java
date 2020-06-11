package com.project.beans;


public class Venue {
	
	long venueID;
	String venueName;
	String venueAddress;
	long venuePhNo;
	long capacity;
	String eventType;
	float price;
	byte[] image;
	String imageName;
	
	
	
	
	public long getVenueID() {
		return venueID;
	}
	public void setVenueID(long venueID) {
		this.venueID = venueID;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getVenueAddress() {
		return venueAddress;
	}
	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}
	public long getVenuePhNo() {
		return venuePhNo;
	}
	public void setVenuePhNo(long venuePhNo) {
		this.venuePhNo = venuePhNo;
	}
	public long getCapacity() {
		return capacity;
	}
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	


}
