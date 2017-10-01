package com.hg.mylifetomorrow.domain;

import java.io.Serializable;

public class Profile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int depId;
	private String depFName;
	private String depLName;	
	private String depSex;
	private String depDOB;
	private String depTime;
	private String depPlaceOfBirth;
	private String depPlaceLongitudeNE;
	private String depPlaceLatitudeNE;
	private String depPlaceLongitudeSW;
	private String depPlaceLatitudeSW;
	private String relationShipCode;
	private String userName;
	private long createTimestamp;
	private String chartImgData;
	private String chartImgName;
	private String analysis;
	private boolean verified;
	private String shortName;

	
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}

	public String getDepDOB() {
		return depDOB;
	}
	public void setDepDOB(String depDOB) {
		this.depDOB = depDOB;
	}
	public String getDepTime() {
		return depTime;
	}
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}
	public String getDepPlaceOfBirth() {
		return depPlaceOfBirth;
	}
	public void setDepPlaceOfBirth(String depPlaceOfBirth) {
		this.depPlaceOfBirth = depPlaceOfBirth;
	}

	public String getDepSex() {
		return depSex;
	}
	public void setDepSex(String depSex) {
		this.depSex = depSex;
	}
	public String getRelationShipCode() {
		return relationShipCode;
	}
	public void setRelationShipCode(String relationShipCode) {
		this.relationShipCode = relationShipCode;
	}
	public String getDepFName() {
		return depFName;
	}
	public void setDepFName(String depFName) {
		this.depFName = depFName;
	}
	public String getDepLName() {
		return depLName;
	}
	public void setDepLName(String depLName) {
		this.depLName = depLName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getCreateTimestamp() {
		return createTimestamp;
	}
	public void setCreateTimestamp(long createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	public String getChartImgData() {
		return chartImgData;
	}
	public void setChartImgData(String chartImgData) {
		this.chartImgData = chartImgData;
	}
	public String getChartImgName() {
		return chartImgName;
	}
	public void setChartImgName(String chartImgName) {
		this.chartImgName = chartImgName;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getDepPlaceLongitudeNE() {
		return depPlaceLongitudeNE;
	}
	public void setDepPlaceLongitudeNE(String depPlaceLongitudeNE) {
		this.depPlaceLongitudeNE = depPlaceLongitudeNE;
	}
	public String getDepPlaceLatitudeNE() {
		return depPlaceLatitudeNE;
	}
	public void setDepPlaceLatitudeNE(String depPlaceLatitudeNE) {
		this.depPlaceLatitudeNE = depPlaceLatitudeNE;
	}
	public String getDepPlaceLongitudeSW() {
		return depPlaceLongitudeSW;
	}
	public void setDepPlaceLongitudeSW(String depPlaceLongitudeSW) {
		this.depPlaceLongitudeSW = depPlaceLongitudeSW;
	}
	public String getDepPlaceLatitudeSW() {
		return depPlaceLatitudeSW;
	}
	public void setDepPlaceLatitudeSW(String depPlaceLatitudeSW) {
		this.depPlaceLatitudeSW = depPlaceLatitudeSW;
	}
	public String getShortName() {
		return this.shortName;
	}
	public void setShortName(String shortName) {
		if(shortName!=null && !shortName.isEmpty()){
			this.shortName = shortName;
		} else	if(depLName!=null && !depLName.isEmpty() && depFName!=null && !depLName.isEmpty()){
			this.shortName=depLName.substring(0, 1)+""+depFName.substring(0, 1);
		}else{
			this.shortName = "*";
		}
	}


}
