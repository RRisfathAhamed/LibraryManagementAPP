package com.demo.librarymanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the view with the ID id_books
        View booksView = findViewById(R.id.id_books);

        // Set an OnClickListener to that view
        booksView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open the BooksActivity (which you should create)
                Intent intent = new Intent(HomeActivity.this, BooksActivity.class);
                startActivity(intent);
            }
        });
    }
}
