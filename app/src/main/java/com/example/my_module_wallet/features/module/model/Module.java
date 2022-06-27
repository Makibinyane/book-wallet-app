package com.example.my_module_wallet.features.module.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "module")
public class Module implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String moduleName;
    private final String moduleCode;
    private final String moduleSemester;
    private final String moduleDescription;

    public Module(int id, String moduleName, String moduleCode, String moduleSemester, String moduleDescription) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.moduleSemester = moduleSemester;
        this.moduleDescription = moduleDescription;
        this.id = id;
    }

    protected Module(Parcel in) {
        moduleName = in.readString();
        moduleCode = in.readString();
        moduleSemester = in.readString();
        moduleDescription = in.readString();
        id = in.readInt();
    }

    public static final Creator<Module> CREATOR = new Creator<Module>() {
        @Override
        public Module createFromParcel(Parcel in) {
            return new Module(in);
        }

        @Override
        public Module[] newArray(int size) {
            return new Module[size];
        }
    };

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleSemester() {
        return moduleSemester;
    }

    public String getModuleDescription() {
        return moduleDescription;
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
        dest.writeString(moduleName);
        dest.writeString(moduleCode);
        dest.writeString(moduleSemester);
        dest.writeString(moduleDescription);
        dest.writeInt(id);
    }
}
