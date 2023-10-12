/**
 * Utility class for simplified navigation between app activities.
 *
 * @author Drey Smith
 * @version 1.0
 * @since 09.06.2023 - Development Began
 * @since 09.20.2023 - Submitted
 */
package com.example.abcsafari;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * This class represents Navigation Utilities for the project.
 */
public class NavigationUtils {
    public static void navigateToMainActivity(Context context) {
	    // dynamically get context of the current activity
        Intent intent = new Intent(context, MainActivity.class);
	    // start main activity
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}