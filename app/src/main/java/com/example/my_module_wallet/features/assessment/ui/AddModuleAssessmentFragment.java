package com.example.my_module_wallet.features.assessment.ui;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.assessment.model.Assessment;
import com.example.my_module_wallet.features.assessment.viewmodel.AssessmentViewModel;
import com.example.my_module_wallet.features.common.AssessmentNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddModuleAssessmentFragment extends Fragment {
    private AssessmentViewModel viewModel;
    private EditText assessmentNameEditText, assessmentDueDateEditText, assessmentDescriptionEditText;
    private int mYear, mMonth, mDay, mHourOfDay, mMinute;
    Calendar calendar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_module_assessment, container, false);

        viewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentDueDateEditText = (EditText) view.findViewById(R.id.txtAddAssessmentDueDate);
        assessmentDueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get (Calendar.YEAR);
                mMonth = calendar.get (Calendar.MONTH);
                mDay = calendar.get (Calendar.DAY_OF_MONTH);
                mHourOfDay = calendar.get(Calendar.HOUR_OF_DAY) + 1;
                mMinute = 0;

                DatePickerDialog datePickerDialog = new DatePickerDialog ( requireContext(), new DatePickerDialog.OnDateSetListener () {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String stringDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String newstring = new SimpleDateFormat("dd/MM/yyyy").format(date);
                        assessmentDueDateEditText.setText(newstring);
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assessmentNameEditText = (EditText) requireView().findViewById(R.id.txtAddAssessmentName);
        assessmentDescriptionEditText = (EditText) requireView().findViewById(R.id.txtAddAssessmentDescription);

        Button btnAddNewAssessment = (Button) requireView().findViewById(R.id.btnAddNewAssessment);

        btnAddNewAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    int moduleId = getArguments().getInt("moduleId");
                    Assessment assessment = new Assessment(0, assessmentNameEditText.getText().toString(), assessmentDescriptionEditText.getText().toString(),
                            assessmentDueDateEditText.getText().toString(), moduleId);
                    addNewAssessment(assessment);
                }
            }
        });
    }

    private void addNewAssessment(Assessment assessment) {
        if (isValid(assessment)) {
            viewModel.addAssessment(assessment);
            setNotification(assessment.getAssessmentName());
            showAddAssessmentDialog();
        }
    }

    private boolean isValid(Assessment assessment) {
        boolean isDataValid = true;
        if (assessment.getAssessmentName().isEmpty()) {
            isDataValid = false;
            assessmentNameEditText.setError("Please enter assessment name");
        }

        if (assessment.getAssessmentDueDate().isEmpty()) {
            isDataValid = false;
            assessmentDueDateEditText.setError("Please enter assessment due date");
        }

        if (assessment.getAssessmentDescription().isEmpty()) {
            isDataValid = false;
            assessmentDescriptionEditText.setError("Please enter assessment description");
        }
        return isDataValid;
    }

    private void showAddAssessmentDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Assessment has been successfully added..")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Navigation.findNavController(requireView()).navigate(R.id.action_addModuleAssessmentFragment_to_assessmentListFragment);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Assessment added");
        alert.show();
    }

    public void setNotification(String assessmentName) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);

        calendar.set(Calendar.HOUR_OF_DAY, mHourOfDay);
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.SECOND, 0);
        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(requireActivity().getApplicationContext(), AssessmentNotification.class);
        intent.putExtra("task", assessmentName);
        intent.putExtra("timeInMillis", calendar.getTimeInMillis());
        intent.putExtra("reqCode", 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireActivity().getApplicationContext(), 1, intent, 0);
        alarmManager.set(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}