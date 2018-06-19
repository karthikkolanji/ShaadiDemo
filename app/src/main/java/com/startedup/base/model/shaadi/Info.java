package com.startedup.base.model.shaadi;


import com.google.gson.annotations.SerializedName;


public class Info{

	@SerializedName("seed")
	private String seed;

	@SerializedName("page")
	private int page;

	@SerializedName("results")
	private int results;

	@SerializedName("version")
	private String version;

	public void setSeed(String seed){
		this.seed = seed;
	}

	public String getSeed(){
		return seed;
	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setResults(int results){
		this.results = results;
	}

	public int getResults(){
		return results;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}
}