package com.example.web;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>  {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private LayoutInflater Inflater;
    private List<String> ArrayList;

    public RecycleViewAdapter(Context context,List<String> arrayList) {
        this.Inflater = LayoutInflater.from(context);
        this.ArrayList = arrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        private Button delete, show;
        private SwipeRevealLayout swipeRevealLayout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.textView);
            delete = itemView.findViewById(R.id.delete);
            show = itemView.findViewById(R.id.show);
            swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "縣市" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.recyclerview_swaier, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.text.setText(ArrayList.get(position));
        viewBinderHelper.setOpenOnlyOne(true);//設置swipe只能有一個item被拉出
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));//綁定Layout

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipeRevealLayout.close(true);
                ArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,ArrayList.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    //  添加数据
    public void addData(int position) {
        ArrayList.add(position, "練習新增" + (position+1));
        notifyItemInserted(position);
    }





    public void recyclerviewAction(RecyclerView recyclerView, final ArrayList<String> choose, final RecycleViewAdapter adapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int position_dragged = viewHolder.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(choose, position_dragged, position_target);
                adapter.notifyItemMoved(position_dragged, position_target);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(Inflater.getContext(), android.R.color.holo_red_dark))
                        .addActionIcon(R.drawable.delete)
                        .create()
                        .decorate();


                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
}
