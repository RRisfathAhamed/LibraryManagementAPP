package com.demo.librarymanagementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SaveBook extends AppCompatActivity {

    private EditText bookIdInput;
    private EditText titleInput;
    private EditText publisherNameInput;
    private TextView saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book); // Replace with your actual layout file name

        bookIdInput = findViewById(R.id.bookIdInput);
        titleInput = findViewById(R.id.titleInput);
        publisherNameInput = findViewById(R.id.publisherNameInput);
        saveButton = findViewById(R.id.submit_area);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });
    }

    private void saveBook() {
        // Here you can implement the logic to save the book data
        // For demonstration purposes, I'll just show a message
        showMessage("Success", "Book data saved successfully");
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
