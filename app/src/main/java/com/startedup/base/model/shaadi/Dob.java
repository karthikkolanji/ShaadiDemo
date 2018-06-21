package com.startedup.base.model.shaadi;


import com.google.gson.annotations.SerializedName;


public class Dob{

	@SerializedName("date")
	private String date;

	@SerializedName("age")
	private String age;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}
}