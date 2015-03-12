package com.ftfl.icare.model;

public class MedicalHistory {
	
	public MedicalHistory() {
		
	}
	String mId = "";
	String mDate = "";
	String mDoctorName = "";
	String mPurpose = "";
	String mSuggestion = "";
	String mProfileId = "";
	String mPrescription = "";
	public String getId() {
		return mId;
	}
	public void setId(String mId) {
		this.mId = mId;
	}
	public String getDate() {
		return mDate;
	}
	public void setDate(String mDate) {
		this.mDate = mDate;
	}
	public String getDoctorName() {
		return mDoctorName;
	}
	public void setDoctorName(String mDoctorName) {
		this.mDoctorName = mDoctorName;
	}
	public String getPurpose() {
		return mPurpose;
	}
	public void setPurpose(String mPurpose) {
		this.mPurpose = mPurpose;
	}
	public String getSuggestion() {
		return mSuggestion;
	}
	public void setSuggestion(String mSuggestion) {
		this.mSuggestion = mSuggestion;
	}
	public String getProfileId() {
		return mProfileId;
	}
	public void setProfileId(String mProfileId) {
		this.mProfileId = mProfileId;
	}
	
	
	
	public String getPrescription() {
		return mPrescription;
	}
	public void setPrescription(String mPrescription) {
		this.mPrescription = mPrescription;
	}
	public MedicalHistory( String mId, String mDate, String mDoctorName,
			String mPurpose, String mSuggestion,String mPrescription, String mProfileId) {
		super();
		this.mId = mId;
		this.mDate = mDate;
		this.mDoctorName = mDoctorName;
		this.mPurpose = mPurpose;
		this.mSuggestion = mSuggestion;
		this.mPrescription = mPrescription;
		this.mProfileId = mProfileId;
		 
	}
	public MedicalHistory( String mDate, String mDoctorName, String mPurpose,
			String mSuggestion, String mPrescription, String mProfileId) {
		super();
		this.mDate = mDate;
		this.mDoctorName = mDoctorName;
		this.mPurpose = mPurpose;
		this.mSuggestion = mSuggestion;
		this.mPrescription = mPrescription;
		this.mProfileId = mProfileId;
	}
	
	

}
