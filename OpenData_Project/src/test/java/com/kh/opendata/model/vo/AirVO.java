package com.kh.opendata.model.vo;

public class AirVO {
	
	// 필드부
	private String stationName; // 측정소명
	private String dataTime; // 측정일시
	private String khaiValue; // 통합대기환경수치
	
	private String pm10Value; // 미세먼지농도
	private String so2Value; // 아황산가스농도
	private String co2Value; // 일산화탄소 농도
	private String no2Value; // 이산화질소농도
	private String o3Value; // 오존 농도
	
	// 생성자부
	public AirVO() {}

	public AirVO(String stationName, String dataTime, String khaiValue, String pm10Value, String so2Value,
			String co2Value, String no2Value, String o3Value) {
		super();
		this.stationName = stationName;
		this.dataTime = dataTime;
		this.khaiValue = khaiValue;
		this.pm10Value = pm10Value;
		this.so2Value = so2Value;
		this.co2Value = co2Value;
		this.no2Value = no2Value;
		this.o3Value = o3Value;
	}
	
	// 메소드부
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getKhaiValue() {
		return khaiValue;
	}

	public void setKhaiValue(String khaiValue) {
		this.khaiValue = khaiValue;
	}

	public String getPm10Value() {
		return pm10Value;
	}

	public void setPm10Value(String pm10Value) {
		this.pm10Value = pm10Value;
	}

	public String getSo2Value() {
		return so2Value;
	}

	public void setSo2Value(String co2Value) {
		this.so2Value = so2Value;
	}
	
	public String getCo2Value() {
		return co2Value;
	}

	public void setCo2Value(String co2Value) {
		this.co2Value = co2Value;
	}

	public String getNo2Value() {
		return no2Value;
	}

	public void setNo2Value(String no2Value) {
		this.no2Value = no2Value;
	}

	public String getO3Value() {
		return o3Value;
	}

	public void setO3Value(String o3Value) {
		this.o3Value = o3Value;
	}

	@Override
	public String toString() {
		return "AirVO [stationName=" + stationName + ", dataTime=" + dataTime + ", khaiValue=" + khaiValue
				+ ", pm10Value=" + pm10Value + ", so2Value=" + so2Value + ", co2Value=" + co2Value + ", no2Value=" + no2Value + ", o3Value="
				+ o3Value + "]";
	};

}