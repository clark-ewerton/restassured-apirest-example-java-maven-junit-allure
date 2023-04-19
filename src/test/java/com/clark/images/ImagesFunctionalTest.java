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
package com.clark.images;

import static com.clark.data.suite.TestTags.FUNCTIONAL;
import static io.restassured.RestAssured.given;

import java.io.File;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import com.clark.BaseAPI;
import com.clark.data.factory.ImagesDataFactory;
import com.clark.data.support.Utils;
import com.clark.specs.ImagesSpecs;

public class ImagesFunctionalTest extends BaseAPI{
	
    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should upload an cat's image with success")
    public void shouldUploadCatImage() {
    	
            given().
                spec(ImagesSpecs.initialConfiguration()).
                multiPart("file", new File(Utils.getAbsolutePath(configuration.imagePath())), "image/jpeg").
        		formParam("sub_id", ImagesDataFactory.getSubId()).
        		formParam("breed_ids", ImagesDataFactory.getBreedsId()).
        		formParam("category_ids", ImagesDataFactory.getCategorysId()).
            when().
                post("/images/upload").
            then().
                statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should not upload an image that has no cat's content")
    public void shouldNotUploadDogImage() {

    	given().
            spec(ImagesSpecs.initialConfiguration()).
            multiPart("file", new File(configuration.imageNotCatPath()), "image/png").
    		formParam("sub_id", ImagesDataFactory.getSubId()).
    		formParam("breed_ids", ImagesDataFactory.getBreedsId()).
    		formParam("category_ids", ImagesDataFactory.getCategorysId()).
        when().
            post("/images/upload").
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    
    @Test
    @Tag(FUNCTIONAL)
    @DisplayName("Should not upload an image which form data is incomplete")
    public void shouldNotUploadCatImageWithIncompleteFormData() {
    	
        given().
            spec(ImagesSpecs.initialConfiguration()).
            multiPart("file", configuration.imageNotCatPath()).
    		formParam("sub_id", ImagesDataFactory.getSubId()).
    		formParam("breed_ids", ImagesDataFactory.getBreedsId()).
    		formParam("category_ids", ImagesDataFactory.getCategorysId()).
        when().
            post("/images/upload").
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
