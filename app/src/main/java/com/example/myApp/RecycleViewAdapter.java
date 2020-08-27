package com.example.myApp;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.myApp1.R;
import com.example.room.DataBase;
import com.example.room.MyData;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>  {

    private Activity activity;
    private List<MyData> myData;
    private OnItemClickListener onItemClickListener;



    public RecycleViewAdapter(Activity activity, List<MyData> myData) {
        this.activity = activity;
        this.myData = myData;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private SwipeRevealLayout swipeRevealLayout;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView);


        }
    }


    public void refreshView() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<MyData> data = DataBase.getInstance(activity).getDataUao().displayAll();
                myData = data;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }


    public void deleteData (int position) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataBase.getInstance(activity).getDataUao().deleteData(myData.get(position).getId());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemRemoved(position);
                        refreshView();
                    }
                });
                }
            }).start();


//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                DataBase.getInstance(activity).getDataUao().deleteData(myData.get(position).getId());
//                refreshView();
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.start();
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_swaier,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder,  int position) {
        holder.text.setText(myData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(myData.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return myData.size(); }

    public interface OnItemClickListener {
        void onItemClick(MyData myData);}







    public void recyclerviewAction(RecyclerView recyclerView) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        deleteData(position);
                        break;
                    case ItemTouchHelper.RIGHT:
                        deleteData(position);
                        break;
                }
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(activity, android.R.color.holo_red_dark))
                        .addActionIcon(R.drawable.delete)
                        .create()
                        .decorate();


                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}
