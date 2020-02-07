package com.azhar.youtubeapi.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.azhar.youtubeapi.R;
import com.azhar.youtubeapi.databinding.ActivityMainBinding;
import com.azhar.youtubeapi.model.Item;
import com.azhar.youtubeapi.model.Videos;
import com.azhar.youtubeapi.presenter.YoutubePresenter;
import com.azhar.youtubeapi.adapter.MainAdapter;
import com.azhar.youtubeapi.utils.DBHelper;
import com.azhar.youtubeapi.view.ListViewHolder;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    String OBJECT = "OBJECT";
    String CHANNELID = "YOUR CHANNEL ID";
    ActivityMainBinding binding;
    YoutubePresenter youtubePresenter;
    DBHelper simpleDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        simpleDB = new DBHelper(this);
        youtubePresenter = new YoutubePresenter(this);
        youtubePresenter.addObserver(this);
        youtubePresenter.getVideos(CHANNELID);

        setToolbar();
        setCacheData();
    }

    void setCacheData(){
        Videos videos = simpleDB.getObject(OBJECT, Videos.class);
        if (videos != null){
            setListVideos(videos.getItems());
        }
    }

    void setToolbar(){
        setSupportActionBar(binding.toolbar);
    }

    void setListVideos(List<Item> items){
        binding.progressbar.setVisibility(View.GONE);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        MainAdapter<Item, ListViewHolder> adapter;

        adapter = new MainAdapter<Item, ListViewHolder>(R.layout.list_video, ListViewHolder.class, Item.class, items) {
            @Override
            public void bindView(ListViewHolder holder, Item model, int position) {
                holder.onBind(MainActivity.this, model);
            }
        };

        binding.list.setLayoutManager(manager);
        binding.list.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        youtubePresenter.deleteObserver(this);
        super.onDestroy();
    }

    @Override
    public void update(Observable observable, Object o) {

        if (o == null){
            Toast.makeText(this, "Koneksi gagal!", Toast.LENGTH_LONG).show();
            return;
        }

        Videos videos = (Videos) o;
        simpleDB.putObject(OBJECT, videos);
        setListVideos(videos.getItems());
    }
}