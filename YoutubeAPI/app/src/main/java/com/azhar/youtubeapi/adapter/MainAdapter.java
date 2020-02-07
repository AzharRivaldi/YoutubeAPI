package com.azhar.youtubeapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class MainAdapter<TipeData, ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> {

    private int mLayout;
    Class<ViewHolder> mViewHolderClass;
    Class<TipeData> mModelClass;
    List<TipeData> mData;

    public MainAdapter(int mLayout, Class<ViewHolder> mViewHolderClass, Class<TipeData> mModelClass, List<TipeData> mData) {
        this.mLayout = mLayout;
        this.mViewHolderClass = mViewHolderClass;
        this.mModelClass = mModelClass;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);

        try {
            Constructor<ViewHolder> constructor = mViewHolderClass.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TipeData model = getItem(position);
        bindView(holder, model, position);
    }

    public abstract void bindView(ViewHolder holder, TipeData model, int position);

    private TipeData getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

