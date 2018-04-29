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
            //Retrieve the JSONArray alsoKnownAs and store it in a variable to be iterated
            JSONArray alsoKnownAs = baseName.getJSONArray("alsoKnownAs");
            //Loop through the Array to retrieve each individually value
            for (int i = 0; i < alsoKnownAs.length(); i++){
                aKnownAs.add(alsoKnownAs.get(i).toString());
            }

            JSONArray ingredientsArray = baseSandwichRequest.getJSONArray("ingredients");
            for (int j = 0; j < ingredientsArray.length(); j++){
                ingredients.add(ingredientsArray.get(j).toString());
            }



            String description = baseSandwichRequest.getString("description");
            String placeOfOrigin = baseSandwichRequest.getString("placeOfOrigin");
            String image = baseSandwichRequest.getString("image");

            sandwich = new Sandwich(mainName,aKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (Exception e){
            Log.e(LOG_TAG, "Failed to Parse JSON");
        }
        return sandwich;
    }
}
