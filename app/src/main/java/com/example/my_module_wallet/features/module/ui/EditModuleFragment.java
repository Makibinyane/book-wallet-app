package com.example.my_module_wallet.features.module.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.module.model.Module;
import com.example.my_module_wallet.features.module.viewmodel.ModuleViewModel;


public class EditModuleFragment extends Fragment {
    private ModuleViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        return inflater.inflate(R.layout.fragment_edit_module, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText moduleNameEditText = (EditText) requireView().findViewById(R.id.txtModuleName);
        EditText moduleSemesterEditText = (EditText) requireView().findViewById(R.id.txtSemester);
        EditText moduleDescriptionEditText = (EditText) requireView().findViewById(R.id.txtModuleDescription);

        Button btnEdit = (Button) requireView().findViewById(R.id.btnEditModule);

        if (getArguments() != null) {
            Module module = (Module) getArguments().getParcelable("module");
            moduleNameEditText.setText(module.getModuleName());
            moduleSemesterEditText.setText(module.getModuleSemester());
            moduleDescriptionEditText.setText(module.getModuleDescription());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Module moduleEdit = new Module(module.getId(), moduleNameEditText.getText().toString(), module.getModuleCode(), moduleSemesterEditText.getText().toString(), moduleDescriptionEditText.getText().toString());
                    performModuleEdit(moduleEdit);
                }
            });
        }
    }

    private void performModuleEdit(Module module) {
        viewModel.editModule(module);
        showEditModuleDialog(module);
    }

    private void showEditModuleDialog(Module module) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Module has been successfully updated..")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("module", module);
                        Navigation.findNavController(requireView()).navigate(R.id.action_editModuleFragment_to_viewModuleDetailsFragment, bundle);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Module updated");
        alert.show();
    }


}