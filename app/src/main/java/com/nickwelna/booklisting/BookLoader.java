package com.nickwelna.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

/**
 * Created by Nick on 1/17/2018.
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String url;

    public BookLoader(Context context, String url) {

        super(context);
        this.url = url;

    }


    @Override
    public List<Book> loadInBackground() {

        if (url == null) {

            return null;

        }

        // Perform the HTTP request for earthquake data and process the response.
        return QueryUtils.extractBooks(url);

    }


    @Override
    protected void onStartLoading() {

        forceLoad();

    }

}
