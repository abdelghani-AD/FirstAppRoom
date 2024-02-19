package com.example.tproomstagiaire.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tproomstagiaire.Entity.Stagiaire;

import java.util.List;

@Dao
public interface StagiaireDao {
    @Insert
    void insert(Stagiaire stagiaire);
    @Update
    void update(Stagiaire stagiaire);
    @Delete
    void delete(Stagiaire stagiaire);

    @Query("select  * from stagiaire where id = :id")
    Stagiaire getStagiaireBYId(int id);
    @Query("select * from stagiaire where id in (:ids)")
    List<Stagiaire> loadAllByIds(int[] ids);
    @Query("select * from stagiaire")
    List<Stagiaire> getAllStagiaires();
}

