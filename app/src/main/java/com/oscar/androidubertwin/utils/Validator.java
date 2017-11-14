package com.oscar.androidubertwin.utils;

import android.util.Patterns;

/**
 * Created by oscar on 11/10/2017.
 */

public final class Validator {

    public static boolean isValidEmail(String email)
    {
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
