package com.flufighter.brace.entities;

public class Weather {

	private String description;

	private int conditionCode;
	private int temperature;
	private String city;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getConditionCode() {
		return conditionCode;
	}

	public void setConditionCode(int conditionCode) {
		this.conditionCode = conditionCode;
	}

}
