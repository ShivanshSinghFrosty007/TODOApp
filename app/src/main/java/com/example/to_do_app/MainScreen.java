package com.example.to_do_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainScreen extends AppCompatActivity implements ClickListiner {

    adapter adapt;
    RecyclerView recyclerView;
    List list;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    TextView startTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        recyclerView = findViewById(R.id.reycleView);
        startTxt = findViewById(R.id.start_txt);

        list = new ArrayList<>();
        loadData();
        StartTxt();
        adapt = new adapter(list, this);
        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
    }

    @Override
    public void onItemClick(int position) {
        list.remove(position);
        SaveData();
        StartTxt();
        adapt.notifyDataSetChanged();
    }

    EditText addTask;
    Button add;
    public void add(View v){

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = LayoutInflater.from(this).inflate(R.layout.popup, null);
        addTask = view.findViewById(R.id.Entertask);
        add = view.findViewById(R.id.addTxt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addTask.getText().toString().equals("")){
                    System.out.println(addTask.getText().toString());
                    list.add(addTask.getText().toString());
                    adapt.notifyDataSetChanged();
                    dialog.dismiss();
                    SaveData();
                    StartTxt();
                }
            }
        });
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    public void SaveData(){
        SharedPreferences prefs = getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> hSet = new HashSet<String>();
        for (int i = 0; i < list.size(); i++) {
            hSet.add((String) list.get(i));
        }
        editor.putStringSet("TaskSet", hSet);
        editor.commit();
    }

    public void loadData(){
        SharedPreferences prefs = getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE);
        Set<String> hSet = new HashSet<String>();
        hSet = prefs.getStringSet("TaskSet", hSet);
        for (String x : hSet) {
            System.out.println(x);
            list.add(x);
        }
//        adapt.notifyDataSetChanged();
    }

    public void StartTxt(){
        if (list.isEmpty()){
            startTxt.setVisibility(View.VISIBLE);
        }else {
            startTxt.setVisibility(View.INVISIBLE);
        }
    }

}