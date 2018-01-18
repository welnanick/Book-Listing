package com.nickwelna.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOK_LOADER_ID = 1;

    private BookAdapter bookAdapter;
    private TextView emptyView;
    private ProgressBar progressBar;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = findViewById(R.id.book_list);

        progressBar = findViewById(R.id.progress_bar);
        searchBar = findViewById(R.id.search_bar);

        emptyView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyView);

        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(bookAdapter);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Book currentBook = bookAdapter.getItem(position);

                Uri bookUri = Uri.parse(currentBook.getGoogleBooksUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                startActivity(websiteIntent);

            }

        });

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {

                    progressBar.setVisibility(View.VISIBLE);
                    emptyView.setText("");
                    getLoaderManager().initLoader(BOOK_LOADER_ID, null, MainActivity.this);

                } else {

                    emptyView.setText(R.string.no_connection);

                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);

            }

        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        String query = searchBar.getText().toString();
        StringBuilder url = new StringBuilder().append("https://www.googleapis.com/books/v1/volumes?q=").append(query);
        return new BookLoader(this, url.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {

        progressBar.setVisibility(View.GONE);
        emptyView.setText(R.string.no_book_text);

        updateUi(data);

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        bookAdapter.clear();

    }

    private void updateUi(final List<Book> books) {

        bookAdapter.clear();

        if (books != null && !books.isEmpty()) {

            bookAdapter.addAll(books);

        }

    }

}
