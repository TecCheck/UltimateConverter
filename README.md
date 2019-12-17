# Ultimate Converter
#### A simple, expandable Converter App for Android

### How to Build
* Clone the repo
* Open it with Android Studio
* Press `Play`

### How to add another Convert Method
This is not the verry best way to implement something like that, but I'm just a beginner.

Everything is sent from the encoder to the decoder as an array of hex Strings. You need to create an new `Converter` and add it to list of converters. You can send multiple values at once to the decoder using the String array. You also need to provide a `toString` method and a name. For that I would recomend to use a string from an android resource file.

```java
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
        String out = "";

        for(String s : input){
            out += String.valueOf((char) Integer.parseInt(s, 16));
        }
        return out;
    }

    @Override
    public String getName() {
        return Utils.appContext.getString(R.string.converter_name_ascii);
    }

    public String toString(){
        return getName();
    }
};

list.add(ascii);
```

### Contributing

If want to ask something or if you have an idea you can make an issue.