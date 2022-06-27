package com.example.my_module_wallet.features.assessment.ui;

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
import com.example.my_module_wallet.features.assessment.model.Assessment;
import com.example.my_module_wallet.features.assessment.viewmodel.AssessmentViewModel;


public class EditModuleAssessmentFragment extends Fragment {
    private AssessmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        return inflater.inflate(R.layout.fragment_edit_module_assessment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText assessmentNameEditText = (EditText) requireView().findViewById(R.id.txtEditAssessmentName);
        EditText assessmentDueDateEditText = (EditText) requireView().findViewById(R.id.txtEditAssessmentDueDate);
        EditText assessmentDescriptionEditText = (EditText) requireView().findViewById(R.id.txtEditAssessmentDescription);

        if (getArguments() != null) {
            Assessment assessment = (Assessment) getArguments().getParcelable("assessment");
            assessmentNameEditText.setText(assessment.getAssessmentName());
            assessmentDueDateEditText.setText(assessment.getAssessmentDueDate());
            assessmentDescriptionEditText.setText(assessment.getAssessmentDescription());

            Button btnEdit = (Button) requireView().findViewById(R.id.btnSaveAssessment);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Assessment updateAssessment = new Assessment(assessment.getId(), assessment.getAssessmentName(), assessment.getAssessmentDescription(), assessment.getAssessmentDueDate(), assessment.getModuleId());
                    performAssessmentEdit(updateAssessment);
                }
            });
        }
    }

    private void performAssessmentEdit(Assessment assessment) {
        viewModel.editAssessment(assessment);
        showEditModuleDialog(assessment);
    }

    private void showEditModuleDialog(Assessment assessment) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Assessment has been successfully updated..")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("assessment", assessment);
                        Navigation.findNavController(requireView()).navigate(R.id.action_editModuleAssessmentFragment_to_viewAssessmentDetailsFragment, bundle);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Assessment updated");
        alert.show();
    }
}