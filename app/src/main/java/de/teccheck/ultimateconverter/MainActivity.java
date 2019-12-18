package de.teccheck.ultimateconverter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Converter> typeList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();

    Spinner spinner1 = null;
    Spinner spinner2 = null;
    EditText editText1 = null;
    EditText editText2 = null;
    ImageButton switchButton = null;
    ImageButton copyButton = null;
    ImageButton pasteButton = null;

    ClipboardManager clipboardManager = null;

    public boolean disableChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.appContext = getApplicationContext();

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        SharedPreferences prefs = getPreferences(0);
        Utils.separator = prefs.getString("separator", Utils.separator);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        switchButton = findViewById(R.id.switchButton);
        copyButton = findViewById(R.id.copyButton);
        pasteButton = findViewById(R.id.pasteButton);

        switchButton.setOnClickListener(this);
        copyButton.setOnClickListener(this);
        pasteButton.setOnClickListener(this);

        typeList = TypeLoader.loadTypes();
        for (Converter conv : typeList){
            nameList.add(conv.getName());
        }

        SpinnerAdapter spinnerAdapter1 = new ArrayAdapter<>(this.getBaseContext(),R.layout.support_simple_spinner_dropdown_item, typeList);

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onTextChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onTextChange();
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        };

        spinner1.setAdapter(spinnerAdapter1);
        spinner2.setAdapter(spinnerAdapter1);

        editText1.addTextChangedListener(textWatcher);
        spinner1.setOnItemSelectedListener(listener);
        spinner2.setOnItemSelectedListener(listener);

        Converter converter1 = (Converter) spinner1.getSelectedItem();
        editText1.setInputType(converter1.getInputType());
    }

    void onTextChange(){
        if(disableChange)
            return;
        if(editText1.getText().toString().equals("")){
            editText2.setText("");
            return;
        }
        Converter converter1 = (Converter) spinner1.getSelectedItem();
        Converter converter2 = (Converter) spinner2.getSelectedItem();

        editText1.setInputType(converter1.getInputType());

        String converted = getString(R.string.converter_error);
        try {
            // Get from first Converter
            String[] conv = converter1.encode(editText1.getText().toString());

            System.out.println(Arrays.toString(conv));

            // Get from second Converter
            converted = converter2.decode(conv);

        }catch (Exception e){
            e.printStackTrace();
        }
        editText2.setText(converted);
    }

    void switchText(){
        AnimatedVectorDrawableCompat drawable = (AnimatedVectorDrawableCompat) switchButton.getDrawable();
        drawable.start();

        disableChange = true;
        String s1 = editText1.getText().toString();
        String s2 = editText2.getText().toString();

        int i1 = spinner1.getSelectedItemPosition();
        int i2 = spinner2.getSelectedItemPosition();

        spinner1.setSelection(i2, true);
        spinner2.setSelection(i1, true);

        editText1.setText(s2);
        editText2.setText(s1);

        disableChange = false;

        onTextChange();
    }

    void copyText(){
        if(clipboardManager == null)
            clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        ClipData clipData = ClipData.newPlainText(getString(R.string.copy_data_label), editText2.getText());
        clipboardManager.setPrimaryClip(clipData);
    }

    void pasteText(){
        if(clipboardManager == null)
            clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if(clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0){
            CharSequence text = clipboardManager.getPrimaryClip().getItemAt(0).getText();
            editText1.setText(text);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == switchButton.getId())
            switchText();
        else if(view.getId() == copyButton.getId())
            copyText();
        else if(view.getId() == pasteButton.getId())
            pasteText();
    }
}