package com.demo.librarymanagementapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private EditText bookIdInput, titleInput, publisherNameInput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        dbHelper = new DatabaseHelper(this);

        bookIdInput = findViewById(R.id.bookIdInput);
        titleInput = findViewById(R.id.titleInput);
        publisherNameInput = findViewById(R.id.publisherNameInput);

        findViewById(R.id.submit_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookId = bookIdInput.getText().toString();
                String title = titleInput.getText().toString();
                String publisherName = publisherNameInput.getText().toString();

                if (bookId.isEmpty() || title.isEmpty() || publisherName.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (dbHelper.isBookIdExists(bookId)) {
                    showMessage("Error", "Book ID already exists!");
                } else {
                    dbHelper.addBook(new Book(bookId, title, publisherName));
                    Toast.makeText(AddBookActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
