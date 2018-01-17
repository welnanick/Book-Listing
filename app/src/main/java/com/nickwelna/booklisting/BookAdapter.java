package com.nickwelna.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, @NonNull List<Book> objects) {

        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        Book book = getItem(position);

        TextView bookTitle = convertView.findViewById(R.id.book_title);
        bookTitle.setText(book.getTitle());

        TextView bookAuthor = convertView.findViewById(R.id.book_author);
        bookAuthor.setText(book.getAuthor());

        return convertView;

    }

}