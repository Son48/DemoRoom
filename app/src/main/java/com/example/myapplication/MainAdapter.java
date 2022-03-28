package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<MainData> datalist;
    private Activity context;
    private RoomDB database;

    public MainAdapter(List<MainData> datalist, Activity context) {
        this.datalist = datalist;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,  false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainData mainData=datalist.get(position);
        database=RoomDB.getInstance(context);
        holder.textView.setText(mainData.getText());
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData d=datalist.get(holder.getAdapterPosition());

                int sID=d.getID();
                String sText=d.getText();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);
                int width= WindowManager.LayoutParams.MATCH_PARENT;
                int height=WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width, height);
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edit_text);
                Button btUpdate=dialog.findViewById(R.id.bt_update);
                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String uText=editText.getText().toString().trim();
                        database.mainDao().update(sID,uText);

                        datalist.clear();
                        datalist.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainData d=datalist.get(holder.getAdapterPosition());
                database.mainDao().delete(d);
                int position =holder.getAdapterPosition();
                datalist.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,datalist.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(datalist!=null){
            return datalist.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView btEdit, btDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btDelete=itemView.findViewById(R.id.btDelete);
            btEdit=itemView.findViewById(R.id.btEdit);
            textView=itemView.findViewById(R.id.textView);
        }
    }
}
