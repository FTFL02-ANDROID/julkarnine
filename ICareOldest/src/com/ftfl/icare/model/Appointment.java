package com.ftfl.icare.model;

public class Appointment {

	String mId = "";
	String mDrName = "";
	String mDate = "";
	String mTime = "";
	String mPurpose = "";
	String mLocation = "";
	String mAlarm = "";
	String mProfileId = "";

	
	public Appointment() {
		super();
	}

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getAlarm() {
		return mAlarm;
	}

	public void setAlarm(String mAlarm) {
		this.mAlarm = mAlarm;
	}

	public String getDrName() {
		return mDrName;
	}

	public void setDrName(String mName) {
		this.mDrName = mName;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(String mTime) {
		this.mTime = mTime;
	}

	public String getPurpose() {
		return mPurpose;
	}

	public void setPurpose(String mStatus) {
		this.mPurpose = mStatus;
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String mNotes) {
		this.mLocation = mNotes;
	}

	public String getProfileId() {
		return mProfileId;
	}

	public void setProfileId(String mProfileId) {
		this.mProfileId = mProfileId;
	}

	public Appointment(String mId, String mDrName, String mDate, String mTime,
			String mPurpose, String mLocation, String mAlarm, String mProfileId) {
		super();
		this.mId = mId;
		this.mDrName = mDrName;
		this.mDate = mDate;
		this.mTime = mTime;
		this.mPurpose = mPurpose;
		this.mLocation = mLocation;
		this.mAlarm = mAlarm;
		this.mProfileId = mProfileId;
	}

	public Appointment(String mDrName, String mDate, String mTime,
			String mPurpose, String mLocation, String mAlarm, String mProfileId) {
		super();
		this.mDrName = mDrName;
		this.mDate = mDate;
		this.mTime = mTime;
		this.mPurpose = mPurpose;
		this.mLocation = mLocation;
		this.mAlarm = mAlarm;
		this.mProfileId = mProfileId;
	}
	
	

}
