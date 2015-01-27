package com.ftfl.icareprofile.model;

public class DietChart {

	String mDietId;
	String profileId ="1";
	String mDietEventName;
	String mDietTime;
	String mDietMenu;
	String mDietDate;
	String mAlarm;

	public String getDietDate() {
		return mDietDate;
	}

	public void setDietDate(String mDietDate) {
		this.mDietDate = mDietDate;
	}
	
	/**
	 * @param eActivityId
	 * @param eActivityDate
	 * @param eActivityTime
	 * @param eDietFoodMenu
	 * @param eDietEventName
	 * @param eSetAlarm
	 */
	
	public DietChart(String eActivityId,
			String eActivityDate, String eActivityTime, String eDietFoodMenu,
			String eDietEventName,String eSetAlarm) {
		super();

		mDietId = eActivityId;
		mDietEventName = eDietEventName;
		mDietTime = eActivityTime;
		mDietMenu = eDietFoodMenu;
		mDietDate = eActivityDate;
		mAlarm = eSetAlarm;
		profileId ="1";
	}

	/**
	 * 
	 * @param eActivityDate
	 * @param eActivityTime
	 * @param eDietFoodMenu
	 * @param eDietEventName
	 * @param eSetAlarm
	 */
	public DietChart(String eActivityDate, String eActivityTime, String eDietFoodMenu,
			String eDietEventName,String eSetAlarm) {
		super();

		
		mDietEventName = eDietEventName;
		mDietTime = eActivityTime;
		mDietMenu = eDietFoodMenu;
		mDietDate = eActivityDate;
		mAlarm = eSetAlarm;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getDietId() {
		return mDietId;
	}

	public void setDietId(String dietId) {
		mDietId = dietId;
	}

	public String getDietEventName() {
		return mDietEventName;
	}

	public String getDietTime() {
		return mDietTime;
	}

	public String getDietMenu() {
		return mDietMenu;
	}
	public String getAlarm() {
		return mAlarm;
	}

}
