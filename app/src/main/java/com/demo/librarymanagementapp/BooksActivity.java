package com.demo.librarymanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_BOOK = 1;
    private static final int REQUEST_CODE_EDIT_BOOK = 2;

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBooks();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BooksActivity.this, AddBookActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_BOOK);
            }
        });
    }

    private void loadBooks() {
        List<Book> bookList = dbHelper.getAllBooks();
        bookAdapter = new BookAdapter(this, bookList, new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(BooksActivity.this, EditBookActivity.class);
                intent.putExtra("BOOK_ID", book.getBookId());
                startActivityForResult(intent, REQUEST_CODE_EDIT_BOOK);
            }
        });
        recyclerView.setAdapter(bookAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD_BOOK || requestCode == REQUEST_CODE_EDIT_BOOK) {
                loadBooks();  // Reload the books from the database
            }
        }
    }
}
