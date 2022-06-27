package com.example.my_module_wallet.features.assessment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.my_module_wallet.features.assessment.model.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessment WHERE moduleId = :id ORDER by assessmentName")
    List<Assessment> getAllAssessments(int id);

    @Query("SELECT Assessment.id, moduleId, assessmentName, count(Assessment.id), assessmentDueDate FROM assessment WHERE moduleId = :id GROUP BY assessmentDueDate  ORDER by assessmentDueDate")
    List<Assessment> getAllAssessmentsByDate(int id);
}
