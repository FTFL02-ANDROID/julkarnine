package com.ftfl.icare.model;

public class ICareProfile {
	String mId = "";
	String mName = "";
	String mGender = "";
	String mDateOfBirth = "";
	String mWeight = "";
	String mHeight = "";
	String mBloodGroup = "";
	String mImage = "";

	/*
	 * set id of the profile
	 */
	public void setID(String eID) {
		mId = eID;
	}

	/*
	 * get id of the activity
	 */
	public String getID() {
		return mId;
	}

	/*
	 * Set a name for icare profile. parameter iName return name
	 */
	public void setName(String eName) {
		mName = eName;
	}

	/*
	 * get name of the icare profile.
	 */
	public String getName() {
		return mName;
	}

	/*
	 * set age of the person
	 */
	public void setGender(String eGender) {
		mGender = eGender;
	}

	/*
	 * get age of the person
	 */
	public String getGender() {
		return mGender;
	}

	/*
	 * set eye color of the person
	 */
	public void setBloodGroup(String eBloodGroup) {
		mBloodGroup = eBloodGroup;
	}

	/*
	 * get eye color of the person
	 */
	public String getBloodGroup() {
		return mBloodGroup;

	}

	/*
	 * set weight
	 */
	public void setWeight(String eWeight) {
		mWeight = eWeight;
	}

	/*
	 * get weight
	 */
	public String getWeight() {
		return mWeight;
	}

	/*
	 * set height
	 */
	public void setHeight(String eHeight) {
		mHeight = eHeight;
	}

	/*
	 * get height
	 */
	public String getHeight() {
		return mHeight;
	}

	/*
	 * set date of birth
	 */
	public void setDateOfBirth(String eDateOfBirth) {
		mDateOfBirth = eDateOfBirth;
	}

	/*
	 * get date of birth
	 */
	public String getDateOfBirth() {
		return mDateOfBirth;
	}

	/*
	 * set special notes
	 */
	public void setImage(String eImage) {
		mImage = eImage;
	}

	/*
	 * get special notes
	 */
	public String getImage() {
		return mImage;
	}

	/*
	 * set empty constructor of this class
	 */
	public ICareProfile() {

	}

	/*
	 * constructor for set value
	 */
	public ICareProfile(String eId, String eName, String eGender,
			String eDateOfBirth, String eHeight, String eWeight,
			String eBloodGroup, String eImage) {
		mId = eId;
		mName = eName;
		mGender = eGender;
		mBloodGroup = eBloodGroup;
		mWeight = eWeight;
		mHeight = eHeight;
		mDateOfBirth = eDateOfBirth;
		mImage = eImage;

	}

}
