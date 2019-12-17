package de.teccheck.ultimateconverter;

public interface Converter {

    //convert some String to an Array of your Type (like 255 -> ff)
    String[] fromInput(String input);

    //convert some Strings to back to your Type (like ff -> 255)
    String toOutput(String[] input);

    //give your value type a Name (like Hexadecimal)
    String getName();

}
