/**
 * The main activity of the ABC Safari app.
 * Allows users to navigate to different learning activities.
 *
 * @author Drey Smith
 * @version 1.0
 * @since 09.06.2023 - Development Began
 * @since 09.20.2023 - Submitted
 */
package com.example.abcsafari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * This class represents the MainActivity.
 */
public class MainActivity extends AppCompatActivity {

    // HashMap to map activity names to their activity classes
    private HashMap<String, Class<?>> activityMap = new HashMap<>();

    /**
     * Initializes the main activity, sets images navigation options at run-time.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the HashMap with activity Mappings
        activityMap.put("Alphabet", AlphabetActivity.class);
        activityMap.put("Numbers", NumbersActivity.class);
        activityMap.put("Reading", ReadingActivity.class);
        activityMap.put("Shapes", ShapesActivity.class);

        // Create a HashMap to store image resources for each activity
        HashMap<String, Integer> imageResourceMap = new HashMap<>();

        // Dynamically set images for each activity
        for(String activityName : activityMap.keySet()) {
            String resourceName = activityName.toLowerCase() + "landingimage";
            int resourceId = getResources().getIdentifier(resourceName, "drawable", getPackageName());
            if(resourceId != 0) {
                imageResourceMap.put(activityName, resourceId);
            }
        }

        // Set the logo image at runtime
        ImageView image = findViewById(R.id.imgLogo);
        image.setImageResource(R.drawable.logo);

        // Set images for each activity at runtime using the activity name mapping
        for(String activityName : activityMap.keySet()) {
            int imageViewId = getResources().getIdentifier("img" + activityName + "Activity", "id", getPackageName());
            if(imageViewId != 0 && imageResourceMap.containsKey(activityName)) {
                image = findViewById(imageViewId);
                image.setImageResource(imageResourceMap.get(activityName));
            }
        }
    }

    /*                                    MENU SECTION                                            */
    /**
     * Creates the options menu in the action bar.
     *
     * @param menu The options menu.
     * @return True if the menu is successfully created, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activities_menu, menu);
        return true;
    }

    /**
     * Handles a menu item selection.
     *
     * @param item The selected menu item.
     * @return True if the item selection is handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        // HashMap to map menu item IDs to target activities
        HashMap<Integer, Class<?>> menuItemMap = new HashMap<>();
        menuItemMap.put(R.id.choiceAlphabet, AlphabetActivity.class);
        menuItemMap.put(R.id.choiceNumbers, NumbersActivity.class);
        menuItemMap.put(R.id.choiceReading, ReadingActivity.class);
        menuItemMap.put(R.id.choiceShapes, ShapesActivity.class);

        // Check if the selected menu item ID exists in the map
        if (menuItemMap.containsKey(itemId)) {
            // Handle the selection
            Class<?> targetActivity = menuItemMap.get(itemId);
            Intent intent = new Intent(this, targetActivity);
            startActivity(intent);
            return true;
        }

        // Default case
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles clicks on image views to navigate to specific activities.
     *
     * @param view The view that was clicked.
     */
    public void onImageSelected(View view) {
        // get selected option based on view tag
        String selectedOption = view.getTag().toString();

        // check if the selection exists
        if(activityMap.containsKey(selectedOption)) {
            // retrieve the class
            Class<?> selectedActivity = activityMap.get(selectedOption);

            Intent intent = new Intent(this, selectedActivity);
            startActivity(intent);
        }
    }
}