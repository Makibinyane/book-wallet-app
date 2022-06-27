package com.example.my_module_wallet.features.module.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.module.model.Module;
import com.example.my_module_wallet.features.module.viewmodel.ModuleViewModel;

public class ViewModuleDetailsFragment extends Fragment {
    private ModuleViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        return inflater.inflate(R.layout.fragment_view_module_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView displayModuleNameTextView = (TextView) requireView().findViewById(R.id.txtDisplayModuleName);
        TextView displayModuleCodeTextView = (TextView) requireView().findViewById(R.id.txtDisplayModuleCode);
        TextView displayModuleSemesterTextView = (TextView) requireView().findViewById(R.id.txtDisplayModuleSemester);
        TextView displayModuleDescriptionTextView = (TextView) requireView().findViewById(R.id.txtDisplayModuleDescription);

        Button btnAssessments = (Button) requireView().findViewById(R.id.btnAssessments);
        CardView cdModuleDetails = (CardView) requireView().findViewById(R.id.cdModuleDetails);

        if (getArguments() != null) {
            Module module = (Module) getArguments().getParcelable("module");
            displayModuleNameTextView.setText(module.getModuleName());
            displayModuleCodeTextView.setText(module.getModuleCode());
            displayModuleSemesterTextView.setText(module.getModuleSemester());
            displayModuleDescriptionTextView.setText(module.getModuleDescription());


            btnAssessments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        navigateToAssessments(module.getId());
                    }
            });

            cdModuleDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        showDeleteOrEditDialog(module);
                    }
            });
        }
    }

    private void navigateToAssessments(int moduleId) {
        Bundle bundle = new Bundle();
        bundle.putInt("moduleId", moduleId);
        Navigation.findNavController(requireView()).navigate(R.id.action_viewModuleDetailsFragment_to_assessmentListFragment, bundle);
    }

    private void navigateToEditModuleScreen(Module module) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("module", module);
        Navigation.findNavController(requireView()).navigate(R.id.action_viewModuleDetailsFragment_to_editModuleFragment, bundle);
    }

    private void showDeleteAlertDialog(Module module) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure you want to delete " + module.getModuleName() + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteModule(module);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Delete " + module.getModuleName());
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void showDeleteOrEditDialog(Module module) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Would you like to delete or edit " + module.getModuleName() + " module?")
                .setCancelable(false)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        navigateToEditModuleScreen(module);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showDeleteAlertDialog(module);
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(module.getModuleName());
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void deleteModule(Module module) {
        viewModel.deleteModule(module);
        showDeleteModuleDialog();
    }

    private void showDeleteModuleDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Module has been successfully deleted..")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Navigation.findNavController(requireView()).navigate(R.id.action_viewModuleDetailsFragment_to_moduleListFragment);
                    }
                });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.setTitle("Module deleted");
        alert.show();
    }
}