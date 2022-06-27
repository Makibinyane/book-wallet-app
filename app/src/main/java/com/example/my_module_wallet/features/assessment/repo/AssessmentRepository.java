package com.example.my_module_wallet.features.assessment.repo;

import android.app.Application;

import com.example.my_module_wallet.db.ModuleWalletDatabase;
import com.example.my_module_wallet.features.assessment.model.Assessment;
import com.example.my_module_wallet.features.assessment.AssessmentDao;

import java.util.List;

public class AssessmentRepository {
    AssessmentDao assessmentDao;

    public AssessmentRepository(Application application) {
        ModuleWalletDatabase database = ModuleWalletDatabase.getDatabase(application);
        assessmentDao = database.assessmentDao();
    }

    public void addNewAssessment(Assessment assessment) {
        assessmentDao.insert(assessment);
    }

    public void updateAssessment(Assessment assessment) {
        assessmentDao.update(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        assessmentDao.delete(assessment);
    }

    public List<Assessment> getAllAssessments(int id) {
        return assessmentDao.getAllAssessments(id);
    }

    public List<Assessment> getAllAssessmentByDate(int id) {
        return assessmentDao.getAllAssessmentsByDate(id);
    }

}
