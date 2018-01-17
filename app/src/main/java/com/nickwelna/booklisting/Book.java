package com.nickwelna.booklisting;

public class Book {

    private String title;
    private String author;
    private String googleBooksUrl;

    public Book(String title, String author, String googleBooksUrl) {

        this.title = title;
        this.author = author;
        this.googleBooksUrl = googleBooksUrl;

    }

    public String getTitle() {

        return title;

    }

    public String getAuthor() {

        return author;

    }

    public String getGoogleBooksUrl() {

        return googleBooksUrl;

    }

}
