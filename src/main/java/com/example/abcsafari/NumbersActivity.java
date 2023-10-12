/**
 * The Numbers activity of the ABC Safari app.
 * Allows users to explore different short stories and their visual representations.
 *
 * @author Drey Smith
 * @version 1.0
 * @since 09.06.2023 - Development Began
 * @since 09.20.2023 - Submitted
 */
package com.example.abcsafari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * This class represents the NumbersActivity.
 */
public class NumbersActivity extends AppCompatActivity {
    private int[] numbersInOrder = {1, 2, 3, 4};
    private int currentIndex = 0;
    /**
     * 2d array to store text and image resource ids
     */
    int[][] numberData = {
            {R.string.one, R.drawable.one},
            {R.string.two, R.drawable.two},
            {R.string.three, R.drawable.three},
            {R.string.four, R.drawable.four}
    };

    /**
     * Track user score throughout activity
     */
    private int score = 0;

    /**
     * Initializes the NumbersActivity.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        // Initialize image view for visual representation of the number
        ImageView numberImageView = findViewById(R.id.imgNumbers);

        // Set up multiple choices for the user to choose the correct number
        Button btnChoice1 = findViewById(R.id.btnNumbers1);
        Button btnChoice2 = findViewById(R.id.btnNumbers2);
        Button btnChoice3 = findViewById(R.id.btnNumbers3);
        Button btnChoice4 = findViewById(R.id.btnNumbers4);

        // Generate random numbers for buttons
        int correctNumber = numbersInOrder[currentIndex];
        // array of size 4 because we have 4 buttons
        int[] randomNumbers = new int[4];
        for (int i = 0; i < randomNumbers.length; i++) {
            if (i == 0) {
                randomNumbers[i] = correctNumber; // set correct answer to an option
            } else {
                int randomNumber;
                do {
                    randomNumber = new Random().nextInt(4) + 1; // Generate a random number between 1 and 4
                } while (randomNumber == correctNumber || contains(randomNumbers, randomNumber));
                randomNumbers[i] = randomNumber;
            }
        }

        // Shuffle the randomNumbers array to randomize the button order for each use of the activity
        shuffleArray(randomNumbers);

        // Assign random numbers to button text
        btnChoice1.setText(String.valueOf(randomNumbers[0]));
        btnChoice2.setText(String.valueOf(randomNumbers[1]));
        btnChoice3.setText(String.valueOf(randomNumbers[2]));
        btnChoice4.setText(String.valueOf(randomNumbers[3]));

        // Set the image with the associated number (first number in the array)
        numberImageView.setImageResource(getDrawableResourceForNumber(numbersInOrder[currentIndex]));

        // Initialize the TextView with the text associated with the first number
        TextView textView = findViewById(R.id.txtNumber);
        int initialTextResourceId = numberData[currentIndex][0];
        textView.setText(getString(initialTextResourceId));

        // Add listeners for each of the four btnChoice buttons
        btnChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(Integer.parseInt(btnChoice1.getText().toString()));
            }
        });

        btnChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(Integer.parseInt(btnChoice2.getText().toString()));
            }
        });

        btnChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(Integer.parseInt(btnChoice3.getText().toString()));
            }
        });

        btnChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(Integer.parseInt(btnChoice4.getText().toString()));
            }
        });

        // Back button listener
        Button btnBack = findViewById(R.id.btnNumbersBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateToMainActivity(NumbersActivity.this);
            }
        });
    }

    /**
     * Checks the user's selected number against the correct number and updates the UI accordingly.
     *
     * @param selectedNumber The number selected by the user.
     */
    private void checkAnswer(int selectedNumber) {
        if (selectedNumber == numbersInOrder[currentIndex]) {
            score++; // Increment score on correct response
        }
        currentIndex++; // Move to the next number regardless of the previous response for accurate scoring

        if (currentIndex < numbersInOrder.length) {
            // Get the text and image resources for the current number
            int textResourceId = numberData[currentIndex][0];
            int imageResourceId = numberData[currentIndex][1];

            // Set the image and text based on the resources
            ImageView numberImageView = findViewById(R.id.imgNumbers);
            numberImageView.setImageResource(imageResourceId);

            // Update the TextView to display the text
            TextView textView = findViewById(R.id.txtNumber);
            textView.setText(getString(textResourceId));
        } else {
            // All numbers displayed
            // Display the score using Toast and floating point for score percentage
            Toast.makeText(this, "Congratulations! You've completed the Numbers activity. Your score was: " + String.format("%.0f%%", ((float) score / numbersInOrder.length) * 100), Toast.LENGTH_LONG).show(); // Using string.format to remove decimal point from the return value
            // Navigate back to MainActivity
            Intent intent = new Intent(NumbersActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish the NumbersActivity
        }
    }

    /**
     * Retrieves the drawable resource ID for the given number.
     *
     * @param number The number for which to retrieve the drawable resource.
     * @return The drawable resource ID.
     */
    private int getDrawableResourceForNumber(int number) {
        switch (number) {
            case 1:
                return R.drawable.one;
            case 2:
                return R.drawable.two;
            case 3:
                return R.drawable.three;
            case 4:
                return R.drawable.four;
            default:
                return R.drawable.one; // Default case will not be used
        }
    }

    /**
     * Checks if a given value exists in an array.
     *
     * @param array The array to check.
     * @param value The value to search for in the array.
     * @return True if the value is found in the array, false otherwise.
     */
    private boolean contains(int[] array, int value) {
        for (int num : array) {
            if (num == value) { // If the array contains the value
                return true;
            }
        }
        return false; // If the array does not contain the value
    }

    /**
     * Shuffles an array in a random order.
     *
     * @param array The array to shuffle.
     */
    private void shuffleArray(int[] array) {
        Random random = new Random(); // Initialize random
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Swap elements at i and the index
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }
}