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

public class ListAdapter extends ArrayAdapter <Project> {


    public ListAdapter(Context context, int resource, List <Project> items) {
        super(context, resource, items);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Project getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.document_list_item, null);
        }

        final Project p = getItem(position);

        if (p != null) {
            TextView tname = (TextView) v.findViewById(R.id.document_name);
            ImageView share = (ImageView) v.findViewById(R.id.share);
            ImageView down = (ImageView) v.findViewById(R.id.download);

            if (tname != null) {
                tname.setText(p.getTitle());
                
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Share(p);
                    }
                });
                
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Download(p);
                    }
                });
                
            }


        }

        return v;
    }

    private void Download(Project project) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(project.getDownloadUel()));
        getContext().startActivity(intent);
    }

    private void Share(Project project){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Share Application");
        sendIntent.setType(project.getDownloadUel());
        getContext().startActivity(sendIntent);
    }
}
