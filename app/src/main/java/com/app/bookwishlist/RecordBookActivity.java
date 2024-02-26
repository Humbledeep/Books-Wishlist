package com.app.bookwishlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecordBookActivity extends AppCompatActivity {

    WishListDBHelper bookDBHelper;
    private AppCompatButton addButton;

    private EditText editTextName,editTextAuthor,editTextYear;
    private Spinner editTextGenre;
    private RadioGroup radioGroup;
    private Boolean hasBeenRead=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        bookDBHelper = new WishListDBHelper(this);

        editTextName=findViewById(R.id.add_edittext);
        editTextAuthor=findViewById(R.id.add_author);
        editTextGenre=findViewById(R.id.add_genre);
        editTextYear=findViewById(R.id.add_year);
        addButton=findViewById(R.id.buttonAdd);
        radioGroup=findViewById(R.id.radioGroup);


        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Narrative");
        spinnerArray.add("Fiction");
        spinnerArray.add("Novel");
        spinnerArray.add("Mystery");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextGenre.setAdapter(adapter);

        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);

                hasBeenRead= radioButton.getText().toString().equalsIgnoreCase("read");

            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveBook();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }

    void saveBook(){

        String text = editTextName.getText().toString();
        String author = editTextAuthor.getText().toString();
        String genre = editTextGenre.getSelectedItem().toString();
        String year = editTextYear.getText().toString();
        Long idd= new Random(9999).nextLong();


        if (text.isEmpty() || author.isEmpty() || genre.isEmpty() || year.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all fields....", Toast.LENGTH_SHORT).show();

        }else{

            Book book=new Book(idd,text,author,genre,Integer.parseInt(year),hasBeenRead);
            long itemId = bookDBHelper.insertBook(book);

            if (itemId != -1) {
                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
                //finish();
                //addEditText.setText("");
                startActivity(new Intent(this,MainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Failed to add item", Toast.LENGTH_LONG).show();
            }
        }
    }

}
