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
package com.clark.e2e;

import static com.clark.data.suite.TestTags.E2E;
import static com.clark.data.support.Utils.removeSpecialCharacters;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import com.clark.client.FavouriteClient;
import com.clark.client.ImagesClient;
import com.clark.data.factory.ImagesDataFactory;

import io.restassured.response.Response;

public class FullE2ETest {
	
	private static ImagesClient imagesClient;
	private static FavouriteClient favouritesClient;
	
	@BeforeClass
    public static void beforeE2e() {
        imagesClient = new ImagesClient();
        favouritesClient = new FavouriteClient();
    }

    @Test
    @Tag(E2E)
    @DisplayName("Should upload a cat's image, validate, favourite it and validate again")
    public void completeSimulation() {
        String IMAGE_ID = getImageIDAfterUploaded();
 
        validateFieldsOfTheImageUploaded(IMAGE_ID);
		
		String FAVOURITE_ID = getFavouriteIDAfterHasFavouritedImage(IMAGE_ID);
		
		validateFieldsOfFavouritedImage(IMAGE_ID, FAVOURITE_ID);

    }

	private void validateFieldsOfFavouritedImage(String IMAGE_ID, String FAVOURITE_ID) {
		Response responseBody = favouritesClient.validateFavouriteImageBasedOn(FAVOURITE_ID);
		
		String ImageId = responseBody.jsonPath().getString("image_id");
		String FavouriteId = responseBody.jsonPath().getString("id");
		String sub_id = responseBody.jsonPath().getString("sub_id");
		
		assertThat(IMAGE_ID).isEqualTo(ImageId);
		assertThat(FAVOURITE_ID).isEqualTo(FavouriteId);
		assertThat(ImagesDataFactory.getSubId()).isEqualTo(sub_id);
	}

	private String getFavouriteIDAfterHasFavouritedImage(String IMAGE_ID) {
		String FAVOURITE_ID = favouritesClient.performFavoriteImageBasedOnImageID(IMAGE_ID).jsonPath().getString("id");
		
		return FAVOURITE_ID;
	}

	private void validateFieldsOfTheImageUploaded(String IMAGE_ID) {
		Response responseBody = imagesClient.validateBreedAndCategoryBasedOnImageID(IMAGE_ID);
		
		String breedId = responseBody.jsonPath().getString("breeds.id");
		String categoriesId = responseBody.jsonPath().getString("categories.id");
		
		assertThat(ImagesDataFactory.getBreedsId()).isEqualTo(removeSpecialCharacters(breedId));
		assertThat(ImagesDataFactory.getCategorysId()).isEqualTo(removeSpecialCharacters(categoriesId));
	}

	private String getImageIDAfterUploaded() {
		String IMAGE_ID = imagesClient.performUploadImageAndReturnImageID().jsonPath().getString("id");
		
		return IMAGE_ID;
	}
}
