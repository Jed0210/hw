package com.example.web;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class bonus extends AppCompatActivity implements View.OnClickListener {

    Dialog dialog;
    Dialog dialog2;
    Button dia,add;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    String[] city = {"台中", "台北", "台南", "台東", "高雄", "嘉義", "雲林", "花蓮", "苗栗", "新竹", "彰化"};
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bonus);

        dia = (Button) findViewById(R.id.dia);
        dia.setOnClickListener(this);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);

        arrayList = new ArrayList<>();
        for (int i = 0; i < city.length; i++) {
            arrayList.add(city[i]);
        }


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new RecycleViewAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
        adapter.recyclerviewAction(recyclerView, arrayList, adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dia:
                dialog1();
                break;
            case R.id.add:
                adapter.addData(arrayList.size());
                break;
        }


    }


    public void dialog1() {
        dialog = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.dialog1, null);
        dialog.setContentView(v);
        dialog.setCancelable(false);

        dialog.show();

        Button next = v.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2();
            }
        });

        Button cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    public void dialog2() {
        dialog2 = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.dialog2, null);
        dialog2.setContentView(v);
        dialog2.setCancelable(false);

        dialog2.show();


        Button previous = v.findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        Button send = v.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
                dialog.dismiss();
            }
        });
    }



}




