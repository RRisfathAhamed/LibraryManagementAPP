package com.demo.librarymanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "library";
    private static final int DB_VERSION = 2; // Updated version number

    // Table names
    private static final String TABLE_BOOK = "Book";
    private static final String TABLE_PUBLISHER = "Publisher";
    private static final String TABLE_BRANCH = "Branch";
    private static final String TABLE_MEMBER = "Member";
    private static final String TABLE_BOOK_AUTHOR = "Book_Author";
    private static final String TABLE_BOOK_COPY = "Book_Copy";
    private static final String TABLE_BOOK_LOAN = "Book_Loan";

    // Common column names
    private static final String COLUMN_BOOK_ID = "BOOK_ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_ACCESS_NO = "ACCESS_NO";
    private static final String COLUMN_BRANCH_ID = "BRANCH_ID";
    private static final String COLUMN_CARD_NO = "CARD_NO";

    // Other column names
    private static final String COLUMN_TITLE = "TITLE";
    private static final String COLUMN_PUBLISHER_NAME = "PUBLISHER_NAME";
    private static final String COLUMN_ADDRESS = "ADDRESS";
    private static final String COLUMN_PHONE = "PHONE";
    private static final String COLUMN_UNPAID_DUES = "UNPAID_DUES";
    private static final String COLUMN_AUTHOR_NAME = "AUTHOR_NAME";
    private static final String COLUMN_DATE_OUT = "DATE_OUT";
    private static final String COLUMN_DATE_DUE = "DATE_DUE";
    private static final String COLUMN_DATE_RETURNED = "DATE_RETURNED";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBookTable = "CREATE TABLE " + TABLE_BOOK + " ("
                + COLUMN_BOOK_ID + " VARCHAR(13) PRIMARY KEY, "
                + COLUMN_TITLE + " VARCHAR(30), "
                + COLUMN_PUBLISHER_NAME + " VARCHAR(20))";
        db.execSQL(createBookTable);
        // Other table creation statements
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        // Drop other tables if they exist
        onCreate(db);
    }

    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, book.getBookId());
        values.put(COLUMN_TITLE, book.getTitle());
        values.put(COLUMN_PUBLISHER_NAME, book.getPublisherName());
        db.insert(TABLE_BOOK, null, values);
        db.close();
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOK, new String[]{COLUMN_BOOK_ID, COLUMN_TITLE, COLUMN_PUBLISHER_NAME}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String bookId = cursor.getString(0);
                String title = cursor.getString(1);
                String publisherName = cursor.getString(2);
                Book book = new Book(bookId, title, publisherName);
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

// Add the following methods to DatabaseHelper.java

    public Book getBookById(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOK, new String[]{COLUMN_BOOK_ID, COLUMN_TITLE, COLUMN_PUBLISHER_NAME},
                COLUMN_BOOK_ID + "=?", new String[]{bookId}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Book book = new Book(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        cursor.close();
        return book;
    }

    public void updateBook(String oldBookId, Book newBook) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, newBook.getBookId());
        values.put(COLUMN_TITLE, newBook.getTitle());
        values.put(COLUMN_PUBLISHER_NAME, newBook.getPublisherName());

        db.update(TABLE_BOOK, values, COLUMN_BOOK_ID + " = ?", new String[]{oldBookId});
        db.close();
    }

    public void deleteBook(String bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOK, COLUMN_BOOK_ID + " = ?", new String[]{bookId});
        db.close();
    }

    public boolean isBookIdExists(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOK, new String[]{COLUMN_BOOK_ID},
                COLUMN_BOOK_ID + "=?", new String[]{bookId}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

}

