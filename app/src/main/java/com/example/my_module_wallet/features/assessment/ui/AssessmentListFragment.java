package com.example.my_module_wallet.features.assessment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.assessment.model.Assessment;
import com.example.my_module_wallet.features.assessment.viewmodel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentListFragment extends Fragment implements AssessmentListAdapter.AssessmentItemClickListener {
    private AssessmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        return inflater.inflate(R.layout.fragment_assessment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        FloatingActionButton button = (FloatingActionButton) requireView().findViewById(R.id.btnAddAssessment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    int moduleId = getArguments().getInt("moduleId");
                    navigateToAddAssessment(moduleId);
                }
            }
        });
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = requireView().findViewById(R.id.assessmentsRecyclerView);

        AssessmentListAdapter adapter = new AssessmentListAdapter();
        adapter.setItemClickListener(this);

        recyclerView.setAdapter(adapter);
        if (getArguments() != null) {
            int moduleId = getArguments().getInt("moduleId");
            viewModel.getAssessments(moduleId);
            viewModel.allAssessmentsMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<Assessment>>() {
                @Override
                public void onChanged(List<Assessment> assessments) {
                    adapter.submitList(assessments);
                }
            });
        }
    }

    private void navigateToAddAssessment(int moduleId) {
        Bundle bundle = new Bundle();
        bundle.putInt("moduleId", moduleId);
        Navigation.findNavController(requireView()).navigate(R.id.action_assessmentListFragment_to_addModuleAssessmentFragment, bundle);
    }

    @Override
    public void onItemClicked(Assessment assessment) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("assessment", assessment);
        Navigation.findNavController(requireView()).navigate(R.id.action_assessmentListFragment_to_viewAssessmentDetailsFragment, bundle);
    }
}