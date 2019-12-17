package de.teccheck.ultimateconverter;

public interface Converter {

    //convert some String to an Array of your Type (like 255 -> ff)
    String[] encode(String input);

    //convert some Strings to back to your Type (like ff -> 255)
    String decode(String[] input);

    //give your value type a Name (like Hexadecimal)
    String getName();

}
