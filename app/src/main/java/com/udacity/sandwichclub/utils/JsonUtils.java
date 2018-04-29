package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**Tag for the Log Messages **/
    private static String LOG_TAG = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) {
        if (TextUtils.isEmpty(json)){
            return null;
        }

        // List that will store the Ingredients for the sandwich
        List<String> ingredients = new ArrayList<>();
        // List that will store the asKnownAs values for the sandwich
        List<String> aKnownAs = new ArrayList<>();
        // List that will store all the information for the sandwich
        Sandwich sandwich = new Sandwich();

        try {
            //Create a JSONObject for the base JSON Response TODO: Make this sound less confusing
            //TODO: ADD COMMENTS FOR EASIER CODE REVIEW
            JSONObject baseSandwichRequest = new JSONObject(json);

            //Create a JSONObject for the the "name" JSON Tree
            JSONObject baseName = baseSandwichRequest.getJSONObject("name");
            //Extract the value located within the "mainName" key
            String mainName = baseName.getString("mainName");
            //Retrieve the JSONArray alsoKnownAs and store
            JSONArray alsoKnownAs = baseName.getJSONArray("alsoKnownAs");
            //Loop through the Array to retrieve each individual value for alsoKnownAs
            for (int i = 0; i < alsoKnownAs.length(); i++){
                //Add the values to the aKnownAs list
                aKnownAs.add(alsoKnownAs.get(i).toString());
            }

            //Retrieve the JSONArray ingredients and store it
            JSONArray ingredientsArray = baseSandwichRequest.getJSONArray("ingredients");
            //Loop through the Array to retrieve each individual value for ingredients
            for (int j = 0; j < ingredientsArray.length(); j++){
                ingredients.add(ingredientsArray.get(j).toString());
            }

            //Retrieve the description from the key: description
            String description = baseSandwichRequest.getString("description");
            //Retrieve the place of origin from the key: placeOfOrigin
            String placeOfOrigin = baseSandwichRequest.getString("placeOfOrigin");
            //Retrieve the image link from the key: image
            String image = baseSandwichRequest.getString("image");

            //Update the Sandwich that was initialized earlier with information pulled from JSON
            sandwich = new Sandwich(mainName,aKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (Exception e){
            Log.e(LOG_TAG, "Failed to Parse JSON");
        }
        return sandwich;
    }
}
