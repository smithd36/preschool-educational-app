/**
 * The Reading activity of the ABC Safari app.
 * Allows users to navigate through a series of short stories and their visual representations.
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
 * This class represents the AlphabetActivity.
 */
public class ReadingActivity extends AppCompatActivity {

    // Data array holding short story text and associated visual representations
    int[][] readingData = {
            {R.string.readingStory1, R.drawable.readingimg1},
            {R.string.readingStory2, R.drawable.readingimg2},
            {R.string.readingStory3, R.drawable.readingimg3},
            {R.string.readingStory4, R.drawable.readingimg4}
    };

    /**
     * Index to keep track of current story being displayed
     */
    private int currentReadingIndex = 0;

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        // Initialize UI components ar run-time
        ImageView imgReading = findViewById(R.id.imgReading);
        TextView txtReading = findViewById(R.id.txtReadingPrompt);
        ImageView icReadingNext = findViewById(R.id.icReadingNext);
        ImageView icReadingPrev = findViewById(R.id.icReadingPrev);
        Button btnReadingBack = findViewById(R.id.btnReadingBack);

        // Initially hide the "Previous" story icon
        icReadingPrev.setVisibility(View.GONE);

        // assign first story and image at runtime
        txtReading.setText(readingData[currentReadingIndex][0]);
        imgReading.setImageResource(readingData[currentReadingIndex][1]);


        // Listener for the "Next" story button
        icReadingNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the currentReadingIndex to display the next story
                if (currentReadingIndex < readingData.length - 1) {
                    currentReadingIndex++;
                } else {
                    // If at the end of the readingData array, navigate to the main activity
                    NavigationUtils.navigateToMainActivity(ReadingActivity.this);
                    return; // Exit the method
                }

                // Update the image and text based on the new story index
                imgReading.setImageResource(readingData[currentReadingIndex][1]);
                txtReading.setText(readingData[currentReadingIndex][0]);

                // Make the "Previous" icon visible
                icReadingPrev.setVisibility(View.VISIBLE);
            }
        });

        // Listener for the "Previous" story button
        icReadingPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrement the currentReadingIndex to display the previous story
                currentReadingIndex = (currentReadingIndex > 0) ? currentReadingIndex - 1 : currentReadingIndex;

                // Update the visibility of the "Previous" button based on the new story index
                icReadingPrev.setVisibility(currentReadingIndex > 0 ? View.VISIBLE : View.GONE);

                // Update the image and text based on the new reading index
                imgReading.setImageResource(readingData[currentReadingIndex][1]);
                txtReading.setText(readingData[currentReadingIndex][0]);
            }
        });

        // Listener for the "Back" button
        btnReadingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use NavigationUtils to navigate back to the main activity
                NavigationUtils.navigateToMainActivity(ReadingActivity.this);
            }
        });
    }
}