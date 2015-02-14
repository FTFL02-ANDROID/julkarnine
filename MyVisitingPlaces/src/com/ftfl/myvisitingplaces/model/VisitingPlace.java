package com.ftfl.myvisitingplaces.model;

public class VisitingPlace {

	String mPlaceId;
	String mPlaceDate;
	String mPlaceTime;
	String mPlaceLatitude;
	String mPlaceLongitude;
	String mPlaceDescription;
	String mPlaceImage;
	String mPlaceName;
	String mPlaceEmail;
	String mPlacePhone;



	public VisitingPlace(String ePlaceId, String ePlaceDate, String ePlaceTime,
			String ePlaceLatitude, String ePlaceLongitude,
			String ePlaceDescription, String ePlaceImage, String eName,
			String eEmail, String ePhone) {
		super();

		mPlaceId = ePlaceId;
		mPlaceDate = ePlaceDate;
		mPlaceTime = ePlaceTime;
		mPlaceLatitude = ePlaceLatitude;
		mPlaceLongitude = ePlaceLongitude;
		mPlaceDescription = ePlaceDescription;
		mPlaceImage = ePlaceImage;
		mPlaceName = eName;
		mPlaceEmail = eEmail;
		mPlacePhone = ePhone;
	}

	public VisitingPlace(String ePlaceDate, String ePlaceTime,
			String ePlaceLatitude, String ePlaceLongitude,
			String ePlaceDescription, String ePlaceImage, String eName,
			String eEmail, String ePhone) {
		super();

		mPlaceDate = ePlaceDate;
		mPlaceTime = ePlaceTime;
		mPlaceLatitude = ePlaceLatitude;
		mPlaceLongitude = ePlaceLongitude;
		mPlaceDescription = ePlaceDescription;
		mPlaceImage = ePlaceImage;
		mPlaceName = eName;
		mPlaceEmail = eEmail;
		mPlacePhone = ePhone;
	}

	public String getPlaceId() {
		return mPlaceId;
	}

	public String getPlaceDate() {
		return mPlaceDate;
	}

	public String getPlaceTime() {
		return mPlaceTime;
	}

	public String getPlaceLatitude() {
		return mPlaceLatitude;
	}

	public String getPlaceDescription() {
		return mPlaceDescription;
	}

	public String getPlaceImage() {
		return mPlaceImage;
	}
	
	public String getPlaceName() {
		return mPlaceName;
	}

	public String getPlaceEmail() {
		return mPlaceEmail ;
	}

	public String getPlacePhone() {
		return mPlacePhone ;
	}

	public String getPlaceLongitude() {
		return mPlaceLongitude;
	}

}
