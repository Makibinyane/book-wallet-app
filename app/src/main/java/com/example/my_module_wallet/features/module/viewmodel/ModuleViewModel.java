package com.example.my_module_wallet.features.module.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.my_module_wallet.features.module.model.Module;
import com.example.my_module_wallet.features.module.repo.ModuleRepository;
import com.example.my_module_wallet.util.AppExecutors;

import java.util.List;

public final class ModuleViewModel extends AndroidViewModel {
    ModuleRepository moduleRepository;
    public MutableLiveData<List<Module>> allModulesMutableLiveData = new MutableLiveData<>();

    public ModuleViewModel(Application application) {
        super(application);
        moduleRepository = new ModuleRepository(application);
    }

    public void addModule(Module module) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                moduleRepository.addNewModule(module);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    public void editModule(Module module) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                moduleRepository.updateModule(module);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    public void deleteModule(Module module) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                moduleRepository.deleteModule(module);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    public void getModules() {
        allModulesMutableLiveData.postValue(moduleRepository.getAllModules());
    }
}
