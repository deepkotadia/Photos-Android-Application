package model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.photos_android.photos_android43.R;
import com.photos_android.photos_android43.UserHomepage;

import java.io.File;
import java.util.*;

public class ImageAdapterForSearch extends BaseAdapter {

    private Context context;
    private List<Photo> searchResults;

    public ImageAdapterForSearch(Context context, List<Photo> searchResults) {
        this.context = context;
        this.searchResults = searchResults;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.activity_photos_in_searched_list_view, null);

            // set image into imageview
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.searchedImageGridView);

            //String mobile = mobileValues[position];
            Uri imguri = Uri.parse(searchResults.get(position).getphotoPath());
            imageView.setImageURI(imguri);
            //imageView.setImageResource(R.drawable.sample_0);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
