package com.example.my_module_wallet.features.user.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.my_module_wallet.features.user.model.User;
import com.example.my_module_wallet.features.user.repo.UserRepository;
import com.example.my_module_wallet.util.AppExecutors;

public final class UserViewModel extends AndroidViewModel {
    UserRepository userRepository;
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void addUser(User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userRepository.addNewUser(user);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    public void userLogin(String username, String password) {
        userMutableLiveData.postValue(userRepository.getUser(username, password));
    }
}
