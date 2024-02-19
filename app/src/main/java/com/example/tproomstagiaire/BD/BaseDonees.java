package com.example.tproomstagiaire.BD;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tproomstagiaire.DAO.StagiaireDao;
import com.example.tproomstagiaire.Entity.Stagiaire;

@Database(entities = Stagiaire.class , version = 1 , exportSchema = false)
public abstract class BaseDonees extends RoomDatabase {
    public abstract StagiaireDao stagiaireDao();

    private static volatile BaseDonees instance;
    public static BaseDonees getInstance(Context context){
        if (instance==null){
            synchronized (BaseDonees.class){
                if (instance == null){
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            BaseDonees.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
