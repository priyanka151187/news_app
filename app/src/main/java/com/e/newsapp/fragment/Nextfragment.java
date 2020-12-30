package com.e.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.newsapp.R;
import com.e.newsapp.RelatedWebPages;
import com.e.newsapp.model.NewsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Nextfragment extends Fragment {

    ImageView imageView;
    TextView title_txt, time_txt, description_txt;
    LinearLayout linear_layout;
    String UrlToImage, Title, time, WebUrl, Description;

    public Nextfragment(String urlToImage, String title, String publishedAt, String webUrl, String description) {
        this.UrlToImage = urlToImage;
        this.Title = title;
        this.time = publishedAt;
        this.WebUrl = webUrl;
        this.Description = description;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nextfragment, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.imageView);
        title_txt = view.findViewById(R.id.title_txt);
        time_txt = view.findViewById(R.id.time_txt);
        description_txt = view.findViewById(R.id.description_txt);
        linear_layout = view.findViewById(R.id.linear_layout);

        //set the value
        Glide.with(getActivity())
                .load(UrlToImage)
                .placeholder(R.drawable.default_image)
                .into(imageView);
        title_txt.setText(Title);
        description_txt.setText(Description);
        time_txt.setText(setDate(time));

        linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RelatedWebPages.class);//send data from one activity to another activity
                intent.putExtra("detailUrl", WebUrl);
                startActivity(intent);
            }
        });
    }

    //setting date
    public String setDate(String date) {
        // String dtStart = "2010-10-15T09:27:37Z";

        String formatedDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date_ = format.parse(date);
            System.out.println(date_.toString());
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy, HH:mm");
            formatedDate = output.format(date_);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }
}
