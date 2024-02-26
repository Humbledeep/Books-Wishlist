package com.app.bookwishlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.widget.Toast;

public class WishListDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookWishList.db";
    private static final int DATABASE_VERSION = 1;

    public WishListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE wishlist (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, author TEXT,genre TEXT,publication INTEGER, read TEXT);");
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS wishlist;");
        onCreate(db);
    }
	@SuppressLint("Range")
	public List<Book> getAllBooks() {
		List<Book> wishList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM wishlist", null);

		if (cursor.moveToFirst()) {
			do {
				long id = cursor.getLong(cursor.getColumnIndex("_id"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String author = cursor.getString(cursor.getColumnIndex("author"));
				String genre = cursor.getString(cursor.getColumnIndex("genre"));
				int completedInt = cursor.getInt(cursor.getColumnIndex("read"));
				int publication = cursor.getInt(cursor.getColumnIndex("publication"));
				boolean completed = completedInt == 1;

				Book book = new Book(id,title,author,genre,publication,completed);
				//book.setId(id);
				book.setRead(completed);

				wishList.add(book);

			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return wishList;
	}
	public long insertBook(Book book) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put("id", book.getId());
		values.put("title", book.getTitle());
		values.put("author", book.getAuthor());
		values.put("genre", book.getGenre());
		values.put("read", book.isRead() ? 1:0);
		values.put("publication", book.getPublicationYear());
		//values.put("", book.isRead() ? "1" : "0");
		long itemId = db.insert("wishlist", null, values);
		db.close();
		return itemId;


	}

	public int updateBook(long itemId, Book book) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("title", book.getTitle());
		values.put("author", book.getAuthor());
		values.put("genre", book.getGenre());
		//values.put("read", !book.isRead());
		values.put("publication", book.getPublicationYear());
		values.put("read", book.isRead() ? 1 : 0);
		int rowsAffected = db.update("wishlist", values, "_id=?", new String[]{String.valueOf(itemId)});
		db.close();
		return rowsAffected;
	}


	public int updateItemStatus(long itemId, boolean completed) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("read", completed ? 1 : 0);
		int rowsAffected = db.update("wishlist", values, "_id=?", new String[]{String.valueOf(itemId)});
		db.close();
		return rowsAffected;
	}

	public void deleteBook(long bookId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("wishlist", "_id" +" = ?", new String[]{String.valueOf(bookId)});
		db.close();

	}

}
