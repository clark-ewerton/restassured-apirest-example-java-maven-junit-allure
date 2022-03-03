package com.clark.client;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.io.File;

import com.clark.config.Configuration;
import com.clark.config.ConfigurationManager;
import com.clark.data.factory.ImagesDataFactory;
import com.clark.data.support.Utils;
import com.clark.specs.ImagesSpecs;

import io.restassured.response.Response;

public class ImagesClient {
	
	 public Response performUploadImageAndReturnImageID() {
		 Configuration configuration = ConfigurationManager.getConfiguration();
	        return
	            given().
	                spec(ImagesSpecs.initialConfiguration()).
	                multiPart("file", new File(Utils.getAbsolutePath(configuration.imagePath())), "image/jpg").
	        		formParam("sub_id", ImagesDataFactory.getSubId()).
	        		formParam("breed_ids", ImagesDataFactory.getBreedsId()).
	        		formParam("category_ids", ImagesDataFactory.getCategorysId()).
	            when().
	                post("/images/upload").
	            then().
	            	log().all().
	                spec(ImagesSpecs.validateStatusCodeCreated()).
	                	assertThat().body(notNullValue()).
	                extract().
	                    response();
	    }
	 
	 public Response validateBreedAndCategoryBasedOnImageID(String iMAGE_ID) {
		 	return
			 given().
	        	spec(ImagesSpecs.initialConfiguration()).
	         when().
	            get("/images/" + iMAGE_ID).
	            	then().
	            		log().all().
	            		spec(ImagesSpecs.validateStatusCodeOK()).
	            			assertThat().body(notNullValue()).
	            			extract().
	            			response();
			
		}
	 
}
