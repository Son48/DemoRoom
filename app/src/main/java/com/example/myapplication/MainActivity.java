package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button btAdd,btReset;
    RecyclerView recyclerView;
    List<MainData> dataList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edText);
        btAdd = findViewById(R.id.btnAdd);
        btReset= findViewById (R.id.btnReset);
        recyclerView = findViewById(R.id.recycler_view);

        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDao().getAll();

        linearLayoutManager= new LinearLayoutManager (this);
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //khoi tao adpater
        mainAdapter =new MainAdapter(dataList,MainActivity.this);
        recyclerView.setAdapter(mainAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText =editText.getText().toString().trim();
                if(!sText.equals("")){
                    MainData mainData=new MainData();
                    mainData.setText(sText);
                    database.mainDao().insert(mainData);
                    editText.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    mainAdapter.notifyDataSetChanged();
                }
            }
        });
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.mainDao().reset(dataList);
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                mainAdapter.notifyDataSetChanged();
            }
        });

    }
}