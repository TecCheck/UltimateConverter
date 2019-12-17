package de.teccheck.ultimateconverter;

import java.util.ArrayList;

public class TypeLoader {

    public static ArrayList<Converter> loadTypes(){

        Converter dec = new Converter() {
            @Override
            public String[] fromInput(String input) {
                String[] split = input.split(Misc.separator);
                String[] out = new String[split.length];

                for(int i = 0; i < split.length; i++){
                    out[i] = Integer.toHexString(Integer.parseInt(split[i]));
                }
                return out;
            }

            @Override
            public String toOutput(String[] input) {
                String[] out = new String[input.length];

                for(int i = 0; i < input.length; i++){
                    out[i] = Integer.toString(Integer.parseInt(input[i],16));
                }

                return Misc.addSeperator(out);
            }

            @Override
            public String getName() {
                return "Decimal";
            }

            public String toString(){
                return getName();
            }
        };

        Converter hex = new Converter() {
            @Override
            public String[] fromInput(String input) {
                return input.split(Misc.separator);
            }

            @Override
            public String toOutput(String[] input) {
                return Misc.addSeperator(input);
            }

            @Override
            public String getName() {
                return "Hexadecimal";
            }

            public String toString(){
                return getName();
            }
        };

        Converter bin = new Converter() {
            @Override
            public String[] fromInput(String input) {
                String[] split = input.split(Misc.separator);
                String[] out = new String[split.length];

                for(int i = 0; i < split.length; i++){
                    out[i] = Integer.toHexString(Integer.parseInt(split[i], 2));
                }
                return out;
            }

            @Override
            public String toOutput(String[] input) {
                String[] out = new String[input.length];

                for(int i = 0; i < input.length; i++){
                    out[i] = Integer.toBinaryString(Integer.parseInt(input[i], 16));
                }

                return Misc.addSeperator(out);
            }

            @Override
            public String getName() {
                return "Binary";
            }

            public String toString(){
                return getName();
            }
        };

        Converter ascii = new Converter() {
            @Override
            public String[] fromInput(String input) {
                char[] chars = input.toCharArray();
                String[] out = new String[chars.length];

                for (int i = 0; i < chars.length; i++){
                    out[i] = Integer.toHexString((int) chars[i]);
                }
                return out;
            }

            @Override
            public String toOutput(String[] input) {
                String out = "";

                for(String s : input){
                    out += String.valueOf((char) Integer.parseInt(s, 16));
                }
                return out;
            }

            @Override
            public String getName() {
                return "Ascii String";
            }

            public String toString(){
                return getName();
            }
        };

        ArrayList<Converter> list = new ArrayList<>();
        list.add(dec);
        list.add(hex);
        list.add(bin);
        list.add(ascii);
        return list;
    }
}
