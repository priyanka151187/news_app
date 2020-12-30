package com.e.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.e.newsapp.api.APIClient;
import com.e.newsapp.api.APIInterface;
import com.e.newsapp.fragment.Nextfragment;
import com.e.newsapp.model.NewsModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIInterface apiInterface;
    private static String TAG = "MainActivity";
    public static ArrayList<String> tablist = new ArrayList<String>();
    ViewPager viewPager;
    TabLayout tabLayout;
    public static List<NewsModel> Newslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getNewsList();
        viewPager = findViewById(R.id.viewpager);

        tabLayout = findViewById(R.id.tabLayout);

    }

    //Set the data to viewPager and tablayout
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < tablist.size(); i++) {
            adapter.addFrag(new Nextfragment(Newslist.get(i).getUrlToImage(), Newslist.get(i).getTitle(),
                    Newslist.get(i).getPublishedAt(), Newslist.get(i).getUrl(), Newslist.get(i).getDescription()), tablist.get(i));
            viewPager.setAdapter(adapter);
        }

        tabLayout.setupWithViewPager(viewPager);
    }

    //Adapter for viewPAger
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    //Call Api for getting all the data
    public void getNewsList() {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        call = apiInterface.NewsList();
        Log.d(TAG, "Call Main " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "listOfMyMonth " + response.body());
                JsonObject js = response.body();

                if (js.get("articles") != null) {
                    if (js.get("articles").getAsJsonArray() != null) {
                        JsonArray data = js.get("articles").getAsJsonArray();
                        for (int i = 0; i < data.size(); i++) {
                            NewsModel newsModel = new NewsModel();
                            JsonObject js1 = data.get(i).getAsJsonObject();
                            JsonObject js2 = js1.get("source").getAsJsonObject();
                            String name = js2.get("name").getAsString();

                            newsModel.setAuthor(!js1.get("author").isJsonNull() ? js1.get("author").getAsString() : null);
                            newsModel.setDescription(!js1.get("description").isJsonNull() ? js1.get("description").getAsString() : null);
                            newsModel.setTitle(!js1.get("title").isJsonNull() ? js1.get("title").getAsString() : null);
                            newsModel.setUrl(!js1.get("url").isJsonNull() ? js1.get("url").getAsString() : null);
                            newsModel.setUrlToImage(!js1.get("urlToImage").isJsonNull() ? js1.get("urlToImage").getAsString() : null);
                            newsModel.setPublishedAt(!js1.get("publishedAt").isJsonNull() ? js1.get("publishedAt").getAsString() : null);
                            newsModel.setContent(!js1.get("content").isJsonNull() ? js1.get("content").getAsString() : null);
                            newsModel.setAuthor(!js1.get("author").isJsonNull() ? js1.get("author").getAsString() : null);
                            tablist.add(name);
                            Newslist.add(newsModel);
                        }

                        Log.d("tablist", "" + tablist.size());
                    }
                }
                setupViewPager(viewPager);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString() + t);
            }
        });

    }
}