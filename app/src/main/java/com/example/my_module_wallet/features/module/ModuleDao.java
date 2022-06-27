package com.example.my_module_wallet.features.module;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.my_module_wallet.features.module.model.Module;

import java.util.List;

@Dao
public interface ModuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Module module);

    @Update
    void update(Module module);

    @Delete
    void delete(Module module);

    @Query("SELECT * FROM module ORDER by moduleName")
    List<Module> getAllModules();
}
