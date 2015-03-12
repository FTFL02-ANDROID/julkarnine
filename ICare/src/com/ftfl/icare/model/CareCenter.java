package com.ftfl.icare.model;

public class CareCenter {

	String mId = "";
	String mName = "";
	String mLocation = "";
	String mImage;
	String mProfileId = "";
	public String getImage() {
		return mImage;
	}
	public void setImage(String image) {
		mImage = image;
	}
	
	public CareCenter() {
		
	}
	public String getId() {
		return mId;
	}
	public void setId(String id) {
		mId = id;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public String getLocation() {
		return mLocation;
	}
	public void setLocation(String location) {
		mLocation = location;
	}
	public String getProfileId() {
		return mProfileId;
	}
	public void setProfileId(String profileId) {
		mProfileId = profileId;
	}
	public CareCenter(String mId, String mName, String mLocation,
			String mImage, String mProfileId) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mLocation = mLocation;
		this.mImage = mImage;
		this.mProfileId = mProfileId;
	}
	public CareCenter(String mName, String mLocation, String mImage,
			String mProfileId) {
		super();
		this.mName = mName;
		this.mLocation = mLocation;
		this.mImage = mImage;
		this.mProfileId = mProfileId;
	}

	
	
	
}
