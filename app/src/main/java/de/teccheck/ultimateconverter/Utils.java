package de.teccheck.ultimateconverter;

import android.content.Context;

public class Utils {

    public static Context appContext = null;

    public static String separator = " ";

    public static String addSeperator(String[] input) {
        StringBuilder out = new StringBuilder();
        int length = input.length;
        for (int i = 0; i < length; i++) {
            if (i != (length - 1))
                out.append(input[i]).append(Utils.separator);
            else
                out.append(input[i]);
        }
        return out.toString();
    }
}
