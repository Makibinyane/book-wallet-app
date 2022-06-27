package com.example.my_module_wallet.features.module.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.module.model.Module;
import com.example.my_module_wallet.features.module.viewmodel.ModuleViewModel;

public class AddModuleFragment extends Fragment {
    private ModuleViewModel viewModel;
    EditText moduleNameEditText, moduleSemesterEditText, moduleCodeEditText, moduleDescriptionEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_module, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moduleNameEditText = (EditText) requireView().findViewById(R.id.txtAddModuleName);
        moduleSemesterEditText = (EditText) requireView().findViewById(R.id.txtAddModuleSemester);
        moduleCodeEditText = (EditText) requireView().findViewById(R.id.txtAddModuleCode);
        moduleDescriptionEditText = (EditText) requireView().findViewById(R.id.txtAddModuleDescription);

        Button btnAddModule = (Button) requireView().findViewById(R.id.btnSaveModule);
        btnAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Module module = new Module(0, moduleNameEditText.getText().toString(), moduleCodeEditText.getText().toString(), moduleSemesterEditText.getText().toString(), moduleDescriptionEditText.getText().toString());
                addNewModule(module);
            }
        });
    }

    private void addNewModule(Module module) {
        if (isValid(module)) {
            viewModel.addModule(module);
            showAddModuleDialog();
        }
    }

    private boolean isValid(Module module) {
        boolean isDataValid = true;
        if (module.getModuleName().isEmpty()) {
            isDataValid = false;
            moduleNameEditText.setError("Please enter module name");
        }

        if (module.getModuleCode().isEmpty()) {
            isDataValid = false;
            moduleCodeEditText.setError("Please enter module code");
        }

        if (module.getModuleDescription().isEmpty()) {
            isDataValid = false;
            moduleDescriptionEditText.setError("Please enter module description");
        }

        if (module.getModuleSemester().isEmpty()) {
            isDataValid = false;
            moduleSemesterEditText.setError("Please enter module semester");
        }
        return isDataValid;
    }

    private void showAddModuleDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Module has been successfully added..")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Navigation.findNavController(requireView()).navigate(R.id.action_addModuleFragment_to_moduleListFragment);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Module added");
        alert.show();
    }
}