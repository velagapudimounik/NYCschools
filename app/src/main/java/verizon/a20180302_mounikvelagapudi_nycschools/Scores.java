package verizon.a20180302_mounikvelagapudi_nycschools;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import verizon.a20180302_mounikvelagapudi_nycschools.ModelClasses.School;

public class Scores extends AppCompatActivity implements ServiceListener {

    RecyclerView recycle;
    String dbnSelected;
    TextView tvWriting, tvReading, tvMath, tvSchoolName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofmarks);

        tvMath = findViewById(R.id.mathId);
        tvReading = findViewById(R.id.readingId);
        tvWriting = findViewById(R.id.writingId);
        tvSchoolName = findViewById(R.id.schoolNameId);

        dbnSelected = getIntent().getStringExtra("dbnSelected");

        String detailedList = "https://data.cityofnewyork.us/resource/734v-jeq5.json";

//        recycle = (RecyclerView) findViewById(R.id.recycler2);
//
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
//        recycle.setLayoutManager(mLayoutManager);

        new WebService(getApplicationContext(), recycle, this, "GET", detailedList, "please wait for loading").execute();
    }

    @Override
    public void onServiceComplete(String respon) {
        ArrayList<School> schools;
        JSONTokener jsonToken = new JSONTokener(respon);
        JSONArray jsonArray = null;
        HashMap<String, School> schoolHashMap = new HashMap<>();

        try {
            jsonArray = new JSONArray(jsonToken);
            System.out.println(jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                School school = new School(jsonArray.getJSONObject(i), "");
                schoolHashMap.put(school.getDbn(), school);
            }

            // if (schoolHashMap.containsKey(dbnSelected)) {
            School selectedSchool1 = schoolHashMap.get(dbnSelected);

            if (selectedSchool1 != null) {
                System.out.println(selectedSchool1.getReadingScore() + selectedSchool1.getWritingScore());
                System.out.println(selectedSchool1.getMathScore() + selectedSchool1.getSchoolName());

                tvReading.setText(selectedSchool1.getReadingScore());
                tvWriting.setText(selectedSchool1.getWritingScore());
                tvMath.setText(selectedSchool1.getMathScore());
                tvSchoolName.setText(selectedSchool1.getSchoolName());

            } else Toast.makeText(this, "School name is null", Toast.LENGTH_SHORT).show();

            //  } else Toast.makeText(this, "This school is not available", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceCancel(String posts) {

    }
}
