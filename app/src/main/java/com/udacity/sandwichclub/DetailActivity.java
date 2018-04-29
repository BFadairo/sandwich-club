package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //TODO: ADD COMMENTS FOR EASIER CODE REVIEW
        //Initialize int variables at 1 to keep track of alternate names and ingredients
        int nameCount = 1;
        int ingredientCount = 1;
        //Find the view related to the name of the sandwich
        TextView nameTx = findViewById(R.id.sandwich_name_iv);
        //Set the text of the view to mainName from the @Sandwich object
        nameTx.setText(sandwich.getMainName());
        //Find views related to the placeOfOrigin
        TextView originTx = findViewById(R.id.origin_tv);
        //Set the text of the related view to the placeOfOrigin from the Sandwich object
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            originTx.setText(R.string.unknown_origin);
        } else {
            originTx.setText(sandwich.getPlaceOfOrigin());
        }
        //Append a newLine here to keep consistent with other views
        originTx.append("\n");
        //Find the view related to the description of the sandwich
        TextView descriptionTx = findViewById(R.id.description_tv);
        //Get the description from the sandwich object
        //and set the text of the view to it
        descriptionTx.setText(sandwich.getDescription());
        //Find the view related to the alternate names of the Sandwich
        TextView alsoKnownAsTx = findViewById(R.id.also_known_tv);

        //Check if the alsoKnownAs List is empty
        //If it is set to no alternate names string
        //If not iterate through list and return each value
        if (sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAsTx.setText(R.string.no_alternate_names);
            alsoKnownAsTx.append("\n");
        } else {
            //Use a For loop to iterate through the alsoKnown ArrayList
            //Get each value at position i and append it to the view
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                String known = sandwich.getAlsoKnownAs().get(i);
                //Used to add counts if the amount of names is greater than 1
                //Mostly for to avoid the user from manually counting

                //If the size of the list is greater than 1
                //append the current nameCount and the other known name
                if (sandwich.getAlsoKnownAs().size() > 1) {
                    alsoKnownAsTx.append(nameCount + ". " + known);
                } else {
                    //Just append the other known name
                    alsoKnownAsTx.append(known);
                }

                nameCount++;
                alsoKnownAsTx.append("\n");
            }
        }
        //Find the view related to the ingredients
        TextView ingredientsTx = findViewById(R.id.ingredients_tv);
        //Iterate through the ingredients List
        //Return each ingredient with a count
        for (int j = 0; j < sandwich.getIngredients().size(); j++){
            String ingredients = sandwich.getIngredients().get(j);
            ingredientsTx.append(ingredientCount + ". " + ingredients);
            ingredientCount++;
            ingredientsTx.append("\n");
        }

    }
}
