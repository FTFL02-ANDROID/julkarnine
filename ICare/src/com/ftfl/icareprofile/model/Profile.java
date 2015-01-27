package com.ftfl.icareprofile.model;

public class Profile {

	public String mName;
	public String mFatherName;
	public String mMotherName;
	public String mDateOfBirth;
	public String mWeight;
	public String mHeight;
	public String mEyeColor;
	public String mSpecialComment;

	public Profile(String name, String fateherName, String motherName,
			String dateOfBirth, String weight, String height, String eyeColor,
			String specialComment) {
		super();
		this.mName = name;
		this.mFatherName = fateherName;
		this.mMotherName = motherName;
		this.mDateOfBirth = dateOfBirth;
		this.mWeight = weight;
		this.mHeight = height;
		this.mEyeColor = eyeColor;
		this.mSpecialComment = specialComment;
	}

	public String getName() {
		return mName;
	}

	public String getFateherName() {
		return mFatherName;
	}

	public String getMotherName() {
		return mMotherName;
	}

	public String getDateOfBirth() {
		return mDateOfBirth;
	}

	public String getWeight() {
		return mWeight;
	}

	public String getHeight() {
		return mHeight;
	}

	public String getEyeColor() {
		return mEyeColor;
	}

	public String getSpecialComment() {
		return mSpecialComment;
	}

}
