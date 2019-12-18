package de.teccheck.ultimateconverter;

import android.text.InputType;

import java.util.ArrayList;

public class TypeLoader {

    public static ArrayList<Converter> loadTypes(){

        Converter dec = new Converter() {
            @Override
            public String[] encode(String input) {
                String[] split = input.split(Utils.separator);
                String[] out = new String[split.length];

                for(int i = 0; i < split.length; i++){
                    out[i] = Integer.toHexString(Integer.parseInt(split[i]));
                }
                return out;
            }

            @Override
            public String decode(String[] input) {
                String[] out = new String[input.length];

                for(int i = 0; i < input.length; i++){
                    out[i] = Integer.toString(Integer.parseInt(input[i],16));
                }

                return Utils.addSeperator(out);
            }

            @Override
            public int getInputType(){
                return InputType.TYPE_CLASS_NUMBER;
            }

            @Override
            public String getName() {
                return Utils.appContext.getString(R.string.converter_name_dec);
            }

            public String toString(){
                return getName();
            }
        };

        Converter hex = new Converter() {
            @Override
            public String[] encode(String input) {
                return input.split(Utils.separator);
            }

            @Override
            public String decode(String[] input) {
                return Utils.addSeperator(input);
            }

            @Override
            public String getName() {
                return Utils.appContext.getString(R.string.converter_name_hex);
            }

            @Override
            public int getInputType(){
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
            }

            public String toString(){
                return getName();
            }
        };

        Converter bin = new Converter() {
            @Override
            public String[] encode(String input) {
                String[] split = input.split(Utils.separator);
                String[] out = new String[split.length];

                for(int i = 0; i < split.length; i++){
                    out[i] = Integer.toHexString(Integer.parseInt(split[i], 2));
                }
                return out;
            }

            @Override
            public String decode(String[] input) {
                String[] out = new String[input.length];

                for(int i = 0; i < input.length; i++){
                    out[i] = Integer.toBinaryString(Integer.parseInt(input[i], 16));
                }

                return Utils.addSeperator(out);
            }

            @Override
            public int getInputType(){
                return InputType.TYPE_CLASS_NUMBER;
            }

            @Override
            public String getName() {
                return Utils.appContext.getString(R.string.converter_name_bin);
            }

            public String toString(){
                return getName();
            }
        };

        Converter ascii = new Converter() {
            @Override
            public String[] encode(String input) {
                char[] chars = input.toCharArray();
                String[] out = new String[chars.length];

                for (int i = 0; i < chars.length; i++){
                    out[i] = Integer.toHexString((int) chars[i]);
                }
                return out;
            }

            @Override
            public String decode(String[] input) {
                StringBuilder out = new StringBuilder();

                for(String s : input){
                    out.append(Integer.parseInt(s, 16));
                }
                return out.toString();
            }

            @Override
            public int getInputType(){
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
            }

            @Override
            public String getName() {
                return Utils.appContext.getString(R.string.converter_name_ascii);
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