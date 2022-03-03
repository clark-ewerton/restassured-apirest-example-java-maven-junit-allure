package com.clark.model;

public class FavouriteBuilder {

	private String image_id;
	private String sub_id;

	public FavouriteBuilder imageid(String imageid) {
		this.image_id = imageid;
		return this;
	}

	public FavouriteBuilder subId(String subId) {
		this.sub_id = subId;
		return this;
	}

	public FavouriteBuilder build() {
		FavouriteBuilder favouriteBuilder = new FavouriteBuilder();
		favouriteBuilder.image_id = image_id;
		favouriteBuilder.sub_id = sub_id;
		return favouriteBuilder;
	}
}
