package com.minone.bogus.model.dto;

import com.minone.bogus.model.Tweet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTweetCmd {

	@NotBlank
	private String user;

	@NotNull
	private Double temperature;

	@NotNull
	private Double pressure;

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	@NotBlank
	private String message;

	public static CreateTweetCmd build(String user, Double temperature, Double pressure, Double latitude,
			Double longitude, String message) {

		CreateTweetCmd obj = new CreateTweetCmd();

		obj.user = user;
		obj.temperature = temperature;
		obj.pressure = pressure;
		obj.latitude = latitude;
		obj.longitude = longitude;
		obj.message = message;

		return obj;
	}

	public String getUser() {
		return user;
	}

	public Double getTemperature() {
		return temperature;
	}

	public Double getPressure() {
		return pressure;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getMessage() {
		return message;
	}

	public Tweet toModel() {

		Tweet data = new Tweet();

		data.setLatitude(latitude);
		data.setLongitude(longitude);
		data.setUser(user);
		data.setTemperature(temperature);
		data.setPressure(pressure);
		data.setMessage(message);

		return data;
	}
}