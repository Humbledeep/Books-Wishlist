package com.app.bookwishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity { 
	WishListDBHelper bookDBHelper;
	RecyclerView recyclerView;
	ImageButton addButton;
	EditText addEditText;
	BookAdapter bookAdapter;

	private AppCompatButton appCompatButtonAdd;

	ImageView imageView,imageViewRefresh;
	private TextView textViewAllBooks,textViewReadBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimary));
		}

		bookDBHelper = new WishListDBHelper(this);
		recyclerView = findViewById(R.id.recycler);
		addButton = findViewById(R.id.add_button);
		imageView = findViewById(R.id.user_icon);
		imageViewRefresh = findViewById(R.id.menu_icon);
		addEditText = findViewById(R.id.add_edittext);
		textViewAllBooks = findViewById(R.id.totalBooks);
		textViewReadBooks = findViewById(R.id.readBooks);
		appCompatButtonAdd = findViewById(R.id.addBookButton);

		imageViewRefresh.setVisibility(View.GONE);

		imageViewRefresh.setOnClickListener(v -> {
			fetchBooks();
		});

		appCompatButtonAdd.setOnClickListener(view -> {

			startActivity(new Intent(this,RecordBookActivity.class));
			finish();
		});

		List<Book> books = bookDBHelper.getAllBooks();
		List<Book> books2 =new ArrayList<>();
		for (Book book : books){
			if(book.isRead()){
				books2.add(book);
			}
		}

		if(books.size()>0) {
			textViewAllBooks.setText(String.valueOf(books.size()) + " Books");
			textViewReadBooks.setText(String.valueOf(books2.size()) + " Read Books");
		}

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		bookAdapter =  new BookAdapter(this, books, bookDBHelper);
		recyclerView.setAdapter(bookAdapter);

    }

	public void fetchBooks(){

		List<Book> books = bookDBHelper.getAllBooks();
		List<Book> books2 =new ArrayList<>();

		for (Book book : books){
			if(book.isRead()){
				books2.add(book);
			}
		}
		if(books.size()>0) {
			textViewAllBooks.setText(String.valueOf(books.size()) + " Books");
			textViewReadBooks.setText(String.valueOf(books2.size()) + " Read Books");
		}
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		bookAdapter =  new BookAdapter(this, books, bookDBHelper);
		recyclerView.setAdapter(bookAdapter);

	}
}
