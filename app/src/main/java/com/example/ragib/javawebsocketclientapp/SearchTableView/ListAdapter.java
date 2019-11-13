package com.example.ragib.javawebsocketclientapp.SearchTableView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ragib.javawebsocketclientapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ragib on 07-Nov-19.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    public interface OnClickRecycleView{
        public void onClickView(int position);
    }
    OnClickRecycleView onClickRecycleView;

    List<ListItem> listItems;

    public ListAdapter(List<ListItem> listItems,OnClickRecycleView onClickRecycleView) {
        this.listItems = listItems;
        this.onClickRecycleView=onClickRecycleView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lookup_search_recycleview, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ListItem listItem=this.listItems.get(position);
        holder.nameTV.setText(listItem.getName());
        holder.nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRecycleView.onClickView(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTV;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.lookupTableTV);
        }
    }
}
