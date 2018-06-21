package com.startedup.base.model.shaadi;


import com.google.gson.annotations.SerializedName;


public class Info{

	@SerializedName("seed")
	private String seed;

	@SerializedName("page")
	private String page;

	@SerializedName("results")
	private String results;

	@SerializedName("version")
	private String version;

	public void setSeed(String seed){
		this.seed = seed;
	}

	public String getSeed(){
		return seed;
	}

	public void setPage(String page){
		this.page = page;
	}

	public String getPage(){
		return page;
	}

	public void setResults(String results){
		this.results = results;
	}

	public String getResults(){
		return results;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}
}