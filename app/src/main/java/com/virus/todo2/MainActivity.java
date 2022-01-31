package com.virus.todo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.virus.todo2.Adapter.ToDoAdapter;
import com.virus.todo2.Utils.DatabaseHandle;
import com.virus.todo2.model.ToDoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{
             private RecyclerView tasksRecycleView;
             private ToDoAdapter tasksadapter;
             private FloatingActionButton fab;

             private List<ToDoModel> taskList;
             private DatabaseHandle db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandle(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecycleView = findViewById(R.id.tasksRecyclerView);
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(this));
        tasksadapter = new ToDoAdapter (db, this);
        tasksRecycleView.setAdapter(tasksadapter);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksadapter));
        itemTouchHelper.attachToRecyclerView(tasksRecycleView);

         taskList = db.getAllTask();
         Collections.reverse(taskList);
         tasksadapter.setTasks(taskList);

         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
             }
         });
    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTask();
        Collections.reverse(taskList);
        tasksadapter.setTasks(taskList);
        tasksadapter.notifyDataSetChanged();
    }
}