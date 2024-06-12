package com.demo.librarymanagementapp;

public class Book {
    private String bookId;
    private String title;
    private String publisherName;

    public Book(String bookId, String title, String publisherName) {
        this.bookId = bookId;
        this.title = title;
        this.publisherName = publisherName;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisherName() {
        return publisherName;
    }
}
