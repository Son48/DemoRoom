package com.example.myapplication;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MainDao {
    //insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //delete query
    @Delete
    void delete(MainData mainData);

    //delete all query
    @Delete
    void reset(List<MainData>mainData);

    //update query

    @Query("UPDATE table_name SET text= :sText WHERE ID = :sID")
    void update(int sID,String sText);

    @Query("SELECT * FROM table_name")
    List<MainData> getAll();
}
