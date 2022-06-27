package com.example.my_module_wallet.features.module.repo;

import android.app.Application;

import com.example.my_module_wallet.db.ModuleWalletDatabase;
import com.example.my_module_wallet.features.module.model.Module;
import com.example.my_module_wallet.features.module.ModuleDao;

import java.util.List;

public class ModuleRepository {
    ModuleDao moduleDao;

    public ModuleRepository(Application application) {
        ModuleWalletDatabase database = ModuleWalletDatabase.getDatabase(application);
        moduleDao = database.moduleDao();
    }

    public void addNewModule(Module module) {
        moduleDao.insert(module);
    }

    public void deleteModule(Module module) {
        moduleDao.delete(module);
    }

    public void updateModule(Module module) {
        moduleDao.update(module);
    }

    public List<Module> getAllModules() {
        return moduleDao.getAllModules();
    }
}
