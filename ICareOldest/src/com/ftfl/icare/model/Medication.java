package com.ftfl.icare.model;

public class Medication {
	String mId;
	String Name;
	String Date;
	String Time;
	String Purpose;
	String mAlarm;
	String ProfileId = "";

	public String getName() {
		return Name;
	}
	
	

	public Medication() {
		super();
	}



	public Medication(String mId, String name, String date, String time,
			String purpose, String setAlarm, String profileId) {
		super();
		this.mId = mId;
		Name = name;
		Date = date;
		Time = time;
		Purpose = purpose;
		mAlarm = setAlarm;
		ProfileId = profileId;
	}



	public Medication(String name, String date, String time, String purpose,
			String setAlarm, String profileId) {
		super();
		Name = name;
		Date = date;
		Time = time;
		Purpose = purpose;
		mAlarm = setAlarm;
		ProfileId = profileId;
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getPurpose() {
		return Purpose;
	}

	public void setPurpose(String purpose) {
		Purpose = purpose;
	}

	public String getAlarm() {
		return mAlarm;
	}

	public void setAlarm(String setAlarm) {
		mAlarm = setAlarm;
	}

	public String getProfileId() {
		return ProfileId;
	}

	public void setProfileId(String profileId) {
		ProfileId = profileId;
	}

	
}
