/**
 * The Alphabet Activity for ABC Safari.
 * This activity displays letters of the alphabet, images, and prompts users to spell words associated with each letter.
 *
 * @author Drey Smith
 * @version 1.0
 * @since 09.06.2023: Development Began
 * @since 09.20.2023: Submitted
 */
package com.example.abcsafari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class represents the AlphabetActivity.
 */
public class AlphabetActivity extends AppCompatActivity {
    /**
     * The index to keep track of position in alphabet array.
     */
    private int currentIndex = 0;

    /**
     * The user's score throughout the activity.
     */
    private int score = 0;

    /**
     * Initialize data for each letter, including resources for text, images, descriptions, and questions.
     */
    private int[][] alphabetData = {
            {R.string.a, R.drawable.alligator, R.array.alphabet_descriptions, R.array.alphabet_questions},
            {R.string.b, R.drawable.bear, R.array.alphabet_descriptions, R.array.alphabet_questions},
            {R.string.c, R.drawable.cat, R.array.alphabet_descriptions, R.array.alphabet_questions},
            {R.string.d, R.drawable.dolphin, R.array.alphabet_descriptions, R.array.alphabet_questions}
    };

    /**
     * Declare and initialize fields for necessary UI components.
     */
    private ImageView alphabetImageView;
    private TextView letterTextView;
    private TextView descriptionTextView;
    private TextView questionTextView;
    private EditText spellingEditText;
    private Button submitButton;

    /**
     * Initializes the Alphabet activity, sets images navigation options at run-time.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        // Set up UI components
        submitButton = findViewById(R.id.btnSubmit);
        alphabetImageView = findViewById(R.id.imgAlphabet);
        letterTextView = findViewById(R.id.txtLetter);
        descriptionTextView = findViewById(R.id.txtDescription);
        spellingEditText = findViewById(R.id.spellingEditText);
        questionTextView = findViewById(R.id.txtAlphabetPrompt);

        // Initialize data for the current position in arrays
        int currentLetterIndex = currentIndex;
        int currentLetterResourceId = alphabetData[currentLetterIndex][0];
        int currentImageResourceId = alphabetData[currentLetterIndex][1];
        int currentDescriptionsArrayId = alphabetData[currentLetterIndex][2];
        int currentQuestionsArrayId = alphabetData[currentLetterIndex][3];

        // Set the image and text based on the current letter data
        alphabetImageView.setImageResource(currentImageResourceId);

        // Retrieve the descriptions and questions arrays
        String[] descriptionsArray = getResources().getStringArray(currentDescriptionsArrayId);
        String[] questionsArray = getResources().getStringArray(currentQuestionsArrayId);

        // Display the associated letter and description
        letterTextView = findViewById(R.id.txtLetter);
        letterTextView.setText(getString(currentLetterResourceId));

        descriptionTextView = findViewById(R.id.txtDescription);
        descriptionTextView.setText(descriptionsArray[currentLetterIndex]);

        // Display the associated letter, description, and question
        letterTextView.setText(getString(currentLetterResourceId));
        descriptionTextView.setText(descriptionsArray[currentLetterIndex]);

        // Set the prompt for the response
        questionTextView.setText("Can you spell " + questionsArray[currentLetterIndex].toLowerCase() + "?");

        // Listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user's spelling from the EditText
                String userSpelling = spellingEditText.getText().toString();

                // Check the spelling
                checkAnswer(userSpelling, currentIndex, questionsArray);
            }
        });

        // Back button listener
        Button btnBack = findViewById(R.id.btnAlphabetBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateToMainActivity(AlphabetActivity.this);
            }
        });
    }

    /**
     * Checks the user's spelling against the correct spelling and handles the game logic.
     *
     * @param userSpelling        The spelling entered by the user.
     * @param currentLetterIndex  The index of the current letter.
     * @param questionsArray      The array of correct spellings for each letter.
     */
    private void checkAnswer(String userSpelling, int currentLetterIndex, String[] questionsArray) {
        // Check if the user's spelling is null or empty
        if (userSpelling == null || userSpelling.trim().isEmpty()) {
            // Show a message to inform the user to enter a response
            Toast.makeText(this, "Whoops! Please try to spell the word before submitting.", Toast.LENGTH_SHORT).show();
            return; // exit CheckAnswer method
        }

        // Get the correct spelling from the questionsArray
        String correctSpelling = questionsArray[currentLetterIndex].toLowerCase(); // Convert to lowercase for case-insensitive comparison

        // Check if the user's spelling matches the correct spelling
        if (userSpelling.toLowerCase().equals(correctSpelling)) {
            score++; // Increment score on correct response
        }

        currentIndex++; // Move to the next letter

        // Check if there are more letters to display
        if (currentLetterIndex < alphabetData.length - 1) {
            // Display the next letter's data
            int nextLetterIndex = currentLetterIndex + 1;
            int currentLetterResourceId = alphabetData[nextLetterIndex][0];
            int currentImageResourceId = alphabetData[nextLetterIndex][1];
            String currentDescription = getResources().getStringArray(alphabetData[nextLetterIndex][2])[nextLetterIndex];

            // Set the image and text for the next letter
            alphabetImageView.setImageResource(currentImageResourceId);
            letterTextView.setText(getString(currentLetterResourceId));
            descriptionTextView.setText(currentDescription);

            // Clear the input text field for the next spelling
            spellingEditText.getText().clear();

            // Set the prompt for the next question
            questionTextView.setText("Can you spell " + questionsArray[nextLetterIndex].toLowerCase() + "?");
        } else {
            // All letters displayed
            // Display the score using Toast and navigate back to MainActivity
            Toast.makeText(this, "Congratulations! You've completed the Alphabet activity. Your score was: " + String.format("%.0f%%", ((float) score / alphabetData.length) * 100), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(AlphabetActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish the AlphabetActivity
        }
    }
}