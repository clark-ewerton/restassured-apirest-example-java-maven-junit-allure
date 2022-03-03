package com.clark.client;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.clark.data.factory.ImagesDataFactory;
import com.clark.model.FavouriteBuilder;
import com.clark.specs.FavouriteSpecs;
import com.google.gson.Gson;

import io.restassured.response.Response;

public class FavouriteClient {
	 
	 public Response performFavoriteImageBasedOnImageID(String iMAGE_ID) {
				
			 /** the code below creates the body string to pass into body method of rest assured **/
			Gson gson = new Gson();
			
			FavouriteBuilder rootJson = new FavouriteBuilder()
					.imageid(iMAGE_ID)
					.subId(ImagesDataFactory.getSubId())
					.build();

			String json = gson.toJson(rootJson);
			
			/** **/
		
			return
	         given().
	        	spec(FavouriteSpecs.initialConfiguration()).
	        		body(json).
	         when().
	            post("/favourites").
	            	then()
	            		.log().all().
	            	spec(FavouriteSpecs.validateStatusCodeOK()).
	            		assertThat().body(notNullValue()).
	            		extract().response();
	}
	 
	 public Response validateFavouriteImageBasedOn(String fAVOURITE_ID) {
		 return
			given().
		        	spec(FavouriteSpecs.initialConfiguration()).
		        when().
		            get("/favourites/" + fAVOURITE_ID).
		            	then().
		            	log().all().
		            	spec(FavouriteSpecs.validateStatusCodeOK()).
		            		assertThat().body(notNullValue()).
		            		extract().response();
		}

}
