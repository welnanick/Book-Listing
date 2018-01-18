package com.nickwelna.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

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

    //Following methods generated with a plugin
    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.googleBooksUrl);

    }

    protected Book(Parcel in) {

        this.title = in.readString();
        this.author = in.readString();
        this.googleBooksUrl = in.readString();

    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {

        @Override
        public Book createFromParcel(Parcel source) {

            return new Book(source);

        }

        @Override
        public Book[] newArray(int size) {

            return new Book[size];

        }

    };

}
