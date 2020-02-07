package com.azhar.youtubeapi.view;

import android.app.Activity;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.youtubeapi.R;
import com.azhar.youtubeapi.databinding.ListVideoBinding;
import com.azhar.youtubeapi.model.Item;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class ListViewHolder extends RecyclerView.ViewHolder {

    private ListVideoBinding binding;

    public ListViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void onBind(final Activity activity, final Item model) {

        Glide.with(activity).asBitmap()
                .load(model.getSnippet().getThumbnails().getDefault().getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .dontAnimate()
                .into(binding.imgThumbnail);

        binding.txtTitle.setText(model.getSnippet().getTitle());
        binding.txtDescription.setText(model.getSnippet().getDescription());
        binding.btnWatch.setOnClickListener(view -> activity.startActivity(YouTubeStandalonePlayer.createVideoIntent(activity,
                activity.getResources().getString(R.string.YOUTUBE_API_KEY), model.getId().getVideoId())));

    }
}
