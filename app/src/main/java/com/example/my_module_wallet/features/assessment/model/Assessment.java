package com.example.my_module_wallet.features.assessment.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment")
public class Assessment implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    int id;
    private final String assessmentName;
    private final String assessmentDescription;
    private final String assessmentDueDate;
    private final int moduleId;

    public Assessment(int id, String assessmentName, String assessmentDescription, String assessmentDueDate, int moduleId) {
        this.assessmentName = assessmentName;
        this.assessmentDescription = assessmentDescription;
        this.assessmentDueDate = assessmentDueDate;
        this.moduleId = moduleId;
        this.id = id;
    }

    protected Assessment(Parcel in) {
        assessmentName = in.readString();
        assessmentDescription = in.readString();
        assessmentDueDate = in.readString();
        moduleId = in.readInt();
        id = in.readInt();
    }

    public static final Creator<Assessment> CREATOR = new Creator<Assessment>() {
        @Override
        public Assessment createFromParcel(Parcel in) {
            return new Assessment(in);
        }

        @Override
        public Assessment[] newArray(int size) {
            return new Assessment[size];
        }
    };

    public String getAssessmentName() {
        return assessmentName;
    }

    public String getAssessmentDescription() {
        return assessmentDescription;
    }

    public String getAssessmentDueDate() {
        return assessmentDueDate;
    }

    public int getModuleId() {
        return moduleId;
    }

    public int getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(assessmentName);
        dest.writeString(assessmentDescription);
        dest.writeString(assessmentDueDate);
        dest.writeInt(moduleId);
    }
}
