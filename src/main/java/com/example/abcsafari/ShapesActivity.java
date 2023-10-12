/**
 *
 * The Shapes activity of the ABC Safari app.
 * Allows users to explore different shapes, their names, and visual representations.
 *
 * @author Drey Smith
 * @version 1.0
 * @since 09.06.2023 - Development Began
 * @since 09.20.2023 - Submitted
 */
package com.example.abcsafari;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class represents the ShapesActivity.
 */
public class ShapesActivity extends AppCompatActivity {

    /**
     * Data array containing shape names and image resources
     */
    int[][] shapesData = {
            {R.string.square, R.drawable.square},
            {R.string.circle, R.drawable.circle},
            {R.string.triangle, R.drawable.triangle},
            {R.string.rhombus, R.drawable.rhombus}
    };

    /**
     * Index to keep track of the current shape being displayed
     */
    private int currentShapeIndex = 0;

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);

        // Initialize UI elements at runtime
        ImageView imgShapes = findViewById(R.id.imgShapes);
        TextView txtShapesPrompt = findViewById(R.id.txtShapesPrompt);
        ImageView icShapesNext = findViewById(R.id.icShapesNext);
        ImageView icShapesPrev = findViewById(R.id.icShapesPrev);
        Button btnShapesBack = findViewById(R.id.btnShapesBack);

        // Initially hide the "Previous" shape icon
        icShapesPrev.setVisibility(View.GONE);

        // Set images and text for the first shape
        imgShapes.setImageResource(shapesData[currentShapeIndex][1]);
        txtShapesPrompt.setText(getString(shapesData[currentShapeIndex][0]));

        // Listener for the "Next" shape icon
        icShapesNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the currentShapeIndex to display the next shape
                if (currentShapeIndex < shapesData.length - 1) {
                    currentShapeIndex++;
                } else {
                    // If at the end of the shapes array, navigate to the main activity
                    NavigationUtils.navigateToMainActivity(ShapesActivity.this);
                    return; // Exit the method
                }

                // Update the image and text based on the new shape index
                imgShapes.setImageResource(shapesData[currentShapeIndex][1]);
                txtShapesPrompt.setText(shapesData[currentShapeIndex][0]);

                // Make the "Previous" icon visible
                icShapesPrev.setVisibility(View.VISIBLE);
            }
        });

        // Listener for the "Previous" shape icon
        icShapesPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrement the currentShapeIndex to display the previous shape
                currentShapeIndex = (currentShapeIndex > 0) ? currentShapeIndex - 1 : currentShapeIndex;

                // Update the visibility of the "Previous" button based on the new shape index
                icShapesPrev.setVisibility(currentShapeIndex > 0 ? View.VISIBLE : View.GONE);

                // Update the image and text based on the new shape index
                imgShapes.setImageResource(shapesData[currentShapeIndex][1]);
                txtShapesPrompt.setText(shapesData[currentShapeIndex][0]);
            }
        });

        // Listener for the "Back" button
        btnShapesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use NavigationUtils to navigate back to the main activity
                NavigationUtils.navigateToMainActivity(ShapesActivity.this);
            }
        });
    }
}