/*
 * MIT License
 *
 * Copyright (c) 2020 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.clark.favourite;

import static com.clark.data.suite.TestTags.CONTRACT;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import com.clark.BaseAPI;
import com.clark.client.ImagesClient;
import com.clark.data.factory.ImagesDataFactory;
import com.clark.model.FavouriteBuilder;
import com.clark.specs.FavouriteSpecs;
import com.google.gson.Gson;

public class FavouriteContractTest extends BaseAPI {
	
	private static ImagesClient imagesClient;
	
	@BeforeClass
    public static void beforeFavouriteFunctionalTest() {
        imagesClient = new ImagesClient();
    }

    @Test
    @Tag(CONTRACT)
    @DisplayName("Should validate the favourite for POST method in v1")
   public void favouritePOSTContractOnV1() {
    	
    	 String IMAGE_ID = getImageIDAfterUploaded();
	    	
    	 /** the code below creates the body string to pass into body method of rest assured **/
			Gson gson = new Gson();
			
			FavouriteBuilder rootJson = new FavouriteBuilder()
					.imageid(IMAGE_ID)
					.subId(ImagesDataFactory.getSubId())
					.build();

			String json = gson.toJson(rootJson);
			
			/** **/
		
	         given().
	        	spec(FavouriteSpecs.initialConfiguration()).
	        		body(json).
	         when().
	            post("/favourites").
	            	then().
            	  body(matchesJsonSchemaInClasspath("schemas/favourite_post_v1_schema.json"));
          
    }
    
    private String getImageIDAfterUploaded() {
		String IMAGE_ID = imagesClient.performUploadImageAndReturnImageID().jsonPath().getString("id");
		
		return IMAGE_ID;
	}

}
