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

import static com.clark.data.suite.TestTags.CONTRACT;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.File;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import com.clark.BaseAPI;
import com.clark.data.factory.ImagesDataFactory;
import com.clark.data.support.Utils;
import com.clark.specs.ImagesSpecs;

public class ImagesContractTest extends BaseAPI {

    @Test
    @Tag(CONTRACT)
    @DisplayName("Should validate the images upload for POST method in v1")
   public void imagesUploadContractOnV1() {
    	
    	given().
        spec(ImagesSpecs.initialConfiguration()).
        multiPart("file", new File(Utils.getAbsolutePath(configuration.imagePath())), "image/jpeg").
		formParam("sub_id", ImagesDataFactory.getSubId()).
		formParam("breed_ids", ImagesDataFactory.getBreedsId()).
		formParam("category_ids", ImagesDataFactory.getCategorysId()).
    when().
        post("/images/upload").
    then().
            body(matchesJsonSchemaInClasspath("schemas/images_upload_v1_schema.json"));
    }

}
