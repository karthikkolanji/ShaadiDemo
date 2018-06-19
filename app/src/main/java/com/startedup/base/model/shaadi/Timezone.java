package com.startedup.base.model.shaadi;


import com.google.gson.annotations.SerializedName;


public class Timezone{

	@SerializedName("offset")
	private String offset;

	@SerializedName("description")
	private String description;

	public void setOffset(String offset){
		this.offset = offset;
	}

	public String getOffset(){
		return offset;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}
}