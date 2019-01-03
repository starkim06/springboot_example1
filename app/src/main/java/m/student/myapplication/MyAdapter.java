package m.student.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView ivTitle;

        View view;

        MyViewHolder(View view){
            super(view);
            this.view = view;
            ivPicture = view.findViewById(R.id.iv_picture);
            ivTitle = view.findViewById(R.id.tv_price);
        }

        void onBind(PhotoInfo photoInfo){
            Picasso.with(view.getContext())
                    .load(photoInfo.url)
                    .into(ivPicture);
            ivTitle.setText(photoInfo.title);
        }
    }

    private ArrayList<PhotoInfo> photoInfoArrayList;

    MyAdapter(ArrayList<PhotoInfo> foodInfoArrayList){
        this.photoInfoArrayList = foodInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.onBind(photoInfoArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoInfoArrayList.size();
    }

}

