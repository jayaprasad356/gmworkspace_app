package com.greymatter.gmworkspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.greymatter.gmworkspace.adapter.ProjectSpinAdapter;
import com.greymatter.gmworkspace.helper.ApiConfig;
import com.greymatter.gmworkspace.helper.Constant;
import com.greymatter.gmworkspace.helper.Session;
import com.greymatter.gmworkspace.model.Project;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TimesheetActivity extends AppCompatActivity {


    private TextView selectedDateTV,tvHour;
    ImageButton imgBackbtn;
    private Spinner spinProject;
    private ProjectSpinAdapter adapter;
    Activity activity;
    Session session;
    Project[] projects;
    String ProjectId = "", ProjectDate = "";
    EditText etDescription;
    Slider sliderHours;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesheet);
        activity = TimesheetActivity.this;
        session = new Session(activity);
        selectedDateTV = findViewById(R.id.idTVSelectedDate);
        imgBackbtn = findViewById(R.id.imgBackbtn);
        spinProject = findViewById(R.id.spinProject);
        etDescription = findViewById(R.id.etDescription);
        sliderHours = findViewById(R.id.sliderHours);
        tvHour = findViewById(R.id.tvHour);
        btnSubmit = findViewById(R.id.btnSubmit);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ProjectDate = df.format(c);
        selectedDateTV.setText(ProjectDate);

        sliderHours.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                float value = sliderHours.getValue();
                int i =  (int) value;
                tvHour.setText(  i+"");
            }
        });


        imgBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        projectList();
        selectedDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                ProjectDate = year + "-" + convertTwodigit(monthOfYear) + "-" + convertTwodigit(dayOfMonth);
                                selectedDateTV.setText(ProjectDate);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSheetapi();
            }
        });
    }

    private void timeSheetapi()
    {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.PROJECT_ID,ProjectId);
        params.put(Constant.STAFF_ID,session.getData(Constant.ID));
        params.put(Constant.DATE,ProjectDate);
        params.put(Constant.HOURS,tvHour.getText().toString().trim());
        params.put(Constant.DESCRIPTION,etDescription.getText().toString().trim());
        ApiConfig.RequestToVolley((result,response) -> {
            Log.d("TIME_SHEET_RES",response);
            if(result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity,
                                MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },activity,Constant.TIMESHEETS_URL,params,true);

    }

    private String convertTwodigit(int s) {
        long val = (long) s;
        String format = "%1$02d";
        return (String.format(format, val));
    }


    private void projectList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.STAFF_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        projects = new Project[jsonArray.length()];

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                projects[i] = new Project();
                                projects[i].setId(jsonObject1.getString(Constant.ID));
                                projects[i].setName(jsonObject1.getString(Constant.NAME));
                            } else {
                                break;
                            }
                        }
                        adapter = new ProjectSpinAdapter(activity, R.layout.spinner_item, projects);
                        spinProject.setAdapter(adapter); // Set the custom adapter to the spinner
                        spinProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view,
                                                       int position, long id) {
                                // Here you get the current item (a User object) that is selected by its position
                                Project project = adapter.getItem(position);
                                ProjectId = project.getId();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapter) {
                            }
                        });


                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, activity, Constant.PROJECCT_LIST_URL, params, true);
    }


}