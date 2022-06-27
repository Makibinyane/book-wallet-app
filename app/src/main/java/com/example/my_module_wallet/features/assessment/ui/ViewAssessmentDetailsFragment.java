package com.example.my_module_wallet.features.assessment.ui;

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
import android.widget.TextView;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.assessment.model.Assessment;
import com.example.my_module_wallet.features.assessment.viewmodel.AssessmentViewModel;

public class ViewAssessmentDetailsFragment extends Fragment {
    private AssessmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        return inflater.inflate(R.layout.fragment_view_assessment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView displayAssessmentNameTextView = (TextView) requireView().findViewById(R.id.txtDisplayAssessmentName);
        TextView displayAssessmentDueDateTextView = (TextView) requireView().findViewById(R.id.txtDisplayAssessmentDueDate);
        TextView displayAssessmentDescriptionTextView = (TextView) requireView().findViewById(R.id.txtDisplayAssessmentDescription);

        CardView cdAssessmentDetails = (CardView) requireView().findViewById(R.id.cdAssessmentDetails);
        if (getArguments() != null) {
            Assessment assessment = (Assessment) getArguments().getParcelable("assessment");
            displayAssessmentNameTextView.setText(assessment.getAssessmentName());
            displayAssessmentDueDateTextView.setText(assessment.getAssessmentDueDate());
            displayAssessmentDescriptionTextView.setText(assessment.getAssessmentDescription());

            cdAssessmentDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteOrEditDialog(assessment);
                }
            });
        }
    }

    private void showDeleteOrEditDialog(Assessment assessment) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Would you like to delete or edit " + assessment.getAssessmentName() + " module?")
                .setCancelable(false)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        navigateToEditAssessmentScreen(assessment);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showDeleteAlertDialog(assessment);
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(assessment.getAssessmentName());
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void showDeleteAlertDialog(Assessment assessment) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure you want to delete " + assessment.getAssessmentName() + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAssessment(assessment);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Delete " + assessment.getAssessmentName());
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void navigateToEditAssessmentScreen(Assessment assessment) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("assessment", assessment);
        Navigation.findNavController(requireView()).navigate(R.id.action_viewAssessmentDetailsFragment_to_editModuleAssessmentFragment, bundle);
    }

    public void deleteAssessment(Assessment assessment) {
        viewModel.deleteAssessment(assessment);
        showDeleteModuleDialog();
    }

    private void showDeleteModuleDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Assessment has been successfully deleted..")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Navigation.findNavController(requireView()).navigate(R.id.action_viewAssessmentDetailsFragment_to_assessmentListFragment);
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Assessment deleted");
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
}