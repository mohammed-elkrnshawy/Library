package com.example.a3zt.documentation.Classes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a3zt.documentation.R;

import java.util.List;

public class CommentAdapter extends ArrayAdapter <Comment> {


    public CommentAdapter(Context context, int resource, List <Comment> items) {
        super(context, resource, items);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Comment getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.comments_list_item, null);
        }

        final Comment p = getItem(position);

        if (p != null) {
            TextView tname = (TextView) v.findViewById(R.id.nametv);
            TextView tcomment = (TextView) v.findViewById(R.id.commenttv);

            if (tname != null) {
                tname.setText(p.getName());
                tcomment.setText(p.getComment());
            }


        }

        return v;
    }

}
