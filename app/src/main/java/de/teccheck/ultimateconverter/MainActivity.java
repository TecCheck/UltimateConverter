package de.teccheck.ultimateconverter;

import android.content.SharedPreferences;
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

    public boolean disableChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getPreferences(0);
        Misc.separator = prefs.getString("separator", Misc.separator);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        switchButton = findViewById(R.id.switchButton);

        switchButton.setOnClickListener(this);

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

        String converted = Misc.getErrorString();
        try {
            //Get from first Converter
            String[] conv = converter1.encode(editText1.getText().toString());

            System.out.println(Arrays.toString(conv));

            //Get from second Converter
            converted = converter2.decode(conv);

        }catch (Exception ignored){
            ignored.printStackTrace();
        }
        editText2.setText(converted);
    }

    @Override
    public void onClick(View view) {
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
}