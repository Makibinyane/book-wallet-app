package com.example.my_module_wallet.features.assessment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.my_module_wallet.features.assessment.model.Assessment;
import com.example.my_module_wallet.features.assessment.repo.AssessmentRepository;
import com.example.my_module_wallet.util.AppExecutors;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    AssessmentRepository assessmentRepository;
    public MutableLiveData<List<Assessment>> allAssessmentsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Assessment>> allAssessmentsByDateMutableLiveData = new MutableLiveData<>();


    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        assessmentRepository = new AssessmentRepository(application);
    }

    public void addAssessment(Assessment assessment) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                assessmentRepository.addNewAssessment(assessment);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    public void editAssessment(Assessment assessment) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                assessmentRepository.updateAssessment(assessment);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    public void deleteAssessment(Assessment assessment) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                assessmentRepository.deleteAssessment(assessment);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    public void getAssessments(int id) {
        allAssessmentsMutableLiveData.postValue(assessmentRepository.getAllAssessments(id));
    }

    public void getAssessmentsByDate(int id) {
        allAssessmentsByDateMutableLiveData.postValue(assessmentRepository.getAllAssessmentByDate(id));
    }

}
