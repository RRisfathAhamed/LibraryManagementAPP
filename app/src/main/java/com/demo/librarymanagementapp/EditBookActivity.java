package com.demo.librarymanagementapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {

    private EditText bookIdInput, titleInput, publisherNameInput;
    private DatabaseHelper dbHelper;
    private String originalBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        dbHelper = new DatabaseHelper(this);

        bookIdInput = findViewById(R.id.bookIdInput);
        titleInput = findViewById(R.id.titleInput);
        publisherNameInput = findViewById(R.id.publisherNameInput);

        originalBookId = getIntent().getStringExtra("BOOK_ID");
        Book book = dbHelper.getBookById(originalBookId);

        bookIdInput.setText(book.getBookId());
        titleInput.setText(book.getTitle());
        publisherNameInput.setText(book.getPublisherName());

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    private void updateBook() {
        String newBookId = bookIdInput.getText().toString();
        String newTitle = titleInput.getText().toString();
        String newPublisherName = publisherNameInput.getText().toString();

        if (newBookId.isEmpty() || newTitle.isEmpty() || newPublisherName.isEmpty()) {
            Toast.makeText(EditBookActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (!newBookId.equals(originalBookId) && dbHelper.isBookIdExists(newBookId)) {
            showMessage("Error", "Book ID already exists!");
        } else {
            Book updatedBook = new Book(newBookId, newTitle, newPublisherName);
            dbHelper.updateBook(originalBookId, updatedBook);
            Toast.makeText(EditBookActivity.this, "Book updated successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }

    private void deleteBook() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Book")
                .setMessage("Are you sure you want to delete this book?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteBook(originalBookId);
                        Toast.makeText(EditBookActivity.this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
