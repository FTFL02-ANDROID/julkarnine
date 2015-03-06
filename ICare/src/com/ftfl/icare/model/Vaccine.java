package com.ftfl.icare.model;

public class Vaccine {

	String mId = "";
	String mName = "";
	String mDate = "";
	String mTime = "";
	String mStatus = "";
	String mNotes = "";
	String mProfileId = "";

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
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

	public String getStatus() {
		return mStatus;
	}

	public void setStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public String getNotes() {
		return mNotes;
	}

	public void setNotes(String mNotes) {
		this.mNotes = mNotes;
	}

	public String getProfileId() {
		return mProfileId;
	}

	public void setProfileId(String mProfileId) {
		this.mProfileId = mProfileId;
	}

	public Vaccine(String eId, String eName, String eDate, String eTime,
			String eStatus, String eNotes, String eProfileId) {

		this.mId = eId;
		this.mName = eName;
		this.mDate = eDate;
		this.mTime = eTime;
		this.mStatus = eStatus;
		this.mNotes = eNotes;
		this.mProfileId = eProfileId;
	}

	public Vaccine(String eName, String eDate, String eTime, String eStatus,
			String eNotes, String eProfileId) {

		this.mName = eName;
		this.mDate = eDate;
		this.mTime = eTime;
		this.mStatus = eStatus;
		this.mNotes = eNotes;
		this.mProfileId = eProfileId;
	}

}
