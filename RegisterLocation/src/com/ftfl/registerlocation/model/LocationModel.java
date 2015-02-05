package com.ftfl.registerlocation.model;

public class LocationModel {

	String mHospitalId;
	String mHospitalName;
	String mHospitalAddress;
	String mHospitalLatitude;
	String mHospitalLongitude;
	String mHospitalDescription;
	byte[] mHospitalImage;

	public String getHospitalLongitude() {
		return mHospitalLongitude;
	}

	public void setHospitalDate(String mHospitalDate) {
		this.mHospitalLongitude = mHospitalDate;
	}

	public LocationModel(String eActivityId, String eHospitalName,
			String eHospitalAddress, String eHospitalLatitude,
			String eHospitalLongitude, String eHospitalDescription,
			byte[] eHospitalImage) {
		super();

		mHospitalId = eActivityId;
		mHospitalName = eHospitalName;
		mHospitalAddress = eHospitalAddress;
		mHospitalLatitude = eHospitalLatitude;
		mHospitalLongitude = eHospitalLongitude;
		mHospitalDescription = eHospitalDescription;
		mHospitalImage = eHospitalImage;

	}

	public LocationModel(String eHospitalName, String eHospitalAddress,
			String eHospitalLatitude, String eHospitalLongitude,
			String eHospitalDescription, byte[] eHospitalImage) {
		super();

		mHospitalName = eHospitalName;
		mHospitalAddress = eHospitalAddress;
		mHospitalLatitude = eHospitalLatitude;
		mHospitalLongitude = eHospitalLongitude;
		mHospitalDescription = eHospitalDescription;
		mHospitalImage = eHospitalImage;
	}

	public String getHospitalId() {
		return mHospitalId;
	}

	public void setHospitalId(String dietId) {
		mHospitalId = dietId;
	}

	public String getHospitalName() {
		return mHospitalName;
	}

	public String getHospitalAddress() {
		return mHospitalAddress;
	}

	public String getHospitalLatitude() {
		return mHospitalLatitude;
	}

	public String getHospitalDescription() {
		return mHospitalDescription;
	}

	public byte[] getHospitalImage() {
		return mHospitalImage;
	}

}
