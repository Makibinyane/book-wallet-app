package com.example.my_module_wallet.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.my_module_wallet.features.assessment.model.Assessment;
import com.example.my_module_wallet.features.assessment.AssessmentDao;
import com.example.my_module_wallet.features.module.model.Module;
import com.example.my_module_wallet.features.module.ModuleDao;
import com.example.my_module_wallet.features.user.model.User;
import com.example.my_module_wallet.features.user.UserDao;

@Database(entities = {Module.class, Assessment.class, User.class}, version = 5, exportSchema = false)
public abstract class ModuleWalletDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "modulewalletdb";
    public abstract ModuleDao moduleDao();
    public abstract AssessmentDao assessmentDao();
    public abstract UserDao userDao();

    private static ModuleWalletDatabase INSTANCE;


    public static ModuleWalletDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ModuleWalletDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
