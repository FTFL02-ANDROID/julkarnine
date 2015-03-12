package com.ftfl.icare.model;

public class DoctorProfile {

	String mId = "";
	String mName = "";
	String mSpecialization = "";
	String mPhone = "";
	String mEmail = "";
	String mAddress = "";
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

	public String getSpecialization() {
		return mSpecialization;
	}

	public void setSpecialization(String mSpecialization) {
		this.mSpecialization = mSpecialization;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	public String getProfileId() {
		return mProfileId;
	}

	public void setProfileId(String mProfileId) {
		this.mProfileId = mProfileId;
	}
	
	public DoctorProfile() {

	}

	public DoctorProfile(String eId, String eName, String eSpecialization,
			String ePhone, String eEmail, String eAddress, String eProfileId) {

		this.mId = eId;
		this.mName = eName;
		this.mSpecialization = eSpecialization;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
		this.mAddress = eAddress;
		this.mProfileId = eProfileId;
	}

	public DoctorProfile(String eName, String eSpecialization, String ePhone,
			String eEmail, String eAddress, String eProfileId) {

		this.mName = eName;
		this.mSpecialization = eSpecialization;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
		this.mAddress = eAddress;
		this.mProfileId = eProfileId;
	}

}
