package com.example.myApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myApp1.R;

import java.util.List;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.ViewHolder>{
    private List<Post> list;
    private LayoutInflater inflater;

    RetrofitAdapter(Context context, List<Post> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= inflater.inflate(R.layout.recyclerview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post mlist =list.get(position);
        holder.id.setText(String.valueOf(mlist.getId()));
        holder.title.setText(mlist.getTitle());
        holder.body.setText(mlist.getBody());
        holder.userid.setText(String.valueOf(mlist.getUserId()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView title;
        TextView body;
        TextView userid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            title=itemView.findViewById(R.id.title);
            body=itemView.findViewById(R.id.body);
            userid=itemView.findViewById(R.id.userid);
        }
    }
}
