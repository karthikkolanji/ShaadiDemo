package com.startedup.base.model.shaadi;


import com.google.gson.annotations.SerializedName;


public class Picture{

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("large")
	private String large;

	@SerializedName("medium")
	private String medium;

	public void setThumbnail(String thumbnail){
		this.thumbnail = thumbnail;
	}

	public String getThumbnail(){
		return thumbnail;
	}

	public void setLarge(String large){
		this.large = large;
	}

	public String getLarge(){
		return large;
	}

	public void setMedium(String medium){
		this.medium = medium;
	}

	public String getMedium(){
		return medium;
	}
}