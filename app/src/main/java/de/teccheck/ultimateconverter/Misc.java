package de.teccheck.ultimateconverter;

public class Misc {

    public static String separator = " ";

    public static String addSeperator(String[] input) {
        StringBuilder out = new StringBuilder();
        int length = input.length;
        for (int i = 0; i < length; i++) {
            if (i != (length - 1))
                out.append(input[i]).append(Misc.separator);
            else
                out.append(input[i]);
        }
        return out.toString();
    }

    public static String getErrorString(){
        return "can´t convert that :(";
    }
}
