package com.example.my_module_wallet.features.user.repo;

import android.app.Application;

import com.example.my_module_wallet.db.ModuleWalletDatabase;
import com.example.my_module_wallet.features.user.model.User;
import com.example.my_module_wallet.features.user.UserDao;

public class UserRepository {
    UserDao userDao;

    public UserRepository(Application application) {
        ModuleWalletDatabase database = ModuleWalletDatabase.getDatabase(application);
        userDao = database.userDao();
    }

    public void addNewUser(User user) {
        userDao.insert(user);
    }

    public User getUser(String username, String password) {
        return userDao.getUser(username, password);
    }
}
